package com.curie.curie.ui.screens.start

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.LoginRequest
import com.curie.curie.ui.navigation.NavigationHost
import com.curie.curie.ui.screens.auth.AuthViewModel
import com.curie.curie.ui.screens.auth.AuthViewModelFactory
import com.curie.curie.ui.screens.auth.LoginScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    tokenStorage: TokenStorage,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(tokenStorage))
) {
    var showSplash by remember { mutableStateOf(true) }

    // Splash por 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    val state by authViewModel.authState.collectAsState()

    when (val auth = state) {
        is AuthViewModel.AuthState.Loading -> SplashScreen()

        is AuthViewModel.AuthState.Success -> {
            // userId garantido aqui
            NavigationHost(auth.userId, authViewModel = authViewModel)
        }

        is AuthViewModel.AuthState.Authenticated -> {
            val userId = tokenStorage.getUserId()
            if (userId != null) {
                NavigationHost(userId, authViewModel = authViewModel)
            } else {
                authViewModel.logout() // sessão inválida, força login
            }
        }

        is AuthViewModel.AuthState.Unauthenticated,
        is AuthViewModel.AuthState.Error,
        AuthViewModel.AuthState.Idle -> {
            LoginScreen(
                onLogin = { email, password -> authViewModel.login(LoginRequest(email, password)) },
                onRegisterClick = { },
                isLoading = false,
                errorMessage = (auth as? AuthViewModel.AuthState.Error)?.message
            )
        }
    }
}

