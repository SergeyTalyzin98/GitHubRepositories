package sergeytalyzin.githubrepositories.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import sergeytalyzin.githubrepositories.helpers.Repository

interface RepositoriesView: MvpView {
    fun startLoginActivity()
    fun startLoading()
    fun endLoading()
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error: String)
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error: Int)
    fun setListAdapter(list: List<Repository.Item>)
    fun clearList()
}