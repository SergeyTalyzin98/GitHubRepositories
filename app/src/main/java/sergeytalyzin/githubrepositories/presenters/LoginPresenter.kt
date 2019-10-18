package sergeytalyzin.githubrepositories.presenters

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sergeytalyzin.githubrepositories.R
import sergeytalyzin.githubrepositories.helpers.AccessToken
import sergeytalyzin.githubrepositories.helpers.RetrofitHelper
import sergeytalyzin.githubrepositories.helpers.Token
import sergeytalyzin.githubrepositories.views.LoginView

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {

    val urlForLogin = "https://github.com/login/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI"

    companion object {
        const val CLIENT_ID = "93413a8b2e0cf1400b67"
        const val CLIENT_SECRET = "c4a06197158f856dd2c03079d1e0eafed782fe16"
        const val REDIRECT_URI = "googlecom://callback"
    }

    fun startRepository() {
        viewState.saveToken(Token.ANONYMOUS)
        viewState.startRepositoryActivity()
    }

    fun login() {
        viewState.startLoading()
        viewState.startLoginProcess()
    }

    fun getToken(uri: Uri) {

        val token = RetrofitHelper(baseUrl = "https://github.com/").getToken(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            code = uri.getQueryParameter("code")!!
        )

        token.enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                try {
                    viewState.saveToken(response.body()!!.accessToken!!)
                    viewState.startRepositoryActivity()
                }
                catch (E: Exception) {
                    viewState.showError(R.string.error_login)
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                if(t.message != null)
                    viewState.showError(t.message!!)
                else
                    viewState.showError(R.string.error_login)
                viewState.endLoading()
            }
        })
    }
}