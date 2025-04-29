package student.bhaskarbathukas3463805.waterintake

import android.content.Context

object HydrationPrefs {

    private const val PREF_NAME = "HYDRATION_TRACKER_PREFS"
    private const val KEY_IS_USER_LOGGED = "IS_HYDRATION_USER_LOGGED"
    private const val KEY_PROFILE_NAME = "HYDRATION_PROFILE_NAME"
    private const val KEY_PROFILE_EMAIL = "HYDRATION_PROFILE_EMAIL"
    private const val KEY_PROFILE_PHOTO_URL = "HYDRATION_PROFILE_PHOTO_URL"
    private const val KEY_DAILY_TARGET_ML = "HYDRATION_DAILY_TARGET_ML"

    fun setUserLoggedIn(context: Context, status: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_IS_USER_LOGGED, status).apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_USER_LOGGED, false)
    }

    fun setProfileName(context: Context, name: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PROFILE_NAME, name).apply()
    }

    fun getProfileName(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PROFILE_NAME, "") ?: ""
    }

    fun setProfileEmail(context: Context, email: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PROFILE_EMAIL, email).apply()
    }

    fun getProfileEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PROFILE_EMAIL, "") ?: ""
    }

    fun setProfilePhotoUrl(context: Context, url: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PROFILE_PHOTO_URL, url).apply()
    }

    fun getProfilePhotoUrl(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PROFILE_PHOTO_URL, "") ?: ""
    }

    fun setDailyWaterGoal(context: Context, goalInMl: Float) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(KEY_DAILY_TARGET_ML, goalInMl).apply()
    }

    fun getDailyWaterGoal(context: Context): Float {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_DAILY_TARGET_ML, 0f)
    }
}
