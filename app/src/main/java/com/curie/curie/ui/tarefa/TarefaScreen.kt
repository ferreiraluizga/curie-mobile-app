package com.curie.curie.ui.tarefa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreen(
    userId: Long,
    viewModel: TarefaViewModel = viewModel()
) {
    val tarefas by viewModel.tarefas.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var editTarefa by remember { mutableStateOf<Tarefa?>(null) }

    // Carrega as tarefas
    LaunchedEffect(userId) {
        viewModel.getByUsuario(userId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas Tarefas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { editTarefa = null; showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                error != null -> Text(
                    text = error ?: "Erro desconhecido",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tarefas) { tarefa ->
                        TarefaItem(
                            tarefa = tarefa,
                            onEdit = {
                                editTarefa = it
                                showDialog = true
                            },
                            onDelete = {
                                viewModel.delete(it.id)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        TarefaDialog(
            tarefa = editTarefa,
            userId = userId,
            onDismiss = { showDialog = false },
            onSave = { tarefa ->
                if (tarefa.id == 0L) {
                    viewModel.save(tarefa)
                } else {
                    viewModel.update(tarefa.id, tarefa)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun TarefaItem(
    tarefa: Tarefa,
    onEdit: (Tarefa) -> Unit = {},
    onDelete: (Tarefa) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tarefa.nome, style = MaterialTheme.typography.titleMedium)
            Text(text = "Prazo: ${tarefa.prazo}")
            Text(text = "Prioridade: ${tarefa.prioridade}")
            Text(text = "Status: ${tarefa.status}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { onEdit(tarefa) }) { Text("Editar") }
                TextButton(onClick = { onDelete(tarefa) }) { Text("Excluir") }
            }
        }
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
