package com.curie.curie.ui.components.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.BlueNavy

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaDialog(
    tarefa: Tarefa?,
    userId: Long,
    onDismiss: () -> Unit,
    onSave: (Tarefa) -> Unit
) {
    var nome by remember { mutableStateOf(tarefa?.nome ?: "") }
    var prazo by remember { mutableStateOf(tarefa?.prazo ?: "") }
    var prioridade by remember { mutableStateOf(tarefa?.prioridade ?: Prioridade.baixa) }
    var status by remember { mutableStateOf(tarefa?.status ?: Status.pendente) }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(8.dp)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                Text(
                    text = if (tarefa == null) "Nova Tarefa" else "Editar Tarefa",
                    style = MaterialTheme.typography.headlineSmall,
                    color = BlueNavy
                )

                Spacer(modifier = Modifier.height(18.dp))

                // NOME
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome da tarefa") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // DATA
                DatePickerField(
                    label = "Prazo",
                    initialValue = prazo,
                    onDateSelected = { prazo = it } // agora realmente atualiza o update
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
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = prioridadeExpanded
                            )
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
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = statusExpanded
                            )
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
                                Tarefa(
                                    id = tarefa?.id ?: 0L,
                                    userId = userId,
                                    nome = nome,
                                    prazo = prazo,
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
