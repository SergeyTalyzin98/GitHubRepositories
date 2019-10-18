package sergeytalyzin.githubrepositories.views

import com.arellomobile.mvp.MvpView

interface LoginView: MvpView {
    fun startLoading()
    fun endLoading()
    fun startRepositoryActivity()
    fun startLoginProcess()
    fun showError(e: String)
    fun showError(e: Int)
    fun saveToken(token: String)
}