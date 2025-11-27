package com.curie.curie

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.start.StartScreen
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita edge-to-edge moderno
        enableEdgeToEdge()

        // ⭐ CONFIGURA A STATUS BAR SEM ACCOMPANIST
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Cor do fundo da status bar
        window.statusBarColor = BlueNavy.toArgb()

        // Cor dos ícones da status bar
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false // ícones brancos

        val tokenStorage = TokenStorage(this)

        setContent {
            CurieTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) {
                    StartScreen(tokenStorage = tokenStorage)
                }
            }
        }
    }
}