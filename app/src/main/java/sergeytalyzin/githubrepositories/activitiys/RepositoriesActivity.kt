package sergeytalyzin.githubrepositories.activitiys

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_repositories.*
import sergeytalyzin.githubrepositories.R
import sergeytalyzin.githubrepositories.helpers.Adapter
import sergeytalyzin.githubrepositories.helpers.Repository
import sergeytalyzin.githubrepositories.helpers.Token
import sergeytalyzin.githubrepositories.presenters.RepositoriesPresenter
import sergeytalyzin.githubrepositories.views.RepositoriesView

class RepositoriesActivity : MvpAppCompatActivity(), RepositoriesView {

    private val repositoriesAdapter = Adapter()
    private var searchView: SearchView? = null

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        setSupportActionBar(toolbar)

        repositories_recycler.layoutManager = LinearLayoutManager(this@RepositoriesActivity)
        repositories_recycler.adapter = repositoriesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val token = Token.getTokenFromSharedPreferences(this@RepositoriesActivity)

        if(token == Token.ANONYMOUS || token == "")
            menu.findItem(R.id.login_menu).isVisible = true
        else
            menu.findItem(R.id.logout_menu).isVisible = true

        val searchItem = menu.findItem(R.id.search_menu)

        searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView?.let {
            if (!repositoriesPresenter.searchViewIsIconified) {
                it.isIconified = false
                it.setQuery(repositoriesPresenter.queryText, false)
            }
        }

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                repositoriesPresenter.setQuery(query = newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // При выполнении метода
        // Давнные о SearchView сохраняются в презентер
        // Это помагает во время переворота экрана не терять состояние SearchView
        searchView?.let {
            if(!it.isIconified) {
                repositoriesPresenter.searchViewIsIconified = false
                repositoriesPresenter.queryText = it.query.toString()
            }
            else {
                repositoriesPresenter.searchViewIsIconified = true
                repositoriesPresenter.queryText = ""
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this@RepositoriesActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: Int) {
        Toast.makeText(this@RepositoriesActivity, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun setListAdapter(list: List<Repository.Item>) {
        repositoriesAdapter.setList(list)
    }

    override fun startLoading() {
        text_search_github.visibility = View.GONE
        repositories_recycler.visibility = View.GONE
        loading_repositories.visibility = View.VISIBLE
    }

    override fun endLoading() {
        loading_repositories.visibility = View.GONE
        text_search_github.visibility = View.GONE
        repositories_recycler.visibility = View.VISIBLE
    }

    override fun startLoginActivity() {
        Token.deleteToken(this@RepositoriesActivity)
        startActivity(Intent(this@RepositoriesActivity, LoginActivity::class.java))
        finish()
    }

    override fun clearList() {
        repositories_recycler.visibility = View.GONE
        repositoriesAdapter.clearList()
        text_search_github.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {

        R.id.login_menu -> {
            startLoginActivity()
            true
        }
        R.id.logout_menu -> {
            startLoginActivity()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
