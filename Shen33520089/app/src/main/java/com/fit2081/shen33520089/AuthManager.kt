package com.fit2081.shen33520089

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * AuthManager is a singleton object that manages user authentication state.
 */
object AuthManager {
    private const val PREFS_NAME = "AuthPrefs"
    private const val KEY_USER_ID = "userId"

    val _userId: MutableState<String?> = mutableStateOf(null)


    fun login(context: Context, userId: String) {
        _userId.value = userId
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun logout(context: Context) {
        _userId.value = null
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    fun getUserId(): String? {
        return _userId.value
    }

    fun isLoggedIn(): Boolean {
        return _userId.value != null
    }

    fun loadUserFromPrefs(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _userId.value = prefs.getString(KEY_USER_ID, null)
    }
}