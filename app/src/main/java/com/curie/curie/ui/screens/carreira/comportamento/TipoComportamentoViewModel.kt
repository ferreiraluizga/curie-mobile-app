package com.curie.curie.ui.screens.carreira.comportamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TipoComportamentoApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.TipoComportamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TipoComportamentoViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val tipoComportamentoApi = RetrofitClient.createService(
        TipoComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    // 🔹 Estados observáveis
    private val _tiposComportamento = MutableStateFlow<List<TipoComportamento>>(emptyList())
    val tiposComportamento: StateFlow<List<TipoComportamento>> = _tiposComportamento

    private val _tipoComportamento = MutableStateFlow<TipoComportamento?>(null)
    val tipoComportamento: StateFlow<TipoComportamento?> = _tipoComportamento

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
                val response = tipoComportamentoApi.getAll().execute()
                if (response.isSuccessful) {
                    _tiposComportamento.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar tipos de comportamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tipos de comportamento: ${e.message}"
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
                val response = tipoComportamentoApi.getById(id).execute()
                if (response.isSuccessful) {
                    _tipoComportamento.value = response.body()
                } else {
                    _error.value = "Erro ao buscar tipo de comportamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tipo de comportamento: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
