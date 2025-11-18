package com.curie.curie.ui.screens.carreira.vocacional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.AreaCarreiraApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.AreaCarreira
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AreaCarreiraViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val areaCarreiraApi = RetrofitClient.createService(
        AreaCarreiraApi::class.java
    ) { tokenStorage.getToken() }

    // 🔹 Estados observáveis
    private val _areasCarreira = MutableStateFlow<List<AreaCarreira>>(emptyList())
    val areasCarreira: StateFlow<List<AreaCarreira>> = _areasCarreira

    private val _areaCarreira = MutableStateFlow<AreaCarreira?>(null)
    val areaCarreira: StateFlow<AreaCarreira?> = _areaCarreira

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 🔹 Buscar todas as áreas de carreira
    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = areaCarreiraApi.getAll().execute()
                if (response.isSuccessful) {
                    _areasCarreira.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar áreas de carreira: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar áreas de carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Buscar área de carreira por ID
    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = areaCarreiraApi.getById(id).execute()
                if (response.isSuccessful) {
                    _areaCarreira.value = response.body()
                } else {
                    _error.value = "Erro ao buscar área de carreira: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar área de carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getIdsDasAreasRecomendadas(): List<Long> {
        val recomendadas = tokenStorage.getAreasRecomendadas()
        val todas = _areasCarreira.value

        return todas
            .filter { recomendadas.contains(it.nome) }
            .map { it.id }
    }

}