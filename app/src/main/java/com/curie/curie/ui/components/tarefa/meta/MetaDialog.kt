package com.curie.curie.ui.components.tarefa.meta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.components.tarefa.DatePickerField
import com.curie.curie.ui.theme.BlueNavy
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetaDialog(
    meta: Meta?,
    userId: Long,
    onDismiss: () -> Unit,
    onSave: (Meta) -> Unit
) {

    var objetivo by remember { mutableStateOf(meta?.objetivo ?: "") }
    var descricao by remember { mutableStateOf(meta?.descricao ?: "") }
    var inicio by remember { mutableStateOf(meta?.inicio ?: "") }
    var fim by remember { mutableStateOf(meta?.fim ?: "") }
    var prioridade by remember { mutableStateOf(meta?.prioridade ?: Prioridade.baixa) }
    var status by remember { mutableStateOf(meta?.status ?: Status.pendente) }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(8.dp)
                .background(Color.Transparent)
        ) {

            Column(modifier = Modifier.padding(22.dp)) {

                Text(
                    text = if (meta == null) "Nova Meta" else "Editar Meta",
                    style = MaterialTheme.typography.headlineSmall,
                    color = BlueNavy
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = objetivo,
                    onValueChange = { objetivo = it },
                    label = { Text("Objetivo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // INÍCIO
                DatePickerField(
                    label = "Início",
                    initialValue = inicio,
                    onDateSelected = { inicio = it }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // FIM
                DatePickerField(
                    label = "Fim",
                    initialValue = fim,
                    onDateSelected = { fim = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // PRIORIDADE
                var prioridadeExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = prioridadeExpanded,
                    onExpandedChange = { prioridadeExpanded = !prioridadeExpanded }
                ) {
                    OutlinedTextField(
                        value = prioridade.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Prioridade") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = prioridadeExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = prioridadeExpanded,
                        onDismissRequest = { prioridadeExpanded = false }
                    ) {
                        Prioridade.values().forEach { p ->
                            DropdownMenuItem(
                                text = { Text(p.name) },
                                onClick = {
                                    prioridade = p
                                    prioridadeExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // STATUS
                var statusExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    OutlinedTextField(
                        value = status.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        Status.values().forEach { s ->
                            DropdownMenuItem(
                                text = { Text(s.name) },
                                onClick = {
                                    status = s
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    TextButton(
                        onClick = {
                            onSave(
                                Meta(
                                    id = meta?.id ?: 0L,
                                    userId = userId,
                                    objetivo = objetivo,
                                    descricao = descricao,
                                    inicio = inicio,
                                    fim = fim,
                                    prioridade = prioridade,
                                    status = status
                                )
                            )
                        }
                    ) {
                        Text("Salvar", color = BlueNavy)
                    }
                }
            }
        }
    }
}
