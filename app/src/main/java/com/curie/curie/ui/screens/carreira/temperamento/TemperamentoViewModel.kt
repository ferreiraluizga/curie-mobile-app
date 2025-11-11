package com.curie.curie.ui.screens.carreira.temperamento

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TemperamentoApi
import com.curie.curie.data.api.TipoTemperamentoApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Temperamento
import com.curie.curie.data.model.TipoTemperamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TemperamentoViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val temperamentoApi = RetrofitClient.createService(
        TemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoTemperamentoApi = RetrofitClient.createService(
        TipoTemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val _temperamentos = MutableStateFlow<List<Temperamento>>(emptyList())
    val temperamentos: StateFlow<List<Temperamento>> = _temperamentos

    private val _temperamento = MutableStateFlow<Temperamento?>(null)
    val temperamento: StateFlow<Temperamento?> = _temperamento

    private val _resultado = MutableStateFlow<TipoTemperamento?>(null)
    val resultado: StateFlow<TipoTemperamento?> = _resultado

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 🔹 Buscar temperamento por ID
    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = temperamentoApi.getById(id).execute()
                if (response.isSuccessful) {
                    _temperamento.value = response.body()
                } else {
                    _error.value = "Erro ao buscar temperamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar temperamento: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Salvar novo temperamento
    fun save(temperamento: Temperamento) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = temperamentoApi.save(temperamento).execute()
                if (response.isSuccessful) {
                    _temperamento.value = response.body()
                } else {
                    _error.value = "Erro ao salvar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao salvar: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Excluir temperamento
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = temperamentoApi.delete(id).execute()
                if (response.isSuccessful) {
                    _temperamentos.value = _temperamentos.value.filterNot { it.id == id }
                } else {
                    _error.value = "Erro ao excluir: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao excluir: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Finalizar teste — cria e salva o resultado do temperamento
    fun finalizarTeste(resultadoNome: String, maiorDesempenho: String, menorDesempenho: String, onResult: (Long?) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("TemperamentoVM", "Iniciando finalizarTeste para resultado: $resultadoNome")

                val tiposResponse = tipoTemperamentoApi.getAll().execute()
                if (tiposResponse.isSuccessful) {
                    val tipos = tiposResponse.body() ?: emptyList()
                    val tipo = tipos.find { it.nome.equals(resultadoNome, ignoreCase = true) }

                    if (tipo != null) {
                        val novoTemperamento = Temperamento(
                            id = null,
                            tipoTemperamentoId = tipo.id,
                            forcaAprendizado = maiorDesempenho,
                            fraquezaAprendizado = menorDesempenho
                        )

                        Log.d("TemperamentoVM", "Salvando temperamento: $novoTemperamento")

                        val saveResponse = temperamentoApi.save(novoTemperamento).execute()
                        if (saveResponse.isSuccessful) {
                            val salvo = saveResponse.body()
                            Log.d("TemperamentoVM", "✅ Temperamento salvo com sucesso: $salvo")
                            _temperamento.value = salvo
                            _resultado.value = tipo
                            withContext(Dispatchers.Main) { onResult(salvo?.id) }
                        } else {
                            val errorMsg = saveResponse.errorBody()?.string()
                            Log.e("TemperamentoVM", "❌ Erro ao salvar: ${saveResponse.code()} - $errorMsg")
                            _error.value = "Erro ao salvar: ${saveResponse.code()}"
                            withContext(Dispatchers.Main) { onResult(null) }
                        }
                    } else {
                        Log.e("TemperamentoVM", "⚠️ Tipo de temperamento não encontrado: $resultadoNome")
                        _error.value = "Tipo de temperamento não encontrado: $resultadoNome"
                        withContext(Dispatchers.Main) { onResult(null) }
                    }
                } else {
                    _error.value = "Erro ao buscar tipos: ${tiposResponse.code()}"
                    withContext(Dispatchers.Main) { onResult(null) }
                }
            } catch (e: Exception) {
                Log.e("TemperamentoVM", "💥 Falha ao finalizar teste", e)
                _error.value = "Falha ao finalizar teste: ${e.message}"
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }
}
