package com.curie.curie.ui.screens.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.components.tarefa.*
import com.curie.curie.ui.components.tarefa.meta.MetaDialog
import com.curie.curie.ui.theme.BlueNavy

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreenContent(
    tarefas: List<Tarefa>,
    metas: List<Meta>,
    userId: Long,
    onEditTarefa: (Tarefa) -> Unit = {},
    onDeleteTarefa: (Tarefa) -> Unit = {},
    onSaveTarefa: (Tarefa) -> Unit = {},
    onEditMeta: (Meta) -> Unit = {},
    onDeleteMeta: (Meta) -> Unit = {},
    onSaveMeta: (Meta) -> Unit = {}
) {
    // Estados internos
    var selectedTab by remember { mutableStateOf("Tarefas") }

    var showTarefaDialog by remember { mutableStateOf(false) }
    var tarefaDialogData by remember { mutableStateOf<Tarefa?>(null) }

    var showMetaDialog by remember { mutableStateOf(false) }
    var metaDialogData by remember { mutableStateOf<Meta?>(null) }

    var showActionDialog by remember { mutableStateOf(false) }
    var selectedTarefa by remember { mutableStateOf<Tarefa?>(null) }

    Scaffold(
        topBar = {
            var query by remember { mutableStateOf("") }
            TarefaTopBar(query = query, onQueryChange = { query = it })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTab == "Tarefas") {
                        tarefaDialogData = Tarefa(
                            id = 0L,
                            userId = userId,
                            nome = "",
                            prazo = "",
                            prioridade = Prioridade.baixa,
                            status = Status.pendente
                        )
                        showTarefaDialog = true

                    } else {
                        metaDialogData = Meta(
                            id = 0L,
                            userId = userId,
                            objetivo = "",
                            descricao = "",
                            inicio = "",
                            fim = "",
                            prioridade = Prioridade.baixa,
                            status = Status.pendente
                        )
                        showMetaDialog = true
                    }
                },
                shape = CircleShape,
                containerColor = BlueNavy,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Adicionar item")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            WeekCalendar()
            Spacer(Modifier.height(16.dp))

            TabsSection(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(16.dp))

            when (selectedTab) {

                "Tarefas" -> {
                    if (tarefas.isEmpty()) {
                        Text(
                            text = "Nenhuma tarefa cadastrada.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(tarefas) { tarefa ->
                                TarefaItem(
                                    tarefa = tarefa,
                                    onClick = {
                                        selectedTarefa = tarefa
                                        showActionDialog = true
                                    },
                                    onEdit = { tarefaToEdit ->
                                        tarefaDialogData = tarefaToEdit.copy()
                                        showTarefaDialog = true
                                    },
                                    onDelete = onDeleteTarefa
                                )
                            }
                        }
                    }
                }

                "Metas" -> {
                    MetaContent(
                        metas = metas,
                        userId = userId,
                        onEdit = { meta ->
                            metaDialogData = meta.copy()
                            showMetaDialog = true
                        },
                        onDelete = onDeleteMeta,
                        onSave = onSaveMeta
                    )
                }
            }
        }
    }

    // ---------- ACTION DIALOG PARA TAREFA ----------
    if (showActionDialog && selectedTarefa != null) {
        AlertDialog(
            onDismissRequest = { showActionDialog = false },
            title = { Text("Ação para a tarefa") },
            text = { Text("Selecione o que deseja fazer com esta tarefa.") },
            confirmButton = {
                TextButton(onClick = {
                    tarefaDialogData = selectedTarefa!!.copy()
                    showTarefaDialog = true
                    showActionDialog = false
                }) {
                    Text("Editar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDeleteTarefa(selectedTarefa!!)
                    showActionDialog = false
                }) {
                    Text("Excluir")
                }
            }
        )
    }

    // ---------- DIÁLOGO DE TAREFA ----------
    if (showTarefaDialog && tarefaDialogData != null) {
        TarefaDialog(
            tarefa = tarefaDialogData,
            userId = userId,
            onDismiss = { showTarefaDialog = false },
            onSave = { updatedTarefa ->
                if (updatedTarefa.id == 0L)
                    onSaveTarefa(updatedTarefa)     // CREATE
                else
                    onEditTarefa(updatedTarefa)     // UPDATE

                showTarefaDialog = false
            }
        )
    }

    // ---------- DIÁLOGO DE META ----------
    if (showMetaDialog && metaDialogData != null) {
        MetaDialog(
            meta = metaDialogData,
            userId = userId,
            onDismiss = { showMetaDialog = false },
            onSave = { updatedMeta ->
                if (updatedMeta.id == 0L)
                    onSaveMeta(updatedMeta)       // CREATE
                else
                    onEditMeta(updatedMeta)       // UPDATE

                showMetaDialog = false
            }
        )
    }
}