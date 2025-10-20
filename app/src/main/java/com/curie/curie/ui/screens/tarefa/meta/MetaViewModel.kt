package com.curie.curie.ui.screens.tarefa.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.MetaApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetaViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val metaApi = RetrofitClient.createService(MetaApi::class.java) { tokenStorage.getToken() }

    private val _metas = MutableStateFlow<List<Meta>>(emptyList())
    val metas: StateFlow<List<Meta>> = _metas

    private val _meta = MutableStateFlow<Meta?>(null)
    val meta: StateFlow<Meta?> = _meta

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ======================
    // CRUD principal
    // ======================

    fun getByUsuario(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.getByUsuario(userId).execute()
                if (response.isSuccessful) {
                    _metas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar tarefas: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tarefas: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun save(meta: Meta) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.save(meta).execute()
                if (response.isSuccessful) {
                    _meta.value = response.body()
                    getByUsuario(meta.userId)
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

    fun update(id: Long, meta: Meta) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.update(id, meta).execute()
                if (response.isSuccessful) {
                    _meta.value = response.body()
                    getByUsuario(meta.userId)
                } else {
                    _error.value = "Erro ao atualizar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao atualizar: ${e.message}"
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
                val response = metaApi.delete(id).execute()
                if (response.isSuccessful) {
                    _metas.value = _metas.value.filterNot { it.id == id }
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

    // ======================
    // Filtros e buscas
    // ======================

    fun getByNome(userId: Long, nome: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.getByNome(userId, nome).execute()
                if (response.isSuccessful) {
                    _metas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar por nome: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar por nome: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getByPrazo(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.getByPrazo(userId).execute()
                if (response.isSuccessful) {
                    _metas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar por prazo: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar por prazo: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getByPrioridade(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = metaApi.getByPrioridade(userId).execute()
                if (response.isSuccessful) {
                    _metas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar por prioridade: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar por prioridade: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
