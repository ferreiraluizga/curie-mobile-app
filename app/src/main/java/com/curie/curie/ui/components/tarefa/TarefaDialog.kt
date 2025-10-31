package com.curie.curie.ui.components.tarefa

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaDialog(
    tarefa: Tarefa?,        // null = criação, não null = edição
    userId: Long,
    onDismiss: () -> Unit,
    onSave: (Tarefa) -> Unit
) {
    var nome by remember { mutableStateOf(tarefa?.nome ?: "") }
    var prazo by remember { mutableStateOf(tarefa?.prazo ?: "") }
    var prioridade by remember { mutableStateOf(tarefa?.prioridade ?: Prioridade.baixa) }
    var status by remember { mutableStateOf(tarefa?.status ?: Status.pendente) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (tarefa == null) "Nova Tarefa" else "Editar Tarefa") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") }
                )
                OutlinedTextField(
                    value = prazo,
                    onValueChange = { prazo = it },
                    label = { Text("Prazo") }
                )

                // Dropdown de Prioridade
                var prioridadeExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = prioridadeExpanded,
                    onExpandedChange = { prioridadeExpanded = !prioridadeExpanded }
                ) {
                    OutlinedTextField(
                        value = prioridade.name,
                        onValueChange = {},
                        label = { Text("Prioridade") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = prioridadeExpanded) },
                        modifier = Modifier.menuAnchor() // <--- ESSENCIAL
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

                // Dropdown de Status
                var statusExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    OutlinedTextField(
                        value = status.name,
                        onValueChange = {},
                        label = { Text("Status") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                        modifier = Modifier.menuAnchor() // ESSENCIAL para o menu abrir
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
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    Tarefa(
                        id = tarefa?.id ?: 0L, // mantém id da tarefa existente
                        userId = userId,
                        nome = nome,
                        prazo = prazo,
                        prioridade = prioridade,
                        status = status
                    )
                )
            }) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
