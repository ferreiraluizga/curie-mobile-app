package com.curie.curie.ui.screens.carreira.analise

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.CarreiraApi
import com.curie.curie.data.api.ComportamentoApi
import com.curie.curie.data.api.GraduacaoApi
import com.curie.curie.data.api.PerfilApi
import com.curie.curie.data.api.PosGraduacaoApi
import com.curie.curie.data.api.ProfissaoApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TemperamentoApi
import com.curie.curie.data.api.TipoComportamentoApi
import com.curie.curie.data.api.TipoTemperamentoApi
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

    private val comportamentoApi = RetrofitClient.createService(
        ComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val temperamentoApi = RetrofitClient.createService(
        TemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoComportamentoApi = RetrofitClient.createService(
        TipoComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoTemperamentoApi = RetrofitClient.createService(
        TipoTemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val _carreiras = MutableStateFlow<List<Carreira>>(emptyList())
    val carreiras: StateFlow<List<Carreira>> = _carreiras

    private val _carreira = MutableStateFlow<Carreira?>(null)
    val carreira: StateFlow<Carreira?> = _carreira

    private val _graduacoes = MutableStateFlow<List<Graduacao>>(emptyList())
    val graduacoes: StateFlow<List<Graduacao>> = _graduacoes

    private val _posGraduacoes = MutableStateFlow<List<PosGraduacao>>(emptyList())
    val posGraduacoes: StateFlow<List<PosGraduacao>> = _posGraduacoes

    private val _profissoes = MutableStateFlow<List<Profissao>>(emptyList())
    val profissoes: StateFlow<List<Profissao>> = _profissoes

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
                val comportamentoId = tokenStorage.getComportamentoId()
                val temperamentoId = tokenStorage.getTemperamentoId()

                val comportamento = comportamentoId?.let {
                    comportamentoApi.getById(it).execute().body()
                }

                val tipoComportamento = comportamento?.let {
                    tipoComportamentoApi.getById(it.tipoComportamentoId).execute().body()
                }

                val temperamento = temperamentoId?.let {
                    temperamentoApi.getById(it).execute().body()
                }

                val tipoTemperamento = temperamento?.let {
                    tipoTemperamentoApi.getById(it.tipoTemperamentoId).execute().body()
                }

                val areas = tokenStorage.getAreasRecomendadas()
                val graduacoes = tokenStorage.getGraduacoes()
                val pos = tokenStorage.getPosGraduacoes()
                val profs = tokenStorage.getProfissoes()

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

    fun loadAllForAreas(areaIds: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {

            _loading.value = true
            _error.value = null

            try {
                val g = graduacaoApi.getAll().execute()
                val pg = posGraduacaoApi.getAll().execute()

                if (g.isSuccessful) {
                    val todas = g.body() ?: emptyList()

                    val filtradas = todas.filter { grad ->
                        areaIds.contains(grad.areaCarreiraId)
                    }

                    _graduacoes.value = filtradas

                    // salvar lista na sessão
                    tokenStorage.saveGraduacoes(
                        filtradas.map { it.nome }
                    )
                }

                if (pg.isSuccessful) {
                    val todas = pg.body() ?: emptyList()

                    val filtradas = todas.filter { pos ->
                        areaIds.contains(pos.areaCarreiraId)
                    }

                    _posGraduacoes.value = filtradas

                    // salvar lista na sessão
                    tokenStorage.savePosGraduacoes(
                        filtradas.map { it.nome }
                    )
                }

            } catch (e: Exception) {
                _error.value = "Erro ao carregar dados: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadAllProfissoes() {
        viewModelScope.launch(Dispatchers.IO) {

            _loading.value = true
            _error.value = null

            try {
                val response = profissaoApi.getAll().execute()

                if (response.isSuccessful) {
                    val todas = response.body() ?: emptyList()

                    _profissoes.value = todas

                    // Salvar nomes das profissões na sessão
                    tokenStorage.saveProfissoes(
                        todas.map { it.nome }
                    )

                } else {
                    _error.value = "Erro ao listar profissões: ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value = "Erro ao carregar profissões: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


}
