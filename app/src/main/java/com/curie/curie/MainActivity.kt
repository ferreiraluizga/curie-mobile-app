package com.curie.curie

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.start.StartScreen
import com.curie.curie.ui.theme.CurieTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tokenStorage = TokenStorage(this)
        setContent {
            CurieTheme {
                StartScreen(tokenStorage = tokenStorage)
            }
        }
    }
}