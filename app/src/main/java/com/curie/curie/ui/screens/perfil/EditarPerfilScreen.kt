package com.curie.curie.ui.screens.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.curie.curie.R
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.User
import com.curie.curie.ui.theme.BlueNavy

@Composable
fun EditarPerfilScreen(
    userId: Long,
    tokenStorage: TokenStorage,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(tokenStorage))

    val userState by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Busca os dados do usuário assim que a tela é aberta
    LaunchedEffect(userId) {
        viewModel.getById(userId)
    }

    // Estado local para edição do objeto User
    var editableUser by remember { mutableStateOf<User?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Quando o ViewModel carregar o usuário, cria a cópia editável
    LaunchedEffect(userState) {
        if (userState != null) {
            editableUser = userState!!.copy()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 🔙 Botão voltar
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Editar perfil",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🖼️ Foto do usuário
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (editableUser?.imagemUrl != null) {
                AsyncImage(
                    model = editableUser!!.imagemUrl,
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "Foto padrão",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 🔹 Campos (só aparecem quando há dados carregados)
        if (editableUser != null) {
            OutlinedTextField(
                value = editableUser!!.nome,
                onValueChange = { editableUser = editableUser!!.copy(nome = it) },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = editableUser!!.email,
                onValueChange = { editableUser = editableUser!!.copy(email = it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = editableUser!!.telefone ?: "",
                onValueChange = { editableUser = editableUser!!.copy(telefone = it) },
                label = { Text("Telefone") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    editableUser?.let {
                        viewModel.update(it.id, it)
                    }
                    onSaveSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueNavy)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Salvar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 🔻 Erro
        if (error != null) {
            Text(
                text = error ?: "",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}