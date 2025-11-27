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
    var selectedTarefa by remember { mutableStateOf<Tarefa?>(null) }
    var showTarefaActionDialog by remember { mutableStateOf(false) }
    var tarefaDialogTarefa by remember { mutableStateOf<Tarefa?>(null) }

    var selectedTab by remember { mutableStateOf("Tarefas") }

    var showCreateMetaDialog by remember { mutableStateOf(false) }
    var editMeta by remember { mutableStateOf<Meta?>(null) }
    var showEditMetaDialog by remember { mutableStateOf(false) }

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
                onClick = {
                    if (selectedTab == "Tarefas") {
                        tarefaDialogTarefa = Tarefa(
                            id = 0L,
                            userId = userId,
                            nome = "",
                            prazo = "",
                            prioridade = Prioridade.baixa,
                            status = Status.pendente
                        )
                    } else {
                        editMeta = Meta(
                            id = 0L,
                            userId = userId,
                            objetivo = "",
                            descricao = "",
                            inicio = "",
                            fim = "",
                            prioridade = Prioridade.baixa,
                            status = Status.pendente
                        )
                        showCreateMetaDialog = true
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

                "Tarefas" -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tarefas) { tarefa ->
                        TarefaItem(
                            tarefa = tarefa,
                            onClick = {
                                selectedTarefa = tarefa
                                showTarefaActionDialog = true
                            },
                            onEdit = { tarefaDialogTarefa = it },
                            onDelete = onDeleteTarefa
                        )
                    }
                }

                "Metas" -> MetaContent(
                    metas = metas,
                    userId = userId,
                    onEdit = {
                        editMeta = it
                        showEditMetaDialog = true
                    },
                    onDelete = onDeleteMeta,
                    onSave = onSaveMeta
                )
            }
        }
    }

    // --- AÇÃO AO CLICAR EM UMA TAREFA ---
    if (showTarefaActionDialog && selectedTarefa != null) {
        AlertDialog(
            onDismissRequest = { showTarefaActionDialog = false },
            title = { Text("O que deseja fazer?") },
            text = { Text("Escolha uma ação para a tarefa selecionada.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTarefaActionDialog = false
                        tarefaDialogTarefa = selectedTarefa
                        selectedTarefa = null
                    }
                ) { Text("Editar") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedTarefa?.let(onDeleteTarefa)
                        showTarefaActionDialog = false
                        selectedTarefa = null
                    }
                ) { Text("Excluir") }
            }
        )
    }

    // --- CRIAÇÃO / EDIÇÃO DE TAREFA ---
    if (tarefaDialogTarefa != null) {
        TarefaDialog(
            tarefa = tarefaDialogTarefa,
            userId = userId,
            onDismiss = { tarefaDialogTarefa = null },
            onSave = {
                onSaveTarefa(it)
                tarefaDialogTarefa = null
            }
        )
    }

    // --- DIALOG PARA CRIAR META ---
    if (showCreateMetaDialog && editMeta != null) {
        MetaDialog(
            meta = editMeta,
            userId = userId,
            onDismiss = { showCreateMetaDialog = false },
            onSave = {
                onSaveMeta(it)
                showCreateMetaDialog = false
            }
        )
    }

    // --- DIALOG PARA EDITAR META ---
    if (showEditMetaDialog && editMeta != null) {
        MetaDialog(
            meta = editMeta,
            userId = userId,
            onDismiss = { showEditMetaDialog = false },
            onSave = {
                onSaveMeta(it)
                showEditMetaDialog = false
            }
        )
    }
}
