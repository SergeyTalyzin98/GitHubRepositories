package sergeytalyzin.githubrepositories.interfeces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import sergeytalyzin.githubrepositories.helpers.AccessToken

interface GitHubApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String) : Call<AccessToken>
}