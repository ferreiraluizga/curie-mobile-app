package com.curie.curie

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge // Já está OK
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface // Adicionado para demonstração
import androidx.compose.foundation.layout.fillMaxSize // Adicionado para demonstração
import androidx.compose.foundation.layout.WindowInsets // Necessário para insets
import androidx.compose.foundation.layout.safeDrawing // Necessário para insets
import androidx.compose.foundation.layout.windowInsetsPadding // Necessário para insets
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.start.StartScreen
import com.curie.curie.ui.theme.CurieTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. CHAME A FUNÇÃO enableEdgeToEdge()
        enableEdgeToEdge()

        val tokenStorage = TokenStorage(this)

        setContent {
            CurieTheme {

                // 2. REMOVA O CÓDIGO OBOSLETO
                // O código abaixo foi removido pois causa o warning e é substituído por enableEdgeToEdge:
                /*
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = BlueNavy,
                    darkIcons = false
                )
                */

                // 3. ENVOLVA SEU CONTEÚDO PARA RESPEITAR AS BARRAS DO SISTEMA
                // Isso garante que o conteúdo interativo (como botões e campos) não fique escondido sob as barras de navegação ou status.
                Surface(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) {
                    StartScreen(tokenStorage = tokenStorage)
                }
            }
        }
    }
}