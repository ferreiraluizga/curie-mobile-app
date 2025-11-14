package com.curie.curie.ui.screens.carreira.vocacional

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.curie.curie.data.model.Comportamento
import com.curie.curie.ui.screens.carreira.PerfilViewModel
import com.curie.curie.ui.screens.carreira.PerfilViewModelFactory
import com.curie.curie.ui.screens.carreira.comportamento.ComportamentoViewModel
import com.curie.curie.ui.screens.carreira.comportamento.ComportamentoViewModelFactory
import com.curie.curie.ui.theme.BlueNavy
import kotlinx.coroutines.launch

// -----------------------------
// DATA CLASSES
// -----------------------------
data class Alternativa(val texto: String, val tipo: String)
data class Pergunta(val texto: String, val alternativas: List<Alternativa>)

// -----------------------------
// PRINCIPAL
// -----------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TesteVocacionalScreen(
    userId: Long,
    onBack: () -> Unit,
    tokenStorage: TokenStorage
) {
    // --- FACTORIES ---
    val areaCarreiraFactory = remember { AreaCarreiraViewModelFactory(tokenStorage) }

    // --- VIEWMODELS ---
    val areaCarreiraViewModel: AreaCarreiraViewModel = viewModel(factory = areaCarreiraFactory)

    // --- ESTADOS ---
    val loading by areaCarreiraViewModel.loading.collectAsStateWithLifecycle()
    val error by areaCarreiraViewModel.error.collectAsStateWithLifecycle()
    val contexto = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val areasMapeadas = mapOf(
        "A" to listOf(
            "Exatas", "Tecnologia", "Engenharia", "Gestão",
            "Administração", "Contabilidade"
        ),
        "B" to listOf(
            "Artes", "Design", "Comunicação", "Criativas",
            "Publicidade", "Moda", "Arquitetura", "Cinema", "Design"
        ),
        "C" to listOf(
            "Humanas", "Educação", "Psicologia", "Social",
            "Serviço Social", "RH"
        ),
        "D" to listOf(
            "Ciências", "Pesquisa", "Filosofia", "Acadêmica",
            "Biologia", "Física", "História", "Filosofia", "Pesquisa"
        )
    )

    val perguntas = listOf(
        Pergunta(
            "Quando surge um problema em grupo, você…",
            listOf(
                Alternativa("Analisa o problema antes de tomar qualquer atitude.", "Analítico"),
                Alternativa("Conversa com todos até chegar a um acordo.", "Comunicativo"),
                Alternativa("Fica tranquilo e espera o momento certo para agir.", "Estável"),
                Alternativa("Assume a liderança e decide o que fazer.", "Proativo")
            )
        ),
        Pergunta(
            "Ao receber uma tarefa nova, você…",
            listOf(
                Alternativa("Faz em um tempo desacelerado e com paciência.", "Estável"),
                Alternativa("Faz uma lista de tarefas antes de começar.", "Analítico"),
                Alternativa("Pede ajuda ou compartilha com alguém.", "Comunicativo"),
                Alternativa("Quer começar logo e resolver rápido.", "Proativo")
            )
        ),
        Pergunta(
            "Quando está estudando, você prefere…",
            listOf(
                Alternativa("Fazer resumos e anotações detalhadas.", "Analítico"),
                Alternativa("Resolver exercícios e desafios práticos.", "Proativo"),
                Alternativa("Discutir o tema com colegas.", "Comunicativo"),
                Alternativa("Seguir uma rotina fixa de estudo.", "Estável")
            )
        ),
        Pergunta(
            "Quando algo dá errado em um trabalho em grupo, você…",
            listOf(
                Alternativa("Revê o que fez e procura o erro.", "Analítico"),
                Alternativa("Fica frustrado, mas tenta resolver sozinho.", "Proativo"),
                Alternativa("Tenta animar os outros e seguir em frente.", "Comunicativo"),
                Alternativa("Aceita o erro e segue o ritmo.", "Estável")
            )
        ),
        Pergunta(
            "Quando alguém te critica…",
            listOf(
                Alternativa("Ri e leva numa boa.", "Comunicativo"),
                Alternativa("Defende seu ponto de vista.", "Proativo"),
                Alternativa("Reavalia o que fez com cuidado.", "Analítico"),
                Alternativa("Fica em silêncio, mas pensa no que ouviu.", "Estável")
            )
        ),
        Pergunta(
            "Sua mesa de estudos geralmente está…",
            listOf(
                Alternativa("Um pouco bagunçada, mas funcional.", "Comunicativo"),
                Alternativa("Cheia de papéis e anotações de ideias.", "Proativo"),
                Alternativa("Extremamente organizada, caso contrário não é possível a concentração.", "Analítico"),
                Alternativa("Organizada e limpa.", "Estável")
            )
        ),
        Pergunta(
            "Em um dia de prova, você...",
            listOf(
                Alternativa("Revisa o material.", "Analítico"),
                Alternativa("Mantém a calma e o foco.", "Estável"),
                Alternativa("Fica nervoso, mas tenta descontrair.", "Comunicativo"),
                Alternativa("Gosta de desafio e sente adrenalina.", "Proativo")
            )
        ),
        Pergunta(
            "Seu maior ponto fraco é…",
            listOf(
                Alternativa("Falta de foco.", "Comunicativo"),
                Alternativa("Procrastinação.", "Estável"),
                Alternativa("Perfeccionismo.", "Analítico"),
                Alternativa("Impulsividade.", "Proativo")
            )
        ),
        Pergunta(
            "Ao lidar com mudanças…",
            listOf(
                Alternativa("Adapta-se rápido.", "Proativo"),
                Alternativa("Precisa planejar tudo antes.", "Analítico"),
                Alternativa("Se empolga, mas pode perder o ritmo.", "Comunicativo"),
                Alternativa("Resiste um pouco, mas aceita.", "Estável")
            )
        ),
        Pergunta(
            "Quando precisa estudar algo difícil, você majoritariamente…",
            listOf(
                Alternativa("Pesquisa várias fontes antes.", "Analítico"),
                Alternativa("Estuda aos poucos, sem pressão.", "Estável"),
                Alternativa("Estuda até dominar o assunto.", "Proativo"),
                Alternativa("Procura ajuda.", "Comunicativo")
            )
        ),
        Pergunta(
            "Quando há uma divergência em grupo, você tende a…",
            listOf(
                Alternativa("Mediar e buscar consenso.", "Comunicativo"),
                Alternativa("Assumir o controle da situação.", "Proativo"),
                Alternativa("Esperar as emoções se acalmarem.", "Estável"),
                Alternativa("Ouvir todos antes de sugerir algo racional ou se abster.", "Analítico")
            )
        ),
        Pergunta(
            "Em dias de muito estresse, você…",
            listOf(
                Alternativa("Se fecha e organiza a mente antes de agir.", "Analítico"),
                Alternativa("Busca companhia para aliviar a tensão.", "Comunicativo"),
                Alternativa("Tenta manter o ritmo, mesmo devagar.", "Estável"),
                Alternativa("Age rapidamente para resolver e sair do problema.", "Proativo")
            )
        ),
        Pergunta(
            "Quando recebe um elogio por algo que fez, você…",
            listOf(
                Alternativa("Fica animado e compartilha a conquista.", "Comunicativo"),
                Alternativa("Analisa o que deu certo para repetir depois.", "Analítico"),
                Alternativa("Agradece e mantém a mesma dedicação de sempre.", "Estável"),
                Alternativa("Se sente motivado a buscar novos desafios.", "Proativo")
            )
        )
    )

    // --- ESTADOS DO TESTE ---
    var perguntaAtual by remember { mutableStateOf(0) }
    val respostas = remember { mutableStateListOf<String?>(*Array(perguntas.size) { null }) }
    var resultado by remember { mutableStateOf<String?>(null) }

    // --- TELA PRINCIPAL ---
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Teste Vocacional", color = Color.White, fontWeight = FontWeight.SemiBold)
                },
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
            // --------------- ETAPA: TESTE ----------------
            if (resultado == null) {
                val scrollState = rememberScrollState()
                val density = LocalDensity.current

                // Bolinhas de progresso
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

                // Auto-scroll das bolinhas
                LaunchedEffect(perguntaAtual) {
                    val offsetDp = perguntaAtual * (42.dp + 8.dp)
                    val offsetPx = with(density) { offsetDp.toPx().toInt() }
                    scrollState.animateScrollTo(offsetPx)
                }

                Spacer(Modifier.height(24.dp))

                // Pergunta atual
                Text(
                    text = perguntas[perguntaAtual].texto,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // Alternativas
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

                // Botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (perguntaAtual > 0) {
                        OutlinedButton(
                            onClick = { perguntaAtual-- },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueNavy)
                        ) {
                            Text("Voltar")
                        }
                    }

                    Button(
                        onClick = {
                            if (perguntaAtual < perguntas.size - 1) {
                                perguntaAtual++
                            } else {
                                val contagem = respostas
                                    .filterNotNull()
                                    .map { it.trim() }
                                    .groupingBy { it }
                                    .eachCount()

                                Log.d("TesteComportamento", "Resumo das respostas: $contagem")

                                val max = contagem.values.maxOrNull()
                                val empatados = contagem.filter { it.value == max }.keys
                                val resultadoCalculado = when {
                                    empatados.size == 1 -> empatados.first()
                                    empatados.size > 1 -> empatados.sorted().joinToString("–")
                                    else -> "Indefinido"
                                }

                                resultado = resultadoCalculado

                                /*
                                comportamentoViewModel.finalizarTeste(resultadoCalculado) { comportamentoId ->
                                    if (comportamentoId != null) {
                                        tokenStorage.saveComportamentoId(comportamentoId)
                                        Log.d("TesteComportamento", "Comportamento salvo com ID: $comportamentoId")
                                    } else {
                                        Log.e("TesteComportamento", "Falha ao salvar comportamento.")
                                    }
                                }
                                 */
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueNavy)
                    ) {
                        Text(if (perguntaAtual == perguntas.size - 1) "Enviar respostas" else "Próxima")
                    }
                }
            }

            // --------------- ETAPA: RESULTADO ----------------
            else {
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
                    /*
                    comportamentoSalvo != null -> {
                        ResultadoComportamentoCompleto(
                            tipo = resultado ?: "Indefinido",
                            comportamento = comportamentoSalvo!!,
                            onConcluir = onBack
                        )
                    }
                     */
                }
            }
        }
    }
}

@Composable
fun ResultadoComportamentoCompleto(
    tipo: String,
    comportamento: Comportamento,
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

        Text("Características:", fontWeight = FontWeight.Bold)
        Text(comportamento.caracteristicas)
        Spacer(Modifier.height(12.dp))

        Text("Aprendizagem:", fontWeight = FontWeight.Bold)
        Text(comportamento.aprendizagem)
        Spacer(Modifier.height(12.dp))

        Text("Dicas de Estudo:", fontWeight = FontWeight.Bold)
        Text(comportamento.descricaoEstudo)
        Spacer(Modifier.height(24.dp))

        Button(onClick = onConcluir) {
            Text("Concluir")
        }
    }
}