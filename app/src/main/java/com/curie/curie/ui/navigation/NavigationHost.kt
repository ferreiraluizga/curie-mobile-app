package com.curie.curie.ui.navigation

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curie.curie.R
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.auth.AuthViewModel
import com.curie.curie.ui.screens.carreira.UserDashboardScreen
import com.curie.curie.ui.screens.carreira.comportamento.TesteComportamentoScreen
import com.curie.curie.ui.screens.carreira.temperamento.TesteTemperamentoScreen
import com.curie.curie.ui.screens.chat.ChatScreen
import com.curie.curie.ui.screens.chat.ChatViewModel
import com.curie.curie.ui.screens.perfil.EditarPerfilScreen
import com.curie.curie.ui.screens.perfil.PerfilScreen
import com.curie.curie.ui.screens.start.HomeScreen
import com.curie.curie.ui.screens.tarefa.TarefaScreen
import com.curie.curie.ui.theme.BlueNavy

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
    var selectedItem by remember { mutableStateOf(2) }
    val items = getBottomNavItems()

    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(), // cantos arredondados
                tonalElevation = 8.dp,
                containerColor = BlueNavy, // azul escuro do print
                contentColor = Color.White
            ) {
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
                                is NavIcon.VectorIcon -> Icon(
                                    imageVector = icon.icon,
                                    contentDescription = item.name,
                                    tint = if (selectedItem == index) Color.White else Color(0x80FFFFFF),
                                    modifier = Modifier.size(24.dp) // tamanho padrão Material
                                )
                                is NavIcon.PainterIcon -> Icon(
                                    painter = icon.painter,
                                    contentDescription = item.name,
                                    tint = if (selectedItem == index) Color.White else Color(0x80FFFFFF),
                                    modifier = Modifier.size(22.dp) // levemente menor que os vetoriais
                                )
                            }
                        },
                        label = null, // remove o texto
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
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
            composable("carreira") {
                UserDashboardScreen(
                    tokenStorage = tokenStorage,
                    onVocacionalClick = {
                        navController.navigate("testeVocacional")
                    },
                    onComportamentalClick = {
                        navController.navigate("testeComportamento")
                    },
                    onTemperamentoClick = {
                        navController.navigate("testeTemperamento")
                    }
                )
            }
            composable("testeComportamento") {
                TesteComportamentoScreen(
                    userId = userId,
                    tokenStorage = tokenStorage,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("testeTemperamento") {
                TesteTemperamentoScreen(
                    userId = userId,
                    tokenStorage = tokenStorage,
                    onBack = { navController.popBackStack() }
                )
            }

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

            composable("perfil") {
                PerfilScreen(
                    userId = userId,
                    tokenStorage = tokenStorage,
                    onEditClick = { id ->
                        navController.navigate("editarPerfil/$id")
                    }
                )
            }

            composable("editarPerfil/{userId}") { backStackEntry ->
                val userIdArg = backStackEntry.arguments?.getString("userId")?.toLong() ?: 0L

                EditarPerfilScreen(
                    userId = userIdArg,
                    tokenStorage = tokenStorage,
                    onBackClick = { navController.popBackStack() },
                    onSaveSuccess = {
                        navController.popBackStack() // volta pro perfil ao salvar
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomNavigationPreview() {
    val items = listOf(
        BottomNavItem("Tarefas", "tarefas", NavIcon.VectorIcon(Icons.Filled.Checklist)),
        BottomNavItem("Carreira", "carreira", NavIcon.VectorIcon(Icons.Filled.School)),
        BottomNavItem("Home", "home", NavIcon.VectorIcon(Icons.Filled.Home)),
        BottomNavItem("Chat", "chat", NavIcon.PainterIcon(painterResource(id = R.drawable.c_logo_nobackground))),
        BottomNavItem("Perfil", "perfil", NavIcon.VectorIcon(Icons.Filled.Person))
    )

    var selectedItem by remember { mutableStateOf(2) } // começa na Home

    // Apenas exibe a barra visualmente
    NavigationBar(
        modifier = Modifier.fillMaxWidth(), // cantos arredondados
        tonalElevation = 8.dp,
        containerColor = Color(0xFF002D5B), // azul escuro do print
        contentColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index }, // só muda o selecionado
                icon = {
                    when (val icon = item.icon) {
                        is NavIcon.VectorIcon -> Icon(
                            imageVector = icon.icon,
                            contentDescription = item.name,
                            tint = if (selectedItem == index) Color.White else Color(0x80FFFFFF),
                            modifier = Modifier.size(24.dp) // tamanho padrão Material
                        )
                        is NavIcon.PainterIcon -> Icon(
                            painter = icon.painter,
                            contentDescription = item.name,
                            tint = if (selectedItem == index) Color.White else Color(0x80FFFFFF),
                            modifier = Modifier.size(22.dp) // levemente menor que os vetoriais
                        )
                    }

                },
                label = null, // sem texto
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
