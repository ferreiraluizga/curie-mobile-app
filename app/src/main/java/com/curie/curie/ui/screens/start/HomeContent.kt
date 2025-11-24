package com.curie.curie.ui.screens.start

import ModernCountCard
import android.R.attr.description
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.Typography
import kotlin.collections.filter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    userName: String?,
    tarefas: List<Tarefa>,
    metas: List<Meta>,
    profissao: String?,
    graduacao: String?,
    pos: String?,
    onLogout: () -> Unit
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
            Column {
                Text(
                    text = "Olá, ${userName ?: "Usuário"}!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Resumo do seu dia",
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
                        title = "TAREFAS",
                        count = tarefas.count { it.status == Status.pendente },
                        modifier = Modifier.weight(1f)
                    )

                    // METAS
                    ModernCountCard(
                        title = "METAS",
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

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {
    // Ajuste conforme seu construtor de Tarefa/Meta real
    val fakeTarefas = listOf(
        Tarefa(1, 1, "Estudar Compose", "2025-10-20", Prioridade.alta, Status.pendente),
        Tarefa(2, 1, "Finalizar projeto", "2025-10-25", Prioridade.media, Status.concluida),
        Tarefa(3, 1, "Revisar código", "2025-10-18", Prioridade.baixa, Status.pendente)
    )

    val fakeMetas = listOf(
        Meta(1, 1, "Meta de Estudo", "Aprimorar habilidades em Kotlin", inicio = "2025-10-15", fim = "2025-10-15", prioridade = Prioridade.alta, status = Status.pendente),
        Meta(2, 1, "Meta de Saúde", "Praticar exercícios 3x por semana", inicio = "2025-10-15", fim = "2025-10-15", prioridade = Prioridade.alta, status = Status.pendente)
    )

    HomeContent(
        userName = "teste",
        tarefas = fakeTarefas,
        metas = fakeMetas,
        profissao = "Desenvolvedor Android",
        graduacao = "Análise e Desenvolvimento de Sistemas",
        pos = "Engenharia de Software",
        onLogout = {}
    )
}
