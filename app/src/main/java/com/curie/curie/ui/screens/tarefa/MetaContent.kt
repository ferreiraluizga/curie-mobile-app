package com.curie.curie.ui.screens.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.data.model.Meta
import com.curie.curie.ui.components.tarefa.meta.MetaDialog
import com.curie.curie.ui.components.tarefa.meta.MetaItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetaContent(
    metas: List<Meta>,
    userId: Long,
    onEdit: (Meta) -> Unit = {},
    onDelete: (Meta) -> Unit = {},
    onSave: (Meta) -> Unit = {}
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var editMeta by remember { mutableStateOf<Meta?>(null) }
    var showActionDialog by remember { mutableStateOf(false) }
    var selectedMeta by remember { mutableStateOf<Meta?>(null) }

    if (metas.isEmpty()) {
        Text(
            text = "Nenhuma meta cadastrada.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(metas) { meta ->
                MetaItem(
                    meta = meta,
                    onEdit = {
                        selectedMeta = it
                        showActionDialog = true
                    },
                    onDelete = { onDelete(it) }
                )
            }
        }
    }

    if (showActionDialog && selectedMeta != null) {
        AlertDialog(
            onDismissRequest = { showActionDialog = false },
            title = { Text("Ação para a meta") },
            text = { Text("Selecione o que deseja fazer com esta meta.") },
            confirmButton = {
                TextButton(onClick = {
                    editMeta = selectedMeta!!.copy()
                    showEditDialog = true
                    showActionDialog = false
                }) {
                    Text("Editar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDelete(selectedMeta!!)
                    showActionDialog = false
                }) {
                    Text("Excluir")
                }
            }
        )
    }

    if (showEditDialog && editMeta != null) {
        MetaDialog(
            meta = editMeta,
            userId = userId,
            onDismiss = { showEditDialog = false },
            onSave = { meta ->
                onSave(meta)
                showEditDialog = false
            }
        )
    }
}
