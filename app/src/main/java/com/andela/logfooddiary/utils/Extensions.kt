package com.andela.logfooddiary.utils

import android.content.Context
import android.content.SharedPreferences

const val IS_LOGGED_IN = "isLogged_in"

fun Boolean.persistToSharedPrefs(context: Context?, key: String) {
    context?.let {
        getPreferences(context, Prefs.PREFERENCES)
                .edit()
                .putBoolean(key, this)
                .apply()
    }
}

fun String.getBooleanPrefValue(context: Context?): Boolean? {
    context?.let {
        return getPreferences(context, Prefs.PREFERENCES)
                .getBoolean(this, false)
    }
    return true
}

private fun getPreferences(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

object Prefs {
    const val PREFERENCES = "Preferences_PREF"
}