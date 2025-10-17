package com.curie.curie.data.api

import android.content.Context

class TokenStorage(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)

    fun saveUserId(userId: Long) {
        prefs.edit().putLong("user_id", userId).apply()
    }

    fun getUserId(): Long? {
        return if (prefs.contains("user_id")) prefs.getLong("user_id", -1).takeIf { it != -1L } else null
    }


    fun clear() {
        prefs.edit().remove("jwt_token").apply()
    }
}
