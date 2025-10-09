package com.curie.curie.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.AuthApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val tokenStorage: TokenStorage // Injetado (será explicado no Passo 4)
) : ViewModel() {

    // 1. Cria a API de autenticação. O tokenProvider é passado como `{ null }`
    //    porque a chamada de login NÃO PRECISA de um token de autorização.
    private val authApi = RetrofitClient.createService(AuthApi::class.java) { null }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val userId: Long) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    fun login(request: LoginRequest) {
        _authState.value = AuthState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Executa a chamada síncrona dentro da coroutine de IO
                val response = authApi.login(request).execute()

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        // 2. Ação CRÍTICA: Salvar o token para uso futuro
                        tokenStorage.saveToken(loginResponse.token)
                        _authState.value = AuthState.Success(loginResponse.userId)
                    } else {
                        _authState.value = AuthState.Error("Resposta do servidor vazia.")
                    }
                } else {
                    // Trata códigos de erro HTTP (ex: 401 Unauthorized)
                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido."
                    _authState.value = AuthState.Error("Falha no Login: Código ${response.code()}. Erro: $errorBody")
                }
            } catch (e: Exception) {
                // Trata erros de rede (ex: servidor offline)
                _authState.value = AuthState.Error("Falha de conexão: ${e.message}")
            }
        }
    }

    fun logout() {
        tokenStorage.clear()
        _authState.value = AuthState.Idle
    }
}