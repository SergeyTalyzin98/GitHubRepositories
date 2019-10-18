package sergeytalyzin.githubrepositories.activitiys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_login.*
import sergeytalyzin.githubrepositories.R
import sergeytalyzin.githubrepositories.helpers.Token
import sergeytalyzin.githubrepositories.presenters.LoginPresenter
import sergeytalyzin.githubrepositories.views.LoginView

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Если пользователь авторизован переходим на экран поиска
        if(Token.getTokenFromSharedPreferences(this@LoginActivity) != "") {
            val intent = Intent(this@LoginActivity, RepositoriesActivity::class.java)
            startActivity(intent)
            finish()
        }

        authentication.setOnClickListener {
            loginPresenter.login()
        }

        anonymously.setOnClickListener {
            loginPresenter.startRepository()
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if(uri != null)
            loginPresenter.getToken(uri = uri)
        else
            endLoading()
    }

    override fun showError(error: String) {
        Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: Int) {
        Toast.makeText(this@LoginActivity, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun saveToken(token: String) =
        Token.saveToken(token = token, context = this@LoginActivity)

    override fun startRepositoryActivity() {
        val intent = Intent(this@LoginActivity, RepositoriesActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun startLoginProcess() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(loginPresenter.urlForLogin))
        startActivity(intent)
    }

    override fun startLoading() {
        authentication.visibility = View.INVISIBLE
        anonymously.visibility = View.INVISIBLE
        loading_login.visibility = View.VISIBLE
    }

    override fun endLoading() {
        authentication.visibility = View.VISIBLE
        anonymously.visibility = View.VISIBLE
        loading_login.visibility = View.GONE
    }
}
