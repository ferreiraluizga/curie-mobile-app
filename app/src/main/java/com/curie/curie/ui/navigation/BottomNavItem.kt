package com.curie.curie.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.curie.curie.R

sealed class NavIcon {
    data class VectorIcon(val icon: ImageVector) : NavIcon()
    data class PainterIcon(val painter: Painter) : NavIcon()
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: NavIcon
)

@Composable
fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem("Tarefas", "tarefas", NavIcon.VectorIcon(Icons.Filled.Checklist)),
        BottomNavItem("Carreira", "carreira", NavIcon.VectorIcon(Icons.Filled.School)),
        BottomNavItem("Home", "home", NavIcon.VectorIcon(Icons.Filled.Home)),
        BottomNavItem("Chat", "chat", NavIcon.PainterIcon(painterResource(id = R.drawable.c_logo_nobackground))),
        BottomNavItem("Perfil", "perfil", NavIcon.VectorIcon(Icons.Filled.Person))
    )
}

