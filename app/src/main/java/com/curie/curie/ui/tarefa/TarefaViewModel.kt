package com.curie.curie.ui.tarefa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TarefaApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TarefaViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val tarefaApi = RetrofitClient.createService(TarefaApi::class.java) { tokenStorage.getToken() }

    private val _tarefas = MutableStateFlow<List<Tarefa>>(emptyList())
    val tarefas: StateFlow<List<Tarefa>> = _tarefas

    private val _tarefa = MutableStateFlow<Tarefa?>(null)
    val tarefa: StateFlow<Tarefa?> = _tarefa

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun save(tarefa: Tarefa) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.save(tarefa).execute()
                if (response.isSuccessful) {
                    _tarefa.value = response.body()
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

    fun getById(id: Long) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.getById(id).execute()
                if (response.isSuccessful) {
                    _tarefa.value = response.body()
                } else {
                    _error.value = "Erro ao buscar tarefa: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tarefa: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getByUsuario(userId: Long) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.getByUsuario(userId).execute()
                if (response.isSuccessful) {
                    _tarefas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ao buscar tarefas do usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar tarefas do usuário: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getByNome(userId: Long, nome: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.getByNome(userId, nome).execute()
                if (response.isSuccessful) {
                    _tarefas.value = response.body() ?: emptyList()
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
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.getByPrazo(userId).execute()
                if (response.isSuccessful) {
                    _tarefas.value = response.body() ?: emptyList()
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
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.getByPrioridade(userId).execute()
                if (response.isSuccessful) {
                    _tarefas.value = response.body() ?: emptyList()
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

    fun update(id: Long?, tarefa: Tarefa) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.update(id, tarefa).execute()
                if (response.isSuccessful) {
                    _tarefa.value = response.body()
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

    fun delete(id: Long?) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tarefaApi.delete(id).execute()
                if (response.isSuccessful) {
                    _tarefa.value = null
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
}
