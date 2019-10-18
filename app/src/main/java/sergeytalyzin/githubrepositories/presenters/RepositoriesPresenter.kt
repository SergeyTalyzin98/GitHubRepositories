package sergeytalyzin.githubrepositories.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
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

    fun getRepositories(query: String) {

        if(query != "") {

            viewState.startLoading()

            val r = RetrofitHelper(baseUrl = "https://api.github.com/").getRepositories(query = query)

            r.enqueue(object : Callback<Repository> {

                override fun onResponse(call: Call<Repository>, response: Response<Repository>) {

                    if (response.body() != null) {
                        viewState.setListAdapter(response.body()!!.items!!)
                        viewState.endLoading()
                    }
                    else {
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
        else
            viewState.clearList()
    }
}


