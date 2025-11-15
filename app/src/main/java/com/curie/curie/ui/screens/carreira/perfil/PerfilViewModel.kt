package com.curie.curie.ui.screens.carreira.perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.PerfilApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val perfilApi = RetrofitClient.createService(PerfilApi::class.java) { tokenStorage.getToken() }

    private val _perfil = MutableStateFlow<Perfil?>(null)
    val perfil: StateFlow<Perfil?> = _perfil

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ======================
    // CRUD principal
    // ======================

    fun save(perfil: Perfil) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("PerfilViewModel", "Enviando perfil para salvar: $perfil")
                val response = perfilApi.save(perfil).execute()
                if (response.isSuccessful) {
                    val perfilSalvo = response.body()
                    Log.d("PerfilViewModel", "✅ Perfil salvo com sucesso: $perfilSalvo")
                    _perfil.value = perfilSalvo
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("PerfilViewModel", "❌ Erro ao salvar perfil: ${response.code()} - $errorBody")
                    _error.value = "Erro ao salvar perfil: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "💥 Falha ao salvar perfil", e)
                _error.value = "Falha ao salvar perfil: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("PerfilViewModel", "Buscando perfil com ID: $id")
                val response = perfilApi.getById(id).execute()
                if (response.isSuccessful) {
                    _perfil.value = response.body()
                    Log.d("PerfilViewModel", "📄 Perfil encontrado: ${response.body()}")
                } else {
                    Log.e("PerfilViewModel", "❌ Erro ao buscar perfil: ${response.code()}")
                    _error.value = "Erro ao buscar perfil: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "💥 Falha ao buscar perfil", e)
                _error.value = "Falha ao buscar perfil: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getMaisRecente(usuarioId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("PerfilViewModel", "Buscando perfil mais recente do usuário: $usuarioId")
                val response = perfilApi.getMaisRecente(usuarioId).execute()
                if (response.isSuccessful) {
                    _perfil.value = response.body()
                    Log.d("PerfilViewModel", "📄 Perfil mais recente: ${response.body()}")
                } else {
                    Log.e("PerfilViewModel", "❌ Erro ao buscar perfil mais recente: ${response.code()}")
                    _error.value = "Erro ao buscar perfil mais recente: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "💥 Falha ao buscar perfil mais recente", e)
                _error.value = "Falha ao buscar perfil mais recente: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("PerfilViewModel", "Deletando perfil com ID: $id")
                val response = perfilApi.delete(id).execute()
                if (response.isSuccessful) {
                    Log.d("PerfilViewModel", "🗑️ Perfil deletado com sucesso")
                    _perfil.value = null
                } else {
                    Log.e("PerfilViewModel", "❌ Erro ao deletar perfil: ${response.code()}")
                    _error.value = "Erro ao deletar perfil: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "💥 Falha ao deletar perfil", e)
                _error.value = "Falha ao deletar perfil: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun update(id: Long, perfil: Perfil) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = perfilApi.update(id, perfil).execute()
                if (response.isSuccessful) {
                    _perfil.value = response.body()
                } else {
                    _error.value = "Erro ao atualizar perfil: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao atualizar perfil: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

}
