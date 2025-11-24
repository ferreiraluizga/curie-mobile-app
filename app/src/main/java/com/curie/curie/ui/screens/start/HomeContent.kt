package com.curie.curie.ui.screens.start

import ModernCountCard
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.data.model.AreaCarreira
import com.curie.curie.data.model.Graduacao
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.PosGraduacao
import com.curie.curie.data.model.Profissao
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Demanda
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.BlueNavy
import java.time.LocalDate
import java.time.LocalDateTime

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
    graducacao: Graduacao?,
    posGraduacao: PosGraduacao?
    ) {
    val proximaMeta = metas
        .filter { it.status == Status.pendente }
        .minByOrNull { it.fim?.let(LocalDate::parse) ?: LocalDate.MAX }

    val proximaTarefa = tarefas
        .filter { it.status == Status.pendente }
        .minByOrNull { it.prazo?.let(LocalDate::parse) ?: LocalDate.MAX }

    val proximoEvento = listOfNotNull(
        proximaMeta?.let { Triple(it.objetivo, it.fim, "Meta") },
        proximaTarefa?.let { Triple(it.nome, it.prazo, "Tarefa") }
    ).minByOrNull { it.second?.let(LocalDate::parse) ?: LocalDate.MAX }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
    ) {

        // 🔵 Header moderno igual o da tela original
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueNavy)
                .padding(vertical = 32.dp, horizontal = 24.dp)
        ) {
            val currentHour = LocalDateTime.now().hour

            val greeting = when (currentHour) {
                in 5..11 -> "Bom dia"
                in 12..17 -> "Boa tarde"
                else -> "Boa noite"
            }

            val motivationalPhrases = listOf(
                "“Educação é a arma mais poderosa que você pode usar para mudar o mundo.” — Nelson Mandela",
                "“A única pessoa educada é aquela que aprendeu a aprender e a mudar.” — Carl Rogers",
                "“Nada na vida deve ser temido, apenas compreendido.” — Marie Curie",
                "“A raiz da educação é amarga, mas seus frutos são doces.” — Aristóteles",
                "“O objetivo da educação não é aumentar o conhecimento, mas criar possibilidades para a descoberta.” — Jean Piaget"
            )
            val phraseOfTheDay = motivationalPhrases.random()

            Column {
                Text(
                    text = "$greeting, ${userName ?: "Usuário"}!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = phraseOfTheDay,
                    color = Color(0xFFD9D9D9),
                    fontSize = 14.sp
                )
            }
        }

        // LISTAGEM
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🎯 Cards de contadores modernos
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // TAREFAS
                    ModernCountCard(
                        title = "TAREFAS RESTANTES",
                        count = tarefas.count { it.status == Status.pendente },
                        modifier = Modifier.weight(1f)
                    )

                    // METAS
                    ModernCountCard(
                        title = "METAS EM ANDAMENTO",
                        count = metas.count { it.status == Status.pendente },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ⏳ Próximo Evento
            item {
                if (proximoEvento != null) {
                    val (titulo, data, tipo) = proximoEvento

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = BlueNavy),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "PRÓXIMA $tipo".uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFB8C4FF)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = titulo ?: "Sem título",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = data ?: "Sem data",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                } else {
                    Text(
                        text = "Nenhuma meta ou tarefa pendente",
                        color = Color.DarkGray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item { HorizontalDivider() }

            // 🔍 Modernização da Análise de Perfil e Plano de Carreira
            item {
                // Card da análise de perfil
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BlueNavy),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Análise do Perfil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB8C4FF) // texto claro sobre azul
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (descricaoPerfil.isNotBlank() &&
                            forcaEducacional.isNotBlank() &&
                            fraquezaEducacional.isNotBlank()
                        ) {
                            Text(
                                text = descricaoPerfil,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "Ponto forte: $forcaEducacional",
                                    fontSize = 15.sp,
                                    color = Color(0xFFB2FF59), // verde claro
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Ponto fraco: $fraquezaEducacional",
                                    fontSize = 15.sp,
                                    color = Color(0xFFFF5252), // vermelho claro
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        } else {
                            Text(
                                text = "Você precisa realizar os testes de perfil para liberar esta análise.",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }

            item {
                // Card da análise do plano de carreira
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BlueNavy),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Plano de Carreira",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB8C4FF)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (profissao != null && graducacao != null && posGraduacao != null) {

                            Text(
                                text = "Profissão escolhida: ${profissao.nome}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Demanda do mercado: ${profissao.demanda}",
                                fontSize = 15.sp,
                                color = Color(0xFF82B1FF) // azul claro
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Graduação: ${graducacao.nome}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Pós-graduação: ${posGraduacao.nome}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Área da carreira: ${graducacao.areaCarreira.nome}",
                                fontSize = 16.sp,
                                color = Color(0xFFB8C4FF)
                            )

                        } else {
                            Text(
                                text = "Você precisa realizar os testes de carreira para liberar esta análise.",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {

    val fakeTarefas = listOf(
        Tarefa(1, 1, "Estudar Compose", "2025-10-20", Prioridade.alta, Status.pendente),
        Tarefa(2, 1, "Finalizar projeto", "2025-10-25", Prioridade.media, Status.concluida),
        Tarefa(3, 1, "Revisar código", "2025-10-18", Prioridade.baixa, Status.pendente)
    )

    val fakeMetas = listOf(
        Meta(
            1, 1, "Meta de Estudo",
            "Aprimorar habilidades em Kotlin",
            inicio = "2025-10-15",
            fim = "2025-10-22",
            prioridade = Prioridade.alta,
            status = Status.pendente
        ),
        Meta(
            2, 1, "Meta de Saúde",
            "Praticar exercícios 3x por semana",
            inicio = "2025-10-10",
            fim = "2025-10-30",
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
        graducacao = fakeGraduacao,
        posGraduacao = fakePosGraduacao
    )
}