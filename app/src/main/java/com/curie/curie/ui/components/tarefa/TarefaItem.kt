package com.curie.curie.ui.components.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.BlueCyan
import com.curie.curie.ui.theme.BlueDark
import com.curie.curie.ui.theme.BlueLight
import com.curie.curie.ui.theme.BluePrimary
import com.curie.curie.ui.theme.CurieTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarefaItem(
    tarefa: Tarefa,
    onEdit: (Tarefa) -> Unit = {},
    onDelete: (Tarefa) -> Unit = {}
) {
    val formattedDate = try {
        val datePart = tarefa.prazo.substring(0, 10)
        val parsedDate = LocalDate.parse(datePart)
        parsedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("pt", "BR")))
    } catch (e: Exception) {
        tarefa.prazo // fallback caso a string não seja válida
    }

    val faixaColor = when (tarefa.prioridade) {
        Prioridade.alta -> BluePrimary
        Prioridade.media -> BlueLight
        Prioridade.baixa -> BlueCyan
        else -> BlueDark
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(faixaColor)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.RadioButtonChecked,
                        contentDescription = "Ícone da tarefa",
                        modifier = Modifier.size(22.dp),
                        tint = faixaColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tarefa.nome,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$formattedDate",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start,
                        color = Color.Gray
                    )

                    Text(
                        text = "Prioridade: ${tarefa.prioridade.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TarefaItemPreview() {
    CurieTheme {
        TarefaItem(
            tarefa = Tarefa(
                id = 1L,
                userId = 1L,
                nome = "Testar layout",
                prazo = "2025-10-15",
                prioridade = Prioridade.alta,
                status = Status.pendente
            )
        )
    }
}

