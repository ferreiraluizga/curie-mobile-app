package com.curie.curie.ui.screens.carreira

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.R
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.components.carreira.ResultCard
import com.curie.curie.ui.components.carreira.TestCard
import com.curie.curie.ui.screens.carreira.comportamento.ComportamentoViewModel
import com.curie.curie.ui.screens.carreira.comportamento.ComportamentoViewModelFactory
import com.curie.curie.ui.screens.carreira.comportamento.TipoComportamentoViewModel
import com.curie.curie.ui.screens.carreira.comportamento.TipoComportamentoViewModelFactory
import com.curie.curie.ui.screens.carreira.temperamento.TemperamentoViewModel
import com.curie.curie.ui.screens.carreira.temperamento.TemperamentoViewModelFactory
import com.curie.curie.ui.screens.carreira.temperamento.TipoTemperamentoViewModel
import com.curie.curie.ui.screens.carreira.temperamento.TipoTemperamentoViewModelFactory
import com.curie.curie.ui.screens.perfil.UserViewModel
import com.curie.curie.ui.screens.perfil.UserViewModelFactory
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.Typography
data class TestStatus(
    val done: Boolean,
    val resultDescription: String? = null
)

@Composable
fun UserDashboardScreen(
    tokenStorage: TokenStorage,
    onVocacionalClick: () -> Unit = {},
    onComportamentalClick: () -> Unit = {},
    onTemperamentoClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    // 🧠 ViewModels principais
    val comportamentoViewModel: ComportamentoViewModel = viewModel(
        factory = ComportamentoViewModelFactory(tokenStorage)
    )
    val temperamentoViewModel: TemperamentoViewModel = viewModel(
        factory = TemperamentoViewModelFactory(tokenStorage)
    )
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(tokenStorage)
    )

    // 🔠 ViewModels auxiliares (tipos)
    val tipoComportamentoViewModel: TipoComportamentoViewModel = viewModel(
        factory = TipoComportamentoViewModelFactory(tokenStorage)
    )
    val tipoTemperamentoViewModel: TipoTemperamentoViewModel = viewModel(
        factory = TipoTemperamentoViewModelFactory(tokenStorage)
    )

    // 🔄 Coleta dos estados dos ViewModels
    val comportamento by comportamentoViewModel.comportamento.collectAsStateWithLifecycle()
    val temperamento by temperamentoViewModel.temperamento.collectAsStateWithLifecycle()
    val tipoComportamento by tipoComportamentoViewModel.tipoComportamento.collectAsStateWithLifecycle()
    val tipoTemperamento by tipoTemperamentoViewModel.tipoTemperamento.collectAsStateWithLifecycle()
    val user by userViewModel.user.collectAsStateWithLifecycle()

    // 🪣 Estados locais
    var vocacionalStatus by remember { mutableStateOf(TestStatus(false)) }
    var comportamentalStatus by remember { mutableStateOf(TestStatus(false)) }
    var temperamentoStatus by remember { mutableStateOf(TestStatus(false)) }

    // 🧍 Buscar usuário logado
    LaunchedEffect(Unit) {
        val userId = tokenStorage.getUserId()
        if (userId != null) {
            userViewModel.getById(userId)
        }
    }

    // 🚀 Buscar resultados se IDs existirem
    LaunchedEffect(Unit) {
        val comportamentoId = tokenStorage.getComportamentoId()
        val temperamentoId = tokenStorage.getTemperamentoId()

        comportamentoId?.let { comportamentoViewModel.getById(it) }
        temperamentoId?.let { temperamentoViewModel.getById(it) }
    }

    // 🔁 Buscar os tipos após carregar comportamento e temperamento
    LaunchedEffect(comportamento) {
        comportamento?.let {
            tipoComportamentoViewModel.getById(it.tipoComportamentoId)
        }
    }

    LaunchedEffect(temperamento) {
        temperamento?.let {
            tipoTemperamentoViewModel.getById(it.tipoTemperamentoId)
        }
    }

    // 🧩 Atualizar status com nomes reais
    LaunchedEffect(tipoComportamento, tipoTemperamento) {
        tipoComportamento?.let {
            comportamentalStatus = TestStatus(true, it.nome)
        }
        tipoTemperamento?.let {
            temperamentoStatus = TestStatus(true, it.nome)
        }
    }

    val allDone = vocacionalStatus.done && comportamentalStatus.done && temperamentoStatus.done

    // 🧱 Layout principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
    ) {
        // 🔹 Cabeçalho
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueNavy)
                .padding(vertical = 32.dp, horizontal = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Olá, ${user?.nome ?: "Usuário"}!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Central de Análises",
                        color = Color(0xFFD9D9D9),
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 🔸 Alerta de progresso
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (allDone) Color(0xFFD9F8E5) else Color(0xFFFFF4CC)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = if (allDone) Icons.Outlined.CheckCircle else Icons.Outlined.WarningAmber,
                    contentDescription = null,
                    tint = if (allDone) Color(0xFF2E7D32) else Color(0xFFB26A00),
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    if (allDone) {
                        Text(
                            text = "Todos os testes foram concluídos!",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Agora você pode gerar sua análise de perfil completa.",
                            color = Color(0xFF2E7D32),
                            fontSize = 13.sp
                        )
                    } else {
                        Text(
                            text = "Atenção!",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB26A00),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Conclua todos os testes para gerar sua análise de perfil.",
                            color = Color(0xFFB26A00),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Se sair antes de finalizar, seus dados serão excluídos.",
                            color = Color(0xFFB26A00),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // 🔹 Conteúdo principal
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Acompanhe seus resultados e explore novos caminhos",
                    color = Color.DarkGray,
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Teste Vocacional
            item {
                if (vocacionalStatus.done) {
                    ResultCard(
                        title = "Teste Vocacional",
                        description = "Você tem afinidade com as seguintes áreas: ${vocacionalStatus.resultDescription}",
                        icon = Icons.Outlined.Book,
                        color = Color(0xFFB39DDB),
                        buttonText = "Ver resultado",
                        onClick = onVocacionalClick
                    )
                } else {
                    TestCard(
                        title = "Teste Vocacional",
                        description = "Descubra suas áreas de afinidade profissional.",
                        questions = "17 perguntas",
                        buttonText = "Explorar carreiras",
                        icon = Icons.Outlined.Book,
                        buttonColor = Color(0xFFB39DDB),
                        onClick = onVocacionalClick
                    )
                }
            }

            // Teste Comportamental
            item {
                if (comportamentalStatus.done) {
                    ResultCard(
                        title = "Teste Comportamental",
                        description = "Seu comportamento predominante é ${comportamentalStatus.resultDescription}",
                        icon = Icons.Outlined.BarChart,
                        color = BlueNavy,
                        buttonText = "Ver resultado",
                        onClick = onComportamentalClick
                    )
                } else {
                    TestCard(
                        title = "Teste Comportamental",
                        description = "Entenda seu estilo de estudo e organização.",
                        questions = "15 perguntas",
                        buttonText = "Fazer teste",
                        icon = Icons.Outlined.BarChart,
                        buttonColor = BlueNavy,
                        onClick = onComportamentalClick
                    )
                }
            }

            // Teste de Temperamento
            item {
                if (temperamentoStatus.done) {
                    ResultCard(
                        title = "Teste de Temperamento",
                        description = "Seu temperamento predominante é ${temperamentoStatus.resultDescription}",
                        icon = Icons.Outlined.Psychology,
                        color = BlueNavy,
                        buttonText = "Ver resultado",
                        onClick = onTemperamentoClick
                    )
                } else {
                    TestCard(
                        title = "Teste de Temperamento",
                        description = "Descubra como reage em diferentes situações.",
                        questions = "17 perguntas",
                        buttonText = "Iniciar teste",
                        icon = Icons.Outlined.Psychology,
                        buttonColor = BlueNavy,
                        onClick = onTemperamentoClick
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}