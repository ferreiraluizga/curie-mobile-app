package com.curie.curie.data.api

import android.content.Context

class TokenStorage(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // ------------------------
    // TOKEN E USUÁRIO
    // ------------------------
    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)

    fun saveUserId(userId: Long) {
        prefs.edit().putLong("user_id", userId).apply()
    }

    fun getUserId(): Long? {
        return if (prefs.contains("user_id"))
            prefs.getLong("user_id", -1).takeIf { it != -1L }
        else null
    }

    // ------------------------
    // TESTE DE COMPORTAMENTO
    // ------------------------
    fun saveComportamentoId(id: Long) {
        prefs.edit().putLong("comportamento_id", id).apply()
    }

    fun getComportamentoId(): Long? {
        return if (prefs.contains("comportamento_id"))
            prefs.getLong("comportamento_id", -1).takeIf { it != -1L }
        else null
    }

    // ------------------------
    // TESTE DE TEMPERAMENTO
    // ------------------------
    fun saveTemperamentoId(id: Long) {
        prefs.edit().putLong("temperamento_id", id).apply()
    }

    fun getTemperamentoId(): Long? {
        return if (prefs.contains("temperamento_id"))
            prefs.getLong("temperamento_id", -1).takeIf { it != -1L }
        else null
    }

    // ------------------------
    // LIMPAR TUDO
    // ------------------------
    fun clear() {
        prefs.edit()
            .remove("jwt_token")
            .remove("user_id")
            .remove("comportamento_id")
            .remove("temperamento_id")
            .apply()
    }
}