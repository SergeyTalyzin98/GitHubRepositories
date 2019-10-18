package sergeytalyzin.githubrepositories.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LoginView: MvpView {
    fun startLoading()
    fun endLoading()
    fun startRepositoryActivity()
    fun startLoginProcess()
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error: String)
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error: Int)
    fun saveToken(token: String)
}