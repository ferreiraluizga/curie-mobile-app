package com.curie.curie.ui.components.tarefa.meta

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetaDialog(
    meta: Meta?,         // null = criação, não null = edição
    userId: Long,
    onDismiss: () -> Unit,
    onSave: (Meta) -> Unit
) {
    var objetivo by remember { mutableStateOf(meta?.objetivo ?: "") }
    var descricao by remember { mutableStateOf(meta?.descricao ?: "") }
    var inicio by remember { mutableStateOf(meta?.inicio ?: "") }
    var fim by remember { mutableStateOf(meta?.fim ?: "") }
    var prioridade by remember { mutableStateOf(meta?.prioridade?: Prioridade.baixa) }
    var status by remember { mutableStateOf(meta?.status ?: Status.pendente) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (meta == null) "Nova Meta" else "Editar Meta") },
        text = {
            Column {
                OutlinedTextField(
                    value = objetivo,
                    onValueChange = { objetivo = it },
                    label = { Text("Nome da Meta") }
                )

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )

                OutlinedTextField(
                    value = inicio,
                    onValueChange = { inicio = it },
                    label = { Text("Inicio (ex: 2025-12-31)") }
                )

                OutlinedTextField(
                    value = fim,
                    onValueChange = { fim = it },
                    label = { Text("Fim (ex: 2025-12-31)") }
                )

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
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded)
                        },
                        modifier = Modifier.menuAnchor()
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
            }) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMetaDialog() {
    MetaDialog(
        meta = Meta(
            id = 1L,
            userId = 1L,
            objetivo = "Testar layout",
            descricao = "Descrição teste de uma meta",
            inicio = "2025-10-15",
            fim = "2025-10-15",
            prioridade = Prioridade.alta,
            status = Status.pendente
        ),
        userId = 1L,
        onDismiss = {},
        onSave = {}
    )
}
