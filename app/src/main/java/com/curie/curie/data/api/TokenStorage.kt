package com.curie.curie.data.api

import android.content.Context

class TokenStorage(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)

    fun clear() {
        prefs.edit().remove("jwt_token").apply()
    }
}
