package com.curie.curie.ui.screens.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.api.UserApi
import com.curie.curie.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val userApi = RetrofitClient.createService(UserApi::class.java) { tokenStorage.getToken() }

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ======================
    // CRUD
    // ======================

    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = userApi.getById(id).execute()
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _error.value = "Erro ao buscar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar usuário: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun update(id: Long, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = userApi.update(id, user).execute()
                if (response.isSuccessful) {
                    _user.value = response.body()
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
                val response = userApi.delete(id).execute()
                if (!response.isSuccessful) {
                    _error.value = "Erro ao excluir: ${response.code()}"
                } else {
                    _user.value = null
                }
            } catch (e: Exception) {
                _error.value = "Falha ao excluir: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
