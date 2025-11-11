package com.curie.curie.ui.screens.carreira.temperamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TipoComportamentoApi
import com.curie.curie.data.api.TipoTemperamentoApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.TipoComportamento
import com.curie.curie.data.model.TipoTemperamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.java

class TipoTemperamentoViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val tipoTemperamentoApi = RetrofitClient.createService(
        TipoTemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    // 🔹 Estados observáveis
    private val _tiposTemperamento = MutableStateFlow<List<TipoTemperamento>>(emptyList())
    val tiposTemperamento: StateFlow<List<TipoTemperamento>> = _tiposTemperamento

    private val _tipoTemperamento = MutableStateFlow<TipoTemperamento?>(null)
    val tipoTemperamento: StateFlow<TipoTemperamento?> = _tipoTemperamento

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 🔹 Buscar todos os tipos de comportamento
    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = tipoTemperamentoApi.getAll().execute()
                if (response.isSuccessful) {
                    _tiposTemperamento.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar tipos de Temperamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tipos de Temperamento: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Buscar tipo de comportamento por ID
    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = tipoTemperamentoApi.getById(id).execute()
                if (response.isSuccessful) {
                    _tipoTemperamento.value = response.body()
                } else {
                    _error.value = "Erro ao buscar tipo de Temperamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tipo de Temperamento: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
