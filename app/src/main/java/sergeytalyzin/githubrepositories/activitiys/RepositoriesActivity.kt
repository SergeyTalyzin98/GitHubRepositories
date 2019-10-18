package sergeytalyzin.githubrepositories.activitiys

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_repositories.*
import sergeytalyzin.githubrepositories.R
import sergeytalyzin.githubrepositories.helpers.Token
import sergeytalyzin.githubrepositories.presenters.RepositoriesPresenter
import sergeytalyzin.githubrepositories.views.RepositoriesView

class RepositoriesActivity : MvpAppCompatActivity(), RepositoriesView {

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val token = Token.getTokenFromSharedPreferences(this@RepositoriesActivity)

        if(token == Token.ANONYMOUS || token == "") {
            menu.findItem(R.id.login_menu).isVisible = true
        }
        else
            menu.findItem(R.id.logout_menu).isVisible = true
        return true
    }

    override fun startLoginActivity() {
        Token.deleteToken(this@RepositoriesActivity)
        startActivity(Intent(this@RepositoriesActivity, LoginActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {

        R.id.search_menu -> {

            true
        }
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
