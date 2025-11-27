package com.curie.curie

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.start.StartScreen
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Força modo retrato
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Edge-to-edge oficial
        enableEdgeToEdge()

        // Define fundo das system bars
        window.statusBarColor = BlueNavy.toArgb()
        window.navigationBarColor = BlueNavy.toArgb()

        // ⭐ Define ícones brancos
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false      // ícones brancos
            isAppearanceLightNavigationBars = false  // ícones brancos
        }

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
