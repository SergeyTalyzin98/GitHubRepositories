package sergeytalyzin.githubrepositories.helpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sergeytalyzin.githubrepositories.interfeces.GitHubApi

class RetrofitHelper(private val baseUrl: String) {

    val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubApi::class.java)

    fun getToken(clientId: String, clientSecret: String, code: String) =
        api.getAccessToken(clientId = clientId, clientSecret = clientSecret, code = code)

    fun getRepositories(query: String) = api.getRepositories(query)
}