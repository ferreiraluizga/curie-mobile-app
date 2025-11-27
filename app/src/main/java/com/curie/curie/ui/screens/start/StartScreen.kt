package com.curie.curie.ui.screens.start

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.LoginRequest
import com.curie.curie.ui.navigation.NavigationHost
import com.curie.curie.ui.screens.auth.*
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    tokenStorage: TokenStorage,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(tokenStorage))
) {
    var showSplash by remember { mutableStateOf(true) }
    var showWelcome by remember { mutableStateOf(false) }
    var isRegistering by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false

        // Verifica se já existe usuário logado
        val userId = tokenStorage.getUserId()

        if (userId != null) {
            // Já tem login → ir direto pra Home
            authViewModel.restoreSession(userId)
        } else {
            // Não está logado → mostrar Welcome
            showWelcome = true
        }
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    val state by authViewModel.authState.collectAsState()

    // Se já está autenticado → Home
    if (state is AuthViewModel.AuthState.Success) {
        NavigationHost((state as AuthViewModel.AuthState.Success).userId, authViewModel)
        return
    }

    if (state is AuthViewModel.AuthState.Authenticated) {
        val userId = tokenStorage.getUserId()
        if (userId != null) {
            NavigationHost(userId, authViewModel)
            return
        }
    }

    // Welcome só aparece se não houver login salvo
    if (showWelcome) {
        WelcomeScreen(
            onLoginClick = {
                showWelcome = false
                isRegistering = false
            },
            onRegisterClick = {
                showWelcome = false
                isRegistering = true
            }
        )
        return
    }

    // Fluxo login/cadastro
    when (state) {

        is AuthViewModel.AuthState.Loading -> SplashScreen()

        is AuthViewModel.AuthState.Error,
        is AuthViewModel.AuthState.Unauthenticated,
        AuthViewModel.AuthState.Idle -> {

            if (isRegistering) {
                SignupScreen(
                    onContinueClick = { request ->
                        authViewModel.register(request)
                    },
                    onLoginClick = { isRegistering = false }
                )
            } else {
                LoginScreen(
                    onLogin = { email, password ->
                        authViewModel.login(LoginRequest(email, password))
                    },
                    onRegisterClick = { isRegistering = true },
                    isLoading = state is AuthViewModel.AuthState.Loading,
                    errorMessage = (state as? AuthViewModel.AuthState.Error)?.message
                )
            }
        }

        else -> {}
    }
}
