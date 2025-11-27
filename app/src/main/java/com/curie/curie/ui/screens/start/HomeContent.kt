package com.curie.curie.ui.screens.start

import ModernCountCard
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.data.model.*
import com.curie.curie.data.model.enums.Demanda
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.BlueNavy
import java.time.LocalDate
import java.time.LocalDateTime

// -------------------------------------------------------
// 🔧 Função para interpretar datas ISO com segurança
// Aceita: "yyyy-MM-dd" OU "yyyy-MM-ddTHH:mm:ss"
// -------------------------------------------------------
@RequiresApi(Build.VERSION_CODES.O)
fun parseIsoDate(date: String?): LocalDate {
    if (date.isNullOrBlank()) return LocalDate.MAX

    return try {
        LocalDateTime.parse(date).toLocalDate() // tenta ISO completo
    } catch (e: Exception) {
        LocalDate.parse(date.substring(0, 10))  // fallback somente data
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    userName: String?,
    tarefas: List<Tarefa>,
    metas: List<Meta>,
    descricaoPerfil: String,
    forcaEducacional: String,
    fraquezaEducacional: String,
    profissao: Profissao?,
    graduacao: Graduacao?,
    posGraduacao: PosGraduacao?
) {

    val bg = Color(0xFFF5F7FA)   // cinza neutro e suave
    val cardBg = Color.White.copy(alpha = 0.95f)
    val titleColor = Color(0xFF102A43)
    val subtextColor = Color(0xFF627D98)

    val nextMeta = metas
        .filter { it.status == Status.pendente }
        .minByOrNull { parseIsoDate(it.fim) }

    val nextTask = tarefas
        .filter { it.status == Status.pendente }
        .minByOrNull { parseIsoDate(it.prazo) }

    val nextEvent = listOfNotNull(
        nextMeta?.let { Triple(it.objetivo, it.fim, "Meta") },
        nextTask?.let { Triple(it.nome, it.prazo, "Tarefa") }
    ).minByOrNull { parseIsoDate(it.second) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {

        // -------------------------------------------------------
        // 🔵 HEADER Moderno
        // -------------------------------------------------------
        // -------------------------------------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            BlueNavy,
                            BlueNavy.copy(alpha = .85f)
                        )
                    )
                )
                .padding(vertical = 28.dp, horizontal = 22.dp)
        ) {

            val hour = LocalDateTime.now().hour
            val greeting = when (hour) {
                in 5..11 -> "Bom dia"
                in 12..17 -> "Boa tarde"
                else -> "Boa noite"
            }

            // 🔥 FRASES DE IMPACTO (as mesmas que você usava antes)
            val frases = listOf(
                "Nada na vida deve ser temido, apenas compreendido. — Marie Curie",
                "A melhor maneira de prever o futuro é criá-lo. — Peter Drucker",
                "A educação é a arma mais poderosa que você pode usar para mudar o mundo. — Nelson Mandela",
                "Só se pode alcançar um grande êxito quando nos mantemos fiéis a nós mesmos. — Friedrich Nietzsche",
                "A mente que se abre a uma nova ideia jamais volta ao seu tamanho original. — Albert Einstein",
                "A tarefa do educador moderno não é cortar florestas, mas irrigar desertos. — C.S. Lewis",
                "Não existe aprendizado sem humildade para ouvir e coragem para tentar. — Paulo Freire",
                "O homem não é nada além daquilo que a educação faz dele. — Immanuel Kant"
            )

            val fraseAleatoria = frases.random()

            Column {
                Text(
                    text = "$greeting, ${userName ?: "Usuário"}!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                // 🔥 AQUI: frase motivacional abaixo da saudação
                Text(
                    text = fraseAleatoria,
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = .85f)
                )
            }
        }


        // -------------------------------------------------------
        // LISTA PRINCIPAL
        // -------------------------------------------------------
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // ---------------------------------------------------
            // 🎯 CARDS DE CONTAGEM Modernos
            // ---------------------------------------------------
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCountCard(
                        title = "Tarefas Pendentes",
                        count = tarefas.count { it.status == Status.pendente },
                        modifier = Modifier.weight(1f)
                    )

                    ModernCountCard(
                        title = "Metas Ativas",
                        count = metas.count { it.status == Status.pendente },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ---------------------------------------------------
            // ⏳ PRÓXIMO EVENTO Moderno
            // ---------------------------------------------------
            item {
                if (nextEvent != null) {
                    val (titulo, data, tipo) = nextEvent

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = BlueNavy),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {

                        Column(
                            Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Próxima $tipo".uppercase(),
                                color = Color(0xFFCBD9FF),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = titulo ?: "Sem título",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(18.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(6.dp))

                                Text(
                                    text = data ?: "Sem data",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Nenhum evento pendente",
                        color = subtextColor,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }

            // DIVISOR SUAVE
            item {
                HorizontalDivider(
                    color = Color.Black.copy(alpha = .05f),
                    thickness = 1.dp
                )
            }

            // ---------------------------------------------------
            // 🔍 ANÁLISE DO PERFIL Moderna
            // ---------------------------------------------------
            item {
                SectionCard(
                    title = "Análise do Perfil",
                    contentColor = titleColor,
                    cardBg = cardBg
                ) {

                    if (descricaoPerfil.isNotBlank() &&
                        forcaEducacional.isNotBlank() &&
                        fraquezaEducacional.isNotBlank()
                    ) {

                        Text(
                            text = descricaoPerfil,
                            fontSize = 15.sp,
                            color = subtextColor
                        )

                        Spacer(Modifier.height(14.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ProfilePill("Ponto forte", forcaEducacional, Color(0xFF4CAF50))
                            ProfilePill("Ponto fraco", fraquezaEducacional, Color(0xFFE53935))
                        }

                    } else {
                        Text(
                            text = "Complete seus testes para liberar esta análise.",
                            color = subtextColor,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            // ---------------------------------------------------
            // 🎓 CARREIRA Moderna
            // ---------------------------------------------------
            item {
                SectionCard(
                    title = "Plano de Carreira",
                    contentColor = titleColor,
                    cardBg = cardBg
                ) {

                    if (profissao != null && graduacao != null && posGraduacao != null) {

                        InfoLine("Profissão escolhida", profissao.nome)
                        InfoLine("Demanda do mercado", profissao.demanda.name)
                        Spacer(Modifier.height(10.dp))
                        InfoLine("Graduação", graduacao.nome)
                        InfoLine("Pós-graduação", posGraduacao.nome)
                        Spacer(Modifier.height(10.dp))
                        InfoLine("Área da carreira", graduacao.areaCarreira.nome)

                    } else {
                        Text(
                            text = "Complete os testes de carreira para ver seu plano.",
                            color = subtextColor,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    contentColor: Color,
    cardBg: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(20.dp)) {

            Text(
                text = title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            Spacer(Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
fun ProfilePill(title: String, value: String, color: Color) {
    Surface(
        color = color.copy(alpha = .15f),
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$title: ",
                fontWeight = FontWeight.SemiBold,
                color = color,
                fontSize = 14.sp
            )
            Text(
                text = value,
                color = color.darken(0.4f),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InfoLine(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF7B8794)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF102A43)
        )
        Spacer(Modifier.height(10.dp))
    }
}

fun Color.darken(amount: Float): Color {
    return Color(
        red = (red * (1 - amount)).coerceIn(0f, 1f),
        green = (green * (1 - amount)).coerceIn(0f, 1f),
        blue = (blue * (1 - amount)).coerceIn(0f, 1f),
        alpha = alpha
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {

    val fakeTarefas = listOf(
        Tarefa(1, 1, "Estudar Compose", "2025-10-20T00:00:00", Prioridade.alta, Status.pendente),
        Tarefa(2, 1, "Finalizar projeto", "2025-10-25T00:00:00", Prioridade.media, Status.concluida),
        Tarefa(3, 1, "Revisar código", "2025-10-18T00:00:00", Prioridade.baixa, Status.pendente)
    )

    val fakeMetas = listOf(
        Meta(
            1, 1, "Meta de Estudo",
            "Aprimorar habilidades em Kotlin",
            inicio = "2025-10-15T00:00:00",
            fim = "2025-10-22T00:00:00",
            prioridade = Prioridade.alta,
            status = Status.pendente
        ),
        Meta(
            2, 1, "Meta de Saúde",
            "Praticar exercícios 3x por semana",
            inicio = "2025-10-10T00:00:00",
            fim = "2025-10-30T00:00:00",
            prioridade = Prioridade.media,
            status = Status.pendente
        )
    )

    val fakeAreaCarreira = AreaCarreira(
        id = 1L,
        nome = "Tecnologia da Informação",
        descricao = "Área focada no desenvolvimento, manutenção e gestão de sistemas computacionais."
    )

    val fakeProfissao = Profissao(
        id = 1L,
        nome = "Desenvolvedor de Software",
        descricao = "Profissional responsável por criar soluções digitais.",
        salario = 8500.0,
        demanda = Demanda.alta
    )

    val fakeGraduacao = Graduacao(
        id = 1L,
        nome = "Ciência da Computação",
        descricao = "Curso focado em desenvolvimento e engenharia de software.",
        duracao = 4,
        areaCarreira = fakeAreaCarreira
    )

    val fakePosGraduacao = PosGraduacao(
        id = 1L,
        nome = "Especialização em Engenharia de Software",
        descricao = "Focado em arquitetura, padrões e projeto de software.",
        duracao = 18,
        areaCarreira = fakeAreaCarreira
    )

    HomeContent(
        userName = "Usuário",
        tarefas = fakeTarefas,
        metas = fakeMetas,
        descricaoPerfil = "Pessoa organizada e focada em resultados.",
        forcaEducacional = "Aprendizado rápido e lógica forte.",
        fraquezaEducacional = "Dificuldade em delegar tarefas.",
        profissao = fakeProfissao,
        graduacao = fakeGraduacao,
        posGraduacao = fakePosGraduacao
    )
}
