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
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.White, darkIcons = true)

    // --- Estados ---
    var selectedTarefa by remember { mutableStateOf<Tarefa?>(null) }
    var showTarefaActionDialog by remember { mutableStateOf(false) }
    var tarefaDialogTarefa by remember { mutableStateOf<Tarefa?>(null) } // null = nenhum diálogo aberto
    var showCreateMetaDialog by remember { mutableStateOf(false) }
    var editMeta by remember { mutableStateOf<Meta?>(null) }
    var showEditMetaDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Tarefas") }

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
                        // Criar nova tarefa com id 0L
                        tarefaDialogTarefa = Tarefa(
                            id = 0L,  // id 0 significa nova tarefa
                            userId = userId,
                            nome = "",
                            prazo = "",
                            prioridade = Prioridade.baixa,
                            status = Status.pendente
                        )
                    } else {
                        showCreateMetaDialog = true
                    }
                },
                shape = CircleShape,
                containerColor = BlueNavy,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar item",
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
                                onDelete = { onDeleteTarefa(it) }
                            )
                        }
                    }
                    "Metas" -> MetaContent(
                        metas = metas,
                        userId = userId,
                        onEdit = onEditMeta,
                        onDelete = onDeleteMeta,
                        onSave = onSaveMeta
                    )
                }
            }
        }
    }

    // --- DIALOG DE AÇÃO DA TAREFA ---
    if (showTarefaActionDialog && selectedTarefa != null) {
        AlertDialog(
            onDismissRequest = { showTarefaActionDialog = false },
            title = { Text("O que deseja fazer?") },
            text = { Text("Escolha uma ação para a tarefa selecionada.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTarefaActionDialog = false
                        // Mantemos o id original da tarefa para editar
                        tarefaDialogTarefa = selectedTarefa
                        selectedTarefa = null
                    }
                ) { Text("Editar") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedTarefa?.let { onDeleteTarefa(it) }
                        showTarefaActionDialog = false
                        selectedTarefa = null
                    }
                ) { Text("Excluir") }
            }
        )
    }

    // --- DIALOG DE CRIAÇÃO / EDIÇÃO DE TAREFA ---
    if (tarefaDialogTarefa != null) {
        TarefaDialog(
            tarefa = tarefaDialogTarefa,
            userId = userId,
            onDismiss = { tarefaDialogTarefa = null },
            onSave = { tarefa ->
                onSaveTarefa(tarefa)
                tarefaDialogTarefa = null
            }
        )
    }

    // --- DIALOGS DE META ---
    if (showCreateMetaDialog) {
        MetaDialog(
            meta = null,
            userId = userId,
            onDismiss = { showCreateMetaDialog = false },
            onSave = { meta ->
                onSaveMeta(meta)
                showCreateMetaDialog = false
            }
        )
    }

    if (showEditMetaDialog && editMeta != null) {
        MetaDialog(
            meta = editMeta,
            userId = userId,
            onDismiss = { showEditMetaDialog = false },
            onSave = { meta ->
                onSaveMeta(meta)
                showEditMetaDialog = false
            }
        )
    }
}
