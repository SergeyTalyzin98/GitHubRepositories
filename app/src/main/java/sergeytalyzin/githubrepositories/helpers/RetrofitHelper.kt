package sergeytalyzin.githubrepositories.helpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sergeytalyzin.githubrepositories.interfeces.GitHubApi

class RetrofitHelper {

    val api = Retrofit.Builder()
        .baseUrl("https://github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubApi::class.java)

    fun getToken(clientId: String, clientSecret: String, code: String) =
        api.getAccessToken(clientId = clientId, clientSecret = clientSecret, code = code)
}