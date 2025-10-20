package com.curie.curie.ui.screens.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import com.curie.curie.ui.components.tarefa.TarefaDialog
import com.curie.curie.ui.components.tarefa.TarefaItem
import com.curie.curie.ui.components.tarefa.meta.MetaItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetaContent(
    metas: List<Meta>,
    userId: Long,
    onEdit: (Tarefa) -> Unit = {},
    onDelete: (Tarefa) -> Unit = {},
    onSave: (Tarefa) -> Unit = {}
) {
    // Estados locais para edição de metas
    var showEditDialog by remember { mutableStateOf(false) }
    var editMeta by remember { mutableStateOf<Tarefa?>(null) }

    // Lista de metas exibidas
    if (metas.isEmpty()) {
        Text("Nenhuma meta cadastrada.")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(metas) { meta ->
                // Se Meta herda de Tarefa, isso é válido.
                MetaItem(
                    meta = meta,
                    onEdit = {
                        editMeta = it
                        showEditDialog = true
                    },
                    onDelete = { onDelete(it) }
                )
            }
        }
    }

    // Diálogo de edição de meta
    if (showEditDialog && editMeta != null) {
        TarefaDialog(
            tarefa = editMeta,
            userId = userId,
            onDismiss = { showEditDialog = false },
            onSave = { tarefa ->
                onSave(tarefa)
                showEditDialog = false
            }
        )
    }
}
