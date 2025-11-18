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

    // 🚨 NOVO: Fluxo reativo para IDs de Áreas Recomendadas
    private val _areasIdsFlow = MutableStateFlow<List<Long>?>(getAreasIds())
    val areasIdsFlow: StateFlow<List<Long>?> = _areasIdsFlow

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

    // 🔹 Mapeamento de área → ID
    val areaEducacionalIds = mapOf(
        "Ciências Humanas" to 1L,
        "Matemática e suas Tecnologias" to 2L,
        "Ciências da Natureza" to 3L,
        "Linguagens e Códigos" to 4L
    )

    // 🔸 EDUCAÇÃO — Força e Fraqueza (corrigido)

    fun saveForcaEducacionalId(id: Long) {
        prefs.edit().putLong("forcaEducacionalId", id).apply()
    }

    fun getForcaEducacionalId(): Long? {
        val id = prefs.getLong("forcaEducacionalId", -1L)
        return if (id == -1L) null else id
    }

    fun saveFraquezaEducacionalId(id: Long) {
        prefs.edit().putLong("fraquezaEducacionalId", id).apply()
    }

    fun getFraquezaEducacionalId(): Long? {
        val id = prefs.getLong("fraquezaEducacionalId", -1L)
        return if (id == -1L) null else id
    }

    fun saveAreasRecomendadas(areas: List<String>) {
        val serialized = areas.joinToString("|") // transforma em string
        prefs.edit().putString("areas_recomendadas", serialized).apply()
    }

    fun getAreasRecomendadas(): List<String> {
        val saved = prefs.getString("areas_recomendadas", "") ?: ""
        if (saved.isBlank()) return emptyList()
        return saved.split("|")
    }

    fun saveGraduacoes(graduacoes: List<String>) {
        val serialized = graduacoes.joinToString("|")
        prefs.edit().putString("graduacoes", serialized).apply()
    }

    fun getGraduacoes(): List<String> {
        val saved = prefs.getString("graduacoes", "") ?: ""
        if (saved.isBlank()) return emptyList()
        return saved.split("|")
    }

    fun savePosGraduacoes(pos: List<String>) {
        val serialized = pos.joinToString("|")
        prefs.edit().putString("pos_graduacoes", serialized).apply()
    }

    fun getPosGraduacoes(): List<String> {
        val saved = prefs.getString("pos_graduacoes", "") ?: ""
        if (saved.isBlank()) return emptyList()
        return saved.split("|")
    }

    fun saveProfissoes(profissoes: List<String>) {
        val serialized = profissoes.joinToString("|")
        prefs.edit().putString("profissoes", serialized).apply()
    }

    fun getProfissoes(): List<String> {
        val saved = prefs.getString("profissoes", "") ?: ""
        if (saved.isBlank()) return emptyList()
        return saved.split("|")
    }

    // =================================================================
    // 🚨 NOVO: Persistência dos IDs de Áreas de Carreira Recomendadas
    // Usado pelo CarreiraViewModel no Dashboard para filtrar dados.
    // =================================================================

    fun saveAreasIds(ids: List<Long>) {
        // SharedPreferences só salva Set<String>. Convertemos Long para String
        val serialized = ids.map { it.toString() }.toSet()
        prefs.edit().putStringSet("areas_recomendadas_ids", serialized).apply()
        _areasIdsFlow.value = ids // Notifica o StateFlow
    }

    fun getAreasIds(): List<Long> {
        val savedSet = prefs.getStringSet("areas_recomendadas_ids", emptySet()) ?: emptySet()
        if (savedSet.isEmpty()) return emptyList()
        // Converte Set<String> de volta para List<Long>
        return savedSet.mapNotNull { it.toLongOrNull() }
    }

    // 🔁 Limpeza
    fun clearAll() {
        prefs.edit().clear().apply()
        _temperamentoIdFlow.value = null
        _comportamentoIdFlow.value = null
    }
}