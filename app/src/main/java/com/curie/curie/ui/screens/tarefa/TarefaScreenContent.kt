package com.curie.curie.ui.screens.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.components.TabsSection
import com.curie.curie.ui.components.TarefaItem
import com.curie.curie.ui.components.TarefaTopBar
import com.curie.curie.ui.components.WeekCalendar
import com.curie.curie.ui.theme.BlueNavy
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreenContent(
    tarefas: List<Tarefa>,
    onEdit: (Tarefa) -> Unit = {},
    onDelete: (Tarefa) -> Unit = {},
    onSave: (Tarefa) -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.White,
        darkIcons = true
    )

    var showDialog by remember { mutableStateOf(false) }
    var editTarefa by remember { mutableStateOf<Tarefa?>(null) }

    Scaffold(
        topBar = {
            var query by remember { mutableStateOf("") }

            TarefaTopBar(
                query = query,
                onQueryChange = { query = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { editTarefa = null; showDialog = true },
                shape = CircleShape,
                containerColor = BlueNavy,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar tarefa",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                WeekCalendar()

                Spacer(Modifier.height(16.dp))

                TabsSection()

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tarefas) { tarefa ->
                        TarefaItem(
                            tarefa = tarefa,
                            onEdit = {
                                editTarefa = it
                                showDialog = true
                            },
                            onDelete = { onDelete(it) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        TarefaDialog(
            tarefa = editTarefa,
            userId = 0L, // Para preview ou teste
            onDismiss = { showDialog = false },
            onSave = { tarefa ->
                onSave(tarefa)
                showDialog = false
            }
        )
    }
}

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
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = prioridadeExpanded) }
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
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) }
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
                        id = tarefa?.id ?: 0L,
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