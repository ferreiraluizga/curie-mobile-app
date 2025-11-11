package com.curie.curie.ui.screens.carreira.temperamento

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Temperamento
import com.curie.curie.ui.theme.BlueNavy
import kotlinx.coroutines.launch

data class Alternativa(val texto: String, val tipo: String)
data class Pergunta(val texto: String, val alternativas: List<Alternativa>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TesteTemperamentoScreen(
    userId: Long,
    onBack: () -> Unit,
    tokenStorage: TokenStorage
) {
    val temperamentoFactory = remember { TemperamentoViewModelFactory(tokenStorage) }
    val temperamentoViewModel: TemperamentoViewModel = viewModel(factory = temperamentoFactory)

    val loading by temperamentoViewModel.loading.collectAsStateWithLifecycle()
    val error by temperamentoViewModel.error.collectAsStateWithLifecycle()
    val temperamentoSalvo by temperamentoViewModel.temperamento.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 🔹 Perguntas sem as áreas de desempenho
    val perguntas = listOf(
        Pergunta(
            "Quando algo dá errado no que eu planejei…",
            listOf(
                Alternativa("Tento entender o que falhou e reorganizo tudo com calma.", "Melancólico"),
                Alternativa("Fico frustrado, mas logo busco uma solução prática.", "Colérico"),
                Alternativa("Converso com alguém para desabafar e clarear as ideias.", "Sanguíneo"),
                Alternativa("Aceito que imprevistos acontecem e continuo no meu ritmo.", "Fleumático")
            )
        ),
        Pergunta(
            "Quando estou em grupo…",
            listOf(
                Alternativa("Gosto de manter o ambiente leve e animado.", "Sanguíneo"),
                Alternativa("Gosto de ficar no meu espaço e evitar tensão.", "Fleumático"),
                Alternativa("Assumo a organização ou direção naturalmente.", "Colérico"),
                Alternativa("Faço críticas (positivas ou não) e ofereço soluções.", "Melancólico")
            )
        ),
        Pergunta(
            "Diante de uma decisão difícil…",
            listOf(
                Alternativa("Analiso todos os prós e contras antes de agir.", "Melancólico"),
                Alternativa("Escolho rápido, confiando no que parece mais lógico.", "Colérico"),
                Alternativa("Peço opiniões de pessoas próximas antes de decidir.", "Sanguíneo"),
                Alternativa("Penso com calma e só ajo quando estiver tranquilo.", "Fleumático")
            )
        ),
        Pergunta(
            "Em momentos de pressão…",
            listOf(
                Alternativa("Mantenho o foco e ajo imediatamente.", "Colérico"),
                Alternativa("Tento aliviar o clima e me adapto.", "Sanguíneo"),
                Alternativa("Fico em silêncio e me adapto.", "Fleumático"),
                Alternativa("Reavalio os detalhes para evitar erros e demoro um pouco para agir.", "Melancólico")
            )
        ),
        Pergunta(
            "Sobre rotina…",
            listOf(
                Alternativa("Gosto de rotina bem estruturada.", "Melancólico"),
                Alternativa("Preciso de variedade para não perder o interesse.", "Sanguíneo"),
                Alternativa("Sigo o que funciona e evito mudanças bruscas.", "Fleumático"),
                Alternativa("Ajusto a rotina sempre que surge uma oportunidade melhor.", "Colérico")
            )
        ),
        Pergunta(
            "Quando alguém me critica...",
            listOf(
                Alternativa("Reflito profundamente e levo isso para melhorar.", "Melancólico"),
                Alternativa("Tento compreender sem me abalar.", "Fleumático"),
                Alternativa("Fico incomodado, mas respondo com firmeza.", "Colérico"),
                Alternativa("Converso para entender o motivo e esclarecer.", "Sanguíneo")
            )
        ),
        Pergunta(
            "Quando estou com várias tarefas...",
            listOf(
                Alternativa("Faço um plano e priorizo a qualidade.", "Melancólico"),
                Alternativa("Escolho o mais urgente e vou direto.", "Colérico"),
                Alternativa("Faço aos poucos, sem me sobrecarregar.", "Fleumático"),
                Alternativa("Faço mais de uma tarefa ao mesmo tempo.", "Sanguíneo")
            )
        ),
        Pergunta(
            "Quando alguém está triste…",
            listOf(
                Alternativa("Tento animar a pessoa com algo leve, tentando distrair do problema.", "Sanguíneo"),
                Alternativa("Ofereço presença e evito invadir o espaço pessoal.", "Fleumático"),
                Alternativa("Sugiro soluções práticas, mesmo que pareça direto demais.", "Colérico"),
                Alternativa("Procuro entender profundamente o que ela sente, ouvindo com atenção.", "Melancólico")
            )
        ),
        Pergunta(
            "Ao lidar com mudanças…",
            listOf(
                Alternativa("Encaro como oportunidade de crescer.", "Colérico"),
                Alternativa("Me adapto devagar, mas sem reclamar.", "Fleumático"),
                Alternativa("Fico animado e curioso com o novo.", "Sanguíneo"),
                Alternativa("Sinto insegurança e tento planejar cada detalhe.", "Melancólico")
            )
        ),
        Pergunta(
            "Quando tenho uma ideia…",
            listOf(
                Alternativa("Coloco em prática o quanto antes.", "Colérico"),
                Alternativa("Penso se ela é realmente viável e bem estruturada.", "Melancólico"),
                Alternativa("Gosto de compartilhar com os outros e ouvir opiniões.", "Sanguíneo"),
                Alternativa("Guardo para analisar com calma depois.", "Fleumático")
            )
        ),
        Pergunta(
            "Quando tenho tempo livre…",
            listOf(
                Alternativa("Gosto de me desafiar com algo novo.", "Colérico"),
                Alternativa("Gosto de conversar, sair ou conhecer gente.", "Sanguíneo"),
                Alternativa("Prefiro descanso e momentos tranquilos.", "Fleumático"),
                Alternativa("Prefiro atividades calmas e introspectivas.", "Melancólico")
            )
        ),
        Pergunta(
            "Quando me sinto sobrecarregado…",
            listOf(
                Alternativa("Me esforço até resolver tudo.", "Colérico"),
                Alternativa("Busco distrações leves para aliviar.", "Sanguíneo"),
                Alternativa("Faço pausas e continuo no meu ritmo.", "Fleumático"),
                Alternativa("Tento entender o motivo da exaustão e ajustar.", "Melancólico")
            )
        )
    )

    var perguntaAtual by remember { mutableStateOf(0) }
    var forcaDescricao by remember { mutableStateOf("") }
    var fraquezaDescricao by remember { mutableStateOf("") }
    val respostas = remember { mutableStateListOf<String?>(*Array(perguntas.size) { null }) }
    var resultado by remember { mutableStateOf<String?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Teste Temperamental", color = Color.White, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BlueNavy)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (resultado == null) {
                val scrollState = rememberScrollState()
                val density = LocalDensity.current

                // 🔹 Bolinhas de progresso
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.Center
                ) {
                    perguntas.forEachIndexed { index, _ ->
                        val isAtual = index == perguntaAtual
                        val isRespondida = respostas[index] != null

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .then(
                                    if (isAtual)
                                        Modifier.border(2.dp, BlueNavy, CircleShape)
                                    else Modifier.background(
                                        if (isRespondida) BlueNavy else Color(0xFFE8EAF6),
                                        CircleShape
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = when {
                                    isAtual -> BlueNavy
                                    isRespondida -> Color.White
                                    else -> Color.Black
                                },
                                fontSize = 14.sp
                            )
                        }
                        if (index < perguntas.lastIndex) Spacer(Modifier.width(8.dp))
                    }
                }

                LaunchedEffect(perguntaAtual) {
                    val offsetDp = perguntaAtual * (42.dp + 8.dp)
                    val offsetPx = with(density) { offsetDp.toPx().toInt() }
                    scrollState.animateScrollTo(offsetPx)
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = perguntas[perguntaAtual].texto,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                perguntas[perguntaAtual].alternativas.forEach { alt ->
                    val selecionada = respostas[perguntaAtual] == alt.tipo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(if (selecionada) BlueNavy else Color(0xFFF2F2F2))
                            .clickable { respostas[perguntaAtual] = alt.tipo }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = alt.texto,
                            color = if (selecionada) Color.White else Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 🔹 Campos de escrita aparecem somente após responder todas
                if (perguntaAtual == perguntas.lastIndex) {
                    OutlinedTextField(
                        value = forcaDescricao,
                        onValueChange = { forcaDescricao = it },
                        label = { Text("Descreva sua principal força de aprendizado") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = fraquezaDescricao,
                        onValueChange = { fraquezaDescricao = it },
                        label = { Text("Descreva sua principal dificuldade de aprendizado") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (perguntaAtual > 0) {
                        OutlinedButton(
                            onClick = { perguntaAtual-- },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueNavy)
                        ) { Text("Voltar") }
                    }

                    Button(
                        onClick = {
                            if (perguntaAtual < perguntas.size - 1) {
                                perguntaAtual++
                            } else {
                                val todasRespondidas = respostas.all { it != null }
                                val textosPreenchidos =
                                    forcaDescricao.isNotBlank() && fraquezaDescricao.isNotBlank()

                                if (!todasRespondidas) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Responda todas as perguntas antes de enviar.")
                                    }
                                    return@Button
                                }

                                if (!textosPreenchidos) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Preencha os campos de texto antes de enviar.")
                                    }
                                    return@Button
                                }

                                val contagem = respostas.filterNotNull().groupingBy { it }.eachCount()
                                val max = contagem.values.maxOrNull()
                                val empatados = contagem.filter { it.value == max }.keys
                                val resultadoCalculado = when {
                                    empatados.size == 1 -> empatados.first()
                                    empatados.size == 2 -> "${empatados.elementAt(0)}–${empatados.elementAt(1)}"
                                    else -> "Indefinido"
                                }

                                resultado = resultadoCalculado

                                temperamentoViewModel.finalizarTeste(
                                    resultadoNome = resultadoCalculado,
                                    maiorDesempenho = forcaDescricao,
                                    menorDesempenho = fraquezaDescricao
                                ) { temperamentoId ->
                                    if (temperamentoId != null) {
                                        tokenStorage.saveTemperamentoId(temperamentoId)
                                        Log.d("TesteTemperamento", "Temperamento salvo com ID: $temperamentoId")
                                    } else {
                                        Log.e("TesteTemperamento", "Falha ao salvar temperamento.")
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueNavy)
                    ) {
                        Text(if (perguntaAtual == perguntas.size - 1) "Enviar respostas" else "Próxima")
                    }
                }
            } else {
                when {
                    loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BlueNavy)
                        }
                    }

                    error != null -> {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Erro: $error", color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = onBack) { Text("Tentar novamente") }
                        }
                    }

                    temperamentoSalvo != null -> {
                        ResultadoTemperamentoCompleto(
                            tipo = resultado ?: "Indefinido",
                            temperamento = temperamentoSalvo!!,
                            onConcluir = onBack
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoTemperamentoCompleto(
    tipo: String,
    temperamento: Temperamento,
    onConcluir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Resultado:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text(text = tipo, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = BlueNavy)
        Spacer(Modifier.height(16.dp))
        Column {
            Text("Sua Força de Aprendizado:", fontWeight = FontWeight.Bold)
            Text(temperamento.forcaAprendizado)
            Spacer(Modifier.height(12.dp))

            Text("Sua Dificuldade de Aprendizado:", fontWeight = FontWeight.Bold)
            Text(temperamento.fraquezaAprendizado)
            Spacer(Modifier.height(12.dp))
        }
        Button(onClick = onConcluir) {
            Text("Concluir")
        }
    }
}