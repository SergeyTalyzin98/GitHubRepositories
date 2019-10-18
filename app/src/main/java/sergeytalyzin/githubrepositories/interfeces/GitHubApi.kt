package sergeytalyzin.githubrepositories.interfeces

import retrofit2.Call
import retrofit2.http.*
import sergeytalyzin.githubrepositories.helpers.AccessToken
import sergeytalyzin.githubrepositories.helpers.Repository

interface GitHubApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String) : Call<AccessToken>

    @GET("search/repositories")
    fun getRepositories(@Query("q") query: String) : Call<Repository>
}