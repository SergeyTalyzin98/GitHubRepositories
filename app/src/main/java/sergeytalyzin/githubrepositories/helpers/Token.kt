package sergeytalyzin.githubrepositories.helpers

import android.content.Context

object Token {

    private const val appPreferences = "appPreferences"
    private const val appPreferencesToken = "token"

    const val ANONYMOUS = "anonymous"

    fun saveToken(token: String, context: Context) {
        context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE)
            .edit().putString(appPreferencesToken, token).apply()
    }

    fun deleteToken(context: Context) {
        context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE)
            .edit().remove(appPreferencesToken).apply()
    }

    fun getTokenFromSharedPreferences(context: Context) : String {
        val mPreferences = context.getSharedPreferences(appPreferences, Context.MODE_PRIVATE)
        if(mPreferences.contains(appPreferencesToken))
            return mPreferences.getString(appPreferencesToken, "")!!
        return ""
    }
}