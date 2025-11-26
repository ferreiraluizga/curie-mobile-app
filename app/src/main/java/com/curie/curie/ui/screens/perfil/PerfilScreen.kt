import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.components.perfil.PerfilImage
import com.curie.curie.ui.components.perfil.ProfileOption
import com.curie.curie.ui.screens.perfil.UserViewModel
import com.curie.curie.ui.screens.perfil.UserViewModelFactory

@Composable
fun PerfilScreen(
    userId: Long,
    tokenStorage: TokenStorage,
    onEditClick: (Long) -> Unit = {},
    onOptionClick: (String) -> Unit = {},
    onLogoutClick: () -> Unit = {} // 👈 NOVO
) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(tokenStorage))
    val userState by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()

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
                userUsername = userState!!.email,
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

            Spacer(modifier = Modifier.height(32.dp))

            // 🔥 BOTÃO DE SAIR
            ProfileOption(Icons.Default.ExitToApp,"Sair") { onLogoutClick() }

        }
    }
}
