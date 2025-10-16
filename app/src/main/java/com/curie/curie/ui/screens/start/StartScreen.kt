package com.curie.curie.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.LoginRequest
import com.curie.curie.ui.screens.start.HomeScreen
import com.curie.curie.ui.screens.start.SplashScreen

@Composable
fun StartScreen(
    tokenStorage: TokenStorage,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(tokenStorage))
) {
    val state by authViewModel.authState.collectAsState()

    when (val auth = state) {
        is AuthViewModel.AuthState.Loading -> {
            // Exibe tela de loading
            SplashScreen()
        }

        is AuthViewModel.AuthState.Authenticated,
        is AuthViewModel.AuthState.Success -> {
            // Usuário logado → Home
            HomeScreen(
                onLogout = { authViewModel.logout() }
            )
        }

        is AuthViewModel.AuthState.Unauthenticated,
        is AuthViewModel.AuthState.Error,
        AuthViewModel.AuthState.Idle -> {
            // Usuário não logado ou erro → Login
            val isLoading = auth is AuthViewModel.AuthState.Loading
            val errorMsg = if (auth is AuthViewModel.AuthState.Error) auth.message else null

            LoginScreen(
                onLogin = { email, password ->
                    authViewModel.login(LoginRequest(email, password))
                },
                onRegisterClick = { /* TODO: Navegar para tela de cadastro */ },
                isLoading = isLoading,
                errorMessage = errorMsg
            )
        }
    }
}
