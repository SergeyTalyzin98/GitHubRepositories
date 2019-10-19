package sergeytalyzin.githubrepositories.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sergeytalyzin.githubrepositories.R
import sergeytalyzin.githubrepositories.helpers.Repository
import sergeytalyzin.githubrepositories.helpers.RetrofitHelper
import sergeytalyzin.githubrepositories.views.RepositoriesView

@InjectViewState
class RepositoriesPresenter: MvpPresenter<RepositoriesView>() {

    // false поиск открыт, true нет
    var searchViewIsIconified = true
    var queryText: String = ""
    var waitNewData = false
    private lateinit var wait: Job

    private fun waitingNewData() = GlobalScope.launch(Dispatchers.Main) {
        waitNewData = true
        delay(1000)
        getRepositories(query = queryText)
        waitNewData = false
    }

    fun setQuery(query: String) {

        if(query != "") {

            viewState.startLoading()
            queryText = query

            if(waitNewData) {
                wait.cancel()
                wait = waitingNewData()
            }
            else
                wait = waitingNewData()
        }
        else {
            wait.cancel()
            viewState.endLoading()
            viewState.clearList()
        }
    }

    private fun getRepositories(query: String) {

        val r = RetrofitHelper(baseUrl = "https://api.github.com/").getRepositories(query = query)

        r.enqueue(object : Callback<Repository> {

            override fun onResponse(call: Call<Repository>, response: Response<Repository>) {

                try {

                    val list = response.body()!!.items!!

                    if(list.isNotEmpty()) {
                        viewState.setListAdapter(list)
                        viewState.endLoading()
                    }
                    else {
                        viewState.endLoading()
                        viewState.clearList()
                        viewState.showError(R.string.nothing_found)
                    }
                }catch (e: Exception) {
                    viewState.endLoading()
                    viewState.clearList()
                    viewState.showError(R.string.nothing_found)
                }
            }

            override fun onFailure(call: Call<Repository>, t: Throwable) {
                viewState.showError(t.message!!)
                viewState.endLoading()
            }
        })
    }
}


