package com.curie.curie.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.components.perfil.PerfilImage
import com.curie.curie.ui.components.perfil.ProfileOption

@Composable
fun PerfilScreen(
    userId: Long,
    tokenStorage: TokenStorage,
    onEditClick: (Long) -> Unit = {},
    onOptionClick: (String) -> Unit = {}
) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(tokenStorage))
    val userState by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()

    // 🔹 Buscar dados do usuário ao abrir
    LaunchedEffect(userId) {
        viewModel.getById(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        if (userState != null) {
            PerfilImage(
                userId = userState!!.id.toInt(),
                userName = userState!!.nome,
                userUsername = userState!!.email, // pode mudar para username se tiver
                onEditClick = { onEditClick(userState!!.id) }
            )
        } else if (loading) {
            Text("Carregando perfil...", color = Color.Gray, modifier = Modifier.padding(24.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            ProfileOption(Icons.Default.Settings, "Configurações") { onOptionClick("config") }
            ProfileOption(Icons.Default.CreditCard, "Assinatura") { onOptionClick("assinatura") }
            ProfileOption(Icons.Default.Group, "Equipe") { onOptionClick("equipe") }
            ProfileOption(Icons.Default.Help, "Suporte") { onOptionClick("suporte") }
            ProfileOption(Icons.Default.Info, "Sobre nós") { onOptionClick("sobre") }
        }
    }
}
