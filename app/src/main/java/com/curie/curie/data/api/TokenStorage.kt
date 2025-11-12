package com.curie.curie.data.api

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TokenStorage(context: Context) {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // 🔹 Fluxos reativos (StateFlow)
    private val _temperamentoIdFlow = MutableStateFlow<Long?>(getTemperamentoId())
    val temperamentoIdFlow: StateFlow<Long?> = _temperamentoIdFlow

    private val _comportamentoIdFlow = MutableStateFlow<Long?>(getComportamentoId())
    val comportamentoIdFlow: StateFlow<Long?> = _comportamentoIdFlow

    // 🧠 Tokens e IDs de usuário
    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? = prefs.getString("token", null)

    fun saveUserId(userId: Long) {
        prefs.edit().putLong("userId", userId).apply()
    }

    fun getUserId(): Long? {
        val id = prefs.getLong("userId", -1L)
        return if (id == -1L) null else id
    }

    // 🔸 IDs dos testes
    fun saveTemperamentoId(id: Long) {
        prefs.edit().putLong("temperamentoId", id).apply()
        _temperamentoIdFlow.value = id
    }

    fun saveComportamentoId(id: Long) {
        prefs.edit().putLong("comportamentoId", id).apply()
        _comportamentoIdFlow.value = id
    }

    fun getTemperamentoId(): Long? {
        val id = prefs.getLong("temperamentoId", -1L)
        return if (id == -1L) null else id
    }

    fun getComportamentoId(): Long? {
        val id = prefs.getLong("comportamentoId", -1L)
        return if (id == -1L) null else id
    }

    // 🔁 Limpeza
    fun clearAll() {
        prefs.edit().clear().apply()
        _temperamentoIdFlow.value = null
        _comportamentoIdFlow.value = null
    }
}
