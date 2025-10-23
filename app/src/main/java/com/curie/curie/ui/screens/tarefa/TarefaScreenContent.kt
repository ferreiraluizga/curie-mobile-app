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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.Meta
import com.curie.curie.ui.components.tarefa.TabsSection
import com.curie.curie.ui.components.tarefa.TarefaDialog
import com.curie.curie.ui.components.tarefa.TarefaItem
import com.curie.curie.ui.components.tarefa.TarefaTopBar
import com.curie.curie.ui.components.tarefa.WeekCalendar
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
    systemUiController.setSystemBarsColor(
        color = Color.White,
        darkIcons = true
    )

    // Controle de diálogos
    var showCreateTarefaDialog by remember { mutableStateOf(false) }
    var showEditTarefaDialog by remember { mutableStateOf(false) }
    var editTarefa by remember { mutableStateOf<Tarefa?>(null) }

    var showCreateMetaDialog by remember { mutableStateOf(false) }
    var showEditMetaDialog by remember { mutableStateOf(false) }
    var editMeta by remember { mutableStateOf<Meta?>(null) }

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
                    if (selectedTab == "Tarefas") showCreateTarefaDialog = true
                    else showCreateMetaDialog = true
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
                                onEdit = {
                                    editTarefa = it
                                    showEditTarefaDialog = true
                                },
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

    // --- DIALOGS DE TAREFA ---
    if (showCreateTarefaDialog) {
        TarefaDialog(
            tarefa = null,
            userId = userId,
            onDismiss = { showCreateTarefaDialog = false },
            onSave = { tarefa ->
                onSaveTarefa(tarefa)
                showCreateTarefaDialog = false
            }
        )
    }

    if (showEditTarefaDialog && editTarefa != null) {
        TarefaDialog(
            tarefa = editTarefa,
            userId = userId,
            onDismiss = { showEditTarefaDialog = false },
            onSave = { tarefa ->
                onSaveTarefa(tarefa)
                showEditTarefaDialog = false
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
