package com.curie.curie.ui.screens.carreira.analise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.CarreiraApi
import com.curie.curie.data.api.GraduacaoApi
import com.curie.curie.data.api.PerfilApi
import com.curie.curie.data.api.PosGraduacaoApi
import com.curie.curie.data.api.ProfissaoApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Carreira
import com.curie.curie.data.model.Graduacao
import com.curie.curie.data.model.Perfil
import com.curie.curie.data.model.PosGraduacao
import com.curie.curie.data.model.Profissao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarreiraViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val carreiraApi = RetrofitClient.createService(
        CarreiraApi::class.java
    ) { tokenStorage.getToken() }

    private val graduacaoApi = RetrofitClient.createService(
        GraduacaoApi::class.java
    ) { tokenStorage.getToken() }

    private val posGraduacaoApi = RetrofitClient.createService(
        PosGraduacaoApi::class.java
    ) { tokenStorage.getToken() }

    private val profissaoApi = RetrofitClient.createService(
        ProfissaoApi::class.java
    ) { tokenStorage.getToken() }


    private val _carreiras = MutableStateFlow<List<Carreira>>(emptyList())
    val carreiras: StateFlow<List<Carreira>> = _carreiras

    private val _carreira = MutableStateFlow<Carreira?>(null)
    val carreira: StateFlow<Carreira?> = _carreira

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ======================
    // CRUD principal
    // ======================

    fun save(carreira: Carreira) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null

            try {
                Log.d("CarreiraViewModel", "Enviando carreira para salvar: $carreira")

                val response = carreiraApi.save(carreira).execute()

                if (response.isSuccessful) {
                    val carreiraSalva = response.body()
                    Log.d("CarreiraViewModel", "✅ Carreira salva: $carreiraSalva")
                    _carreira.value = carreiraSalva
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CarreiraViewModel", "❌ Erro ao salvar carreira: ${response.code()} - $errorBody")
                    _error.value = "Erro ao salvar carreira: ${response.code()}"
                }

            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao salvar carreira", e)
                _error.value = "Falha ao salvar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // ---------------------------------------------------------
    // 🔹 BUSCAR POR ID
    // ---------------------------------------------------------
    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            _loading.value = true
            _error.value = null

            try {
                Log.d("CarreiraViewModel", "Buscando carreira com ID: $id")

                val response = carreiraApi.getById(id).execute()

                if (response.isSuccessful) {
                    _carreira.value = response.body()
                    Log.d("CarreiraViewModel", "📄 Carreira encontrada: ${response.body()}")
                } else {
                    Log.e("CarreiraViewModel", "❌ Erro ao buscar carreira: ${response.code()}")
                    _error.value = "Erro ao buscar carreira: ${response.code()}"
                }

            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao buscar carreira", e)
                _error.value = "Falha ao buscar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // ---------------------------------------------------------
    // 🔹 DELETAR
    // ---------------------------------------------------------
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            _loading.value = true
            _error.value = null

            try {
                Log.d("CarreiraViewModel", "Deletando carreira com ID: $id")

                val response = carreiraApi.delete(id).execute()

                if (response.isSuccessful) {
                    Log.d("CarreiraViewModel", "🗑️ Carreira deletada com sucesso")
                    _carreira.value = null
                } else {
                    Log.e("CarreiraViewModel", "❌ Erro ao deletar carreira: ${response.code()}")
                    _error.value = "Erro ao deletar carreira: ${response.code()}"
                }

            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao deletar carreira", e)
                _error.value = "Falha ao deletar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
