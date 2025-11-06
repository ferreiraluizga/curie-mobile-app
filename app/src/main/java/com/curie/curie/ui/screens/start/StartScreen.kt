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
import com.curie.curie.ui.screens.auth.SignupScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    tokenStorage: TokenStorage,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(tokenStorage))
) {
    var showSplash by remember { mutableStateOf(true) }
    var isRegistering by remember { mutableStateOf(false) }

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
            NavigationHost(auth.userId, authViewModel)
        }

        is AuthViewModel.AuthState.Authenticated -> {
            val userId = tokenStorage.getUserId()
            if (userId != null) {
                NavigationHost(userId, authViewModel)
            } else {
                authViewModel.logout()
            }
        }

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
                    errorMessage = (auth as? AuthViewModel.AuthState.Error)?.message
                )
            }
        }
    }
}
