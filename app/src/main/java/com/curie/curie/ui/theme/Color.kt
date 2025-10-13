package com.curie.curie.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val BlueDark = Color(0xFF033E8C)
val BlueNavy = Color(0xFF000438)
val BluePrimary = Color(0xFF0477BF)
val BlueLight = Color(0xFF049DD9)
val BlueCyan = Color(0xFF04B2D9)

val LightColors: ColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueLight,
    onPrimaryContainer = BlueNavy,

    secondary = BlueDark,
    onSecondary = Color.White,
    secondaryContainer = BlueCyan,
    onSecondaryContainer = BlueNavy,

    background = Color.White,
    onBackground = BlueNavy,

    surface = Color.White,
    onSurface = BlueNavy,

    error = Color(0xFFB00020),
    onError = Color.White
)

val DarkColors: ColorScheme = darkColorScheme(
    primary = BlueLight,
    onPrimary = BlueNavy,
    primaryContainer = BluePrimary,
    onPrimaryContainer = Color.White,

    secondary = BlueCyan,
    onSecondary = BlueNavy,
    secondaryContainer = BlueDark,
    onSecondaryContainer = Color.White,

    background = BlueNavy,
    onBackground = Color.White,

    surface = BlueDark,
    onSurface = Color.White,

    error = Color(0xFFCF6679),
    onError = Color.Black
)
