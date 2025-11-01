package com.curie.curie.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curie.curie.R
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.auth.AuthViewModel
import com.curie.curie.ui.screens.chat.ChatScreen
import com.curie.curie.ui.screens.chat.ChatViewModel
import com.curie.curie.ui.screens.start.HomeScreen
import com.curie.curie.ui.screens.tarefa.TarefaScreen

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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    userId: Long,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = getBottomNavItems()

    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            when (val icon = item.icon) {
                                is NavIcon.VectorIcon -> Icon(icon.icon, contentDescription = item.name)
                                is NavIcon.PainterIcon -> Icon(painter = icon.painter, contentDescription = item.name)
                            }
                        },
                        label = { Text(item.name) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("tarefas") {
                TarefaScreen(userId = userId, tokenStorage = tokenStorage)
            }
            composable("carreira") { Text("Tela Carreira") }
            composable("home") {
                HomeScreen(
                    userId = userId,
                    onLogout = {
                        authViewModel.logout()
                    }
                )
            }
            composable("chat") {
                val chatViewModel: ChatViewModel = viewModel()
                ChatScreen(viewModel = chatViewModel)
            }
            composable("perfil") { Text("Tela Perfil") }
        }
    }
}