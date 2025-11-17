package com.curie.curie.ui.screens.carreira.vocacional

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
import com.curie.curie.ui.theme.BlueNavy

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
            "Quando você precisa aprender algo novo rapidamente, tende a...",
            listOf(
                Alternativa("Fazer anotações, criar um resumo e estruturar o conteúdo.", "C"), // Humanas
                Alternativa("Testar direto na prática, aprendendo com o erro.", "A"), // Exatas/Tec
                Alternativa("Buscar vídeos, exemplos visuais e formas diferentes de entender.", "B"), // Artes/Design
                Alternativa("Conversar com alguém que já sabe e trocar experiências.", "C") // Educação/Psicologia
            )
        ),
        Pergunta(
            "Ao observar um problema no seu dia a dia, sua primeira reação é...",
            listOf(
                Alternativa("Pensar em como resolver com lógica ou ferramentas.", "A"), // Engenharia/TI
                Alternativa("Imaginar uma forma inovadora de contornar ou transformar.", "B"), // Criativas
                Alternativa("Querer entender o impacto disso nas pessoas.", "C"), // Humanas/Saúde
                Alternativa("Analisar o contexto e as causas por trás.", "D") // Ciências/Pesquisa
            )
        ),
        Pergunta(
            "Em um projeto novo, o que mais te empolga?",
            listOf(
                Alternativa("Estruturar o plano e definir metas.", "A"), // Gestão
                Alternativa("Criar algo único e expressivo.", "B"), // Design/Comunicação
                Alternativa("Explorar ideias e fazer testes.", "D"), // Ciência/Tecnologia
                Alternativa("Trabalhar com outras pessoas para alcançar um resultado coletivo.", "C") // Educação/Social
            )
        ),
        Pergunta(
            "Quando tem uma tarefa longa e complexa, você...",
            listOf(
                Alternativa("Divide em partes e faz passo a passo.", "A"), // Exatas
                Alternativa("Vai fazendo do seu jeito e ajusta conforme o progresso.", "B"), // Criativo
                Alternativa("Gosta de trabalhar com alguém para manter o ritmo.", "C"), // Social
                Alternativa("Pensa antes em todo o processo e possíveis dificuldades.", "D") // Analítico/Ciências
            )
        ),
        Pergunta(
            "Qual tipo de atividade te deixa mais satisfeito no fim do dia?",
            listOf(
                Alternativa("Aquela que trouxe resultados concretos.", "A"), // Engenharia/Admin.
                Alternativa("Aquela que permitiu expressar ideias novas.", "B"), // Artes/Design
                Alternativa("Aquela que ajudou ou inspirou alguém.", "C"), // Psicologia/Educação
                Alternativa("Aquela que te ensinou algo novo sobre o mundo.", "D") // Pesquisa/Ciência
            )
        ),
        Pergunta(
            "Quando algo dá errado, o que você costuma fazer primeiro?",
            listOf(
                Alternativa("Ver onde houve falha técnica e corrigir.", "A"), // Exatas
                Alternativa("Tentar outro caminho criativo.", "B"), // Artes
                Alternativa("Conversar para entender o que aconteceu.", "C"), // Humanas
                Alternativa("Refletir e anotar o aprendizado.", "D") // Ciência
            )
        ),
        Pergunta(
            "Você se sente mais confiante quando...",
            listOf(
                Alternativa("Consegue dominar uma ferramenta ou método.", "A"), // Tecnologia/Eng.
                Alternativa("As pessoas valorizam suas ideias e energia.", "B"), // Comunicação/Empreender
                Alternativa("Alguém te agradece por uma ajuda ou conselho.", "C"), // Educação/Saúde
                Alternativa("Descobre uma explicação ou lógica nova.", "D") // Pesquisa/Filosofia
            )
        ),
        Pergunta(
            "Se pudesse participar de um grande projeto, escolheria...",
            listOf(
                Alternativa("Construir ou programar algo funcional.", "A"), // TI/Eng.
                Alternativa("Criar campanhas, vídeos ou soluções visuais.", "B"), // Design/Publicidade
                Alternativa("Fazer entrevistas e entender comportamentos.", "C"), // Psicologia/Sociologia
                Alternativa("Coletar dados e comprovar hipóteses.", "D") // Ciências Naturais
            )
        ),
        Pergunta(
            "Quando pensa em 'sucesso', o que te vem à mente?",
            listOf(
                Alternativa("Ter estabilidade e reconhecimento profissional.", "A"), // Gestão/Técnico
                Alternativa("Ser lembrado pela criatividade e originalidade.", "B"), // Artes
                Alternativa("Ajudar pessoas e deixar impacto positivo.", "C"), // Educação/Social
                Alternativa("Contribuir para o conhecimento humano.", "D") // Pesquisa
            )
        ),
        Pergunta(
            "Como reage quando alguém discorda de você?",
            listOf(
                Alternativa("Argumenta com dados e lógica.", "A"), // Direito/Exatas
                Alternativa("Procura entender o ponto de vista e achar equilíbrio.", "C"), // Psicologia
                Alternativa("Usa exemplos criativos para explicar o seu ponto.", "B"), // Comunicação
                Alternativa("Analisa o motivo da discordância e reflete sobre ela.", "D") // Filosofia
            )
        ),
        Pergunta(
            "Qual dessas tarefas você considera mais prazerosa?",
            listOf(
                Alternativa("Organizar, planejar e colocar em prática.", "A"), // Administração
                Alternativa("Criar, ilustrar ou inventar algo.", "B"), // Artes
                Alternativa("Ensinar ou orientar alguém.", "C"), // Educação
                Alternativa("Pesquisar e descobrir informações.", "D") // Ciência
            )
        ),
        Pergunta(
            "Se tivesse um tempo livre de 3 horas, o que te atrairia mais?",
            listOf(
                Alternativa("Resolver algo prático — montar, consertar, programar.", "A"), // Exatas
                Alternativa("Criar algo — pintar, escrever, editar vídeos.", "B"), // Artes
                Alternativa("Conversar, ajudar alguém ou ouvir histórias.", "C"), // Psicologia/Social
                Alternativa("Ler, estudar ou fazer anotações de curiosidades.", "D") // Ciência/Pesquisa
            )
        ),
        Pergunta(
            "Quando trabalha sob pressão, tende a...",
            listOf(
                Alternativa("Focar totalmente na solução e agir rápido.", "A"), // Gestão/Eng.
                Alternativa("Manter a calma e seguir o plano.", "D"), // Ciências/Organização
                Alternativa("Motivar o grupo e manter o clima leve.", "C"), // Social/Comunicação
                Alternativa("Parar um instante e refletir no sentido da tarefa.", "D") // Filosofia/Humanas
            )
        ),
        Pergunta(
            "Em uma conversa com amigos, você costuma...",
            listOf(
                Alternativa("Falar sobre projetos, ideias e soluções práticas.", "A"), // Empreendedorismo
                Alternativa("Contar histórias ou trazer humor e emoção.", "B"), // Comunicação
                Alternativa("Escutar e dar conselhos.", "C"), // Psicologia
                Alternativa("Levantar reflexões e teorias.", "D") // Filosofia/Ciência
            )
        ),
        Pergunta(
            "Ao ver um problema social, você se imagina...",
            listOf(
                Alternativa("Criando uma ferramenta ou tecnologia para resolver.", "A"), // Eng/TI
                Alternativa("Fazendo campanhas e mobilizando pessoas.", "B"), // Comunicação/Social
                Alternativa("Ajudando diretamente quem precisa.", "C"), // Educação/Saúde
                Alternativa("Estudando as causas e impactos do problema.", "D") // Ciências Sociais
            )
        ),
        Pergunta(
            "Quando começa um novo projeto, o que te desafia mais?",
            listOf(
                Alternativa("Transformar ideias em algo prático.", "A"), // Exatas
                Alternativa("Escolher o melhor caminho criativo.", "B"), // Design
                Alternativa("Manter o foco por muito tempo.", "C"), // Humanas
                Alternativa("Garantir que tudo esteja perfeitamente planejado.", "D") // Engenharia/Ciência
            )
        ),
        Pergunta(
            "O que mais te incomoda em um ambiente de trabalho?",
            listOf(
                Alternativa("Falta de organização e eficiência.", "A"), // Exatas
                Alternativa("Falta de liberdade e expressão.", "B"), // Artes
                Alternativa("Falta de empatia e colaboração.", "C"), // Humanas
                Alternativa("Falta de propósito e aprendizado.", "D") // Ciência/Filosofia
            )
        ),
        Pergunta(
            "Se você pudesse ser reconhecido por algo, gostaria que fosse por...",
            listOf(
                Alternativa("Sua capacidade de resolver problemas.", "A"), // Exatas
                Alternativa("Sua originalidade e visão criativa.", "B"), // Artes
                Alternativa("Sua sensibilidade e apoio às pessoas.", "C"), // Psicologia
                Alternativa("Sua inteligência e profundidade de pensamento.", "D") // Ciências/Filosofia
            )
        )
    )

    // --- ESTADOS DO TESTE ---
    var perguntaAtual by remember { mutableStateOf(0) }
    val respostas = remember { mutableStateListOf<String?>(*Array(perguntas.size) { null }) }
    var resultado by remember { mutableStateOf<String?>(null) }
    var mostrarDesempate by remember { mutableStateOf(false) }
    var letrasEmpatadas by remember { mutableStateOf(listOf<String>()) }
    var perguntaDesempate by remember { mutableStateOf<Pergunta?>(null) }
    var areaForte by remember { mutableStateOf<String?>(null) }
    var areaFraca by remember { mutableStateOf<String?>(null) }
    var etapaExtra by remember { mutableStateOf(false) }


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
            // --------------- ETAPA: DESEMPATE ----------------
            if (mostrarDesempate && perguntaDesempate != null) {

                // Pergunta
                Text(
                    text = perguntaDesempate!!.texto,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                // Alternativas filtradas
                perguntaDesempate!!.alternativas.forEach { alt ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color(0xFFF0F0F0))
                            .clickable {
                                // RESPOSTA FINAL
                                resultado = alt.tipo
                                mostrarDesempate = false
                            }
                            .padding(14.dp)
                    ) {
                        Text(text = alt.texto)
                    }
                }

                return@Column // ← evita desenhar o restante da tela
            }

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

                                val max = contagem.values.maxOrNull()
                                val empatados = contagem.filter { it.value == max }.keys.toList()

                                if (empatados.size == 1) {
                                    // Não tem empate → resultado final
                                    resultado = empatados.first()
                                } else {
                                    // TEM EMPATE → ativar pergunta de desempate
                                    letrasEmpatadas = empatados
                                    mostrarDesempate = true

                                    perguntaDesempate = gerarPerguntaDesempate(empatados)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueNavy)
                    ) {
                        Text(if (perguntaAtual == perguntas.size - 1) "Enviar respostas" else "Próxima")
                    }
                }
            }

            // --------------- ETAPA: PERGUNTAS EXTRAS ----------------
            else if (!etapaExtra) {

                Text(
                    text = "Para finalizar, nos diga um pouco mais sobre você.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                // Pergunta 1 - Área forte
                Text("Qual é sua área de conhecimento mais forte?")
                Spacer(Modifier.height(8.dp))

                listOf("Ciências Humanas", "Matemática e suas Tecnologias", "Ciências da Natureza", "Linguagens e Códigos").forEach { area ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(if (areaForte == area) BlueNavy else Color(0xFFF2F2F2))
                            .clickable { areaForte = area }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = area,
                            color = if (areaForte == area) Color.White else Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Pergunta 2 - Área fraca
                Text("E qual é sua área de maior dificuldade?")
                Spacer(Modifier.height(8.dp))

                listOf("Ciências Humanas", "Matemática e suas Tecnologias", "Ciências da Natureza", "Linguagens e Códigos").forEach { area ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(if (areaFraca == area) BlueNavy else Color(0xFFF2F2F2))
                            .clickable { areaFraca = area }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = area,
                            color = if (areaFraca == area) Color.White else Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(30.dp))

                Button(
                    onClick = {
                        if (areaForte != null && areaFraca != null) {
                            val forteId = tokenStorage.areaEducacionalIds[areaForte]
                            val fracaId = tokenStorage.areaEducacionalIds[areaFraca]
                            tokenStorage.saveForcaEducacionalId(forteId!!)
                            tokenStorage.saveFraquezaEducacionalId(fracaId!!)
                            etapaExtra = true // libera a etapa final
                        }
                    },
                    enabled = areaForte != null && areaFraca != null,
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNavy),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar")
                }
            }

            // --------------- ETAPA: RESULTADO ----------------
            else {
                val letraFinal = resultado ?: ""

                val listaAreas = areasMapeadas[letraFinal] ?: emptyList()

                // 🔥 SALVAR NO TOKEN STORAGE A LISTA DE ÁREAS
                tokenStorage.saveAreasRecomendadas(listaAreas)

                val areas = listaAreas.joinToString(", ")

                val descricao = when (letraFinal) {
                    "A" -> "Você possui um perfil voltado para lógica, exatas, tecnologia e resolução prática de problemas."
                    "B" -> "Seu perfil é criativo, comunicativo e expressivo. Áreas de artes e design combinam com você."
                    "C" -> "Você tem vocação para trabalhar com pessoas, educação, cuidado e desenvolvimento humano."
                    "D" -> "Você possui perfil analítico, investigativo e reflexivo, ideal para pesquisa e ciências."
                    else -> "Não foi possível determinar sua vocação."
                }

                ResultadoVocacional(
                    tipo = letraFinal,
                    recomendacao = areas,
                    descricao = descricao,
                    onConcluir = onBack
                )
            }
        }
    }
}

fun gerarPerguntaDesempate(letras: List<String>): Pergunta {
    val mapaAlternativas = mapOf(
        "A" to Alternativa("Resolver problemas práticos com lógica.", "A"),
        "B" to Alternativa("Criar uma solução criativa e visual.", "B"),
        "C" to Alternativa("Ajudar, ouvir e entender pessoas.", "C"),
        "D" to Alternativa("Pesquisar, analisar ou compreender profundamente.", "D")
    )

    val alternativasFiltradas = letras.mapNotNull { mapaAlternativas[it] }

    return Pergunta(
        texto = "Qual das alternativas abaixo te representa melhor?",
        alternativas = alternativasFiltradas
    )
}

@Composable
fun ResultadoVocacional(
    tipo: String,
    recomendacao: String,
    descricao: String,
    onConcluir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Sua Vocação:",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = tipo,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = BlueNavy
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Áreas recomendadas:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = recomendacao,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Descrição:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = descricao,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(30.dp))

        Button(onClick = onConcluir) {
            Text("Concluir")
        }
    }
}