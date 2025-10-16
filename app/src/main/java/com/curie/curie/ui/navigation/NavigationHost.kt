package com.curie.curie.ui.navigation

import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    val items = getBottomNavItems()

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
            startDestination = items.first().route,
            modifier = Modifier.padding(padding)
        ) {
            composable("tarefas") { Text("Tela Tarefas") }
            composable("carreira") { Text("Tela Carreira") }
            composable("home") { Text("Tela Home") }
            composable("chat") { Text("Tela Chat") }
            composable("perfil") { Text("Tela Perfil") }
        }
    }
}
