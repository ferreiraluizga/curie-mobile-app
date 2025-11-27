package com.curie.curie.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.AuthApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.LoginRequest
import com.curie.curie.data.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    // Retrofit com injeção do token atual
    private val authApi = RetrofitClient.createService(AuthApi::class.java) {
        tokenStorage.getToken()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        object Idle : AuthState()                // Nenhuma ação em andamento
        object Loading : AuthState()             // Tentando logar
        object Authenticated : AuthState()       // Já logado (token presente)
        object Unauthenticated : AuthState()     // Precisa logar
        data class Success(val userId: Long) : AuthState() // Login bem-sucedido
        data class Error(val message: String) : AuthState() // Erro no login ou rede
    }

    init {
        // Ao iniciar, checa se há sessão salva
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenStorage.getToken()
            val userId = tokenStorage.getUserId()
            _authState.value = if (token.isNullOrEmpty() || userId == null) {
                AuthState.Unauthenticated
            } else {
                AuthState.Authenticated
            }
        }
    }

    fun login(request: LoginRequest) {
        _authState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authApi.login(request).execute()

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        // Salva o token localmente
                        tokenStorage.saveToken(loginResponse.token)
                        tokenStorage.saveUserId(loginResponse.userId)

                        // Atualiza o estado para sucesso + autenticado
                        _authState.value = AuthState.Success(loginResponse.userId)
                        _authState.value = AuthState.Authenticated
                    } else {
                        _authState.value = AuthState.Error("Resposta do servidor vazia.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido."
                    _authState.value = AuthState.Error(
                        "Falha no login (${response.code()}): $errorBody"
                    )
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Falha de conexão: ${e.message}")
            }
        }
    }

    fun register(request: RegisterRequest) {
        _authState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authApi.register(request).execute()

                if (response.isSuccessful) {
                    _authState.value = AuthState.Success(userId = -1L)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido."
                    _authState.value = AuthState.Error(
                        "Falha no registro (${response.code()}): $errorBody"
                    )
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Falha de conexão: ${e.message}")
            }
        }
    }

    fun restoreSession(userId: Long) {
        _authState.value = AuthState.Success(userId)
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenStorage.clearAll()
            _authState.value = AuthState.Unauthenticated
        }
    }
}