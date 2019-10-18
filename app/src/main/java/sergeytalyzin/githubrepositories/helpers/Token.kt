package sergeytalyzin.githubrepositories.helpers

import android.content.Context

object Token {

    // Этот объект работает с токеном
    // Токен хранится в SharedPreferences
    // Если пользователь выбирает анонимный вход,
    // вместе токена сохраняется "anonymous".
    // Это используется для того,
    // что бы пользователю, при запуске приложения постоянно не показывался экран логина,
    // при необходимости пользователь может на экране поиска войти либо выйти.

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