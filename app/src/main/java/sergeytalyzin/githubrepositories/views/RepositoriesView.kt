package sergeytalyzin.githubrepositories.views

import com.arellomobile.mvp.MvpView
import sergeytalyzin.githubrepositories.helpers.Repository

interface RepositoriesView: MvpView {
    fun startLoginActivity()
    fun startLoading()
    fun endLoading()
    fun showError(m: String)
    fun setListAdapter(list: List<Repository.Item>)
    fun clearList()
}