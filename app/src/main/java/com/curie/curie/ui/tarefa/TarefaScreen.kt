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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreen(
    userId: Long,
    viewModel: TarefaViewModel = viewModel()
) {
    val tarefas by viewModel.tarefas.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    // Carrega as tarefas quando entrar na tela
    LaunchedEffect(userId) {
        viewModel.getByUsuario(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Minhas Tarefas") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                error != null -> {
                    Text(
                        text = error ?: "Erro desconhecido",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tarefas) { tarefa ->
                            TarefaItem(tarefa)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TarefaItem(tarefa: Tarefa) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tarefa.nome, style = MaterialTheme.typography.titleMedium)
            Text(text = "Prazo: ${tarefa.prazo}")
            Text(text = "Prioridade: ${tarefa.prioridade}")
            Text(text = "Status: ${tarefa.status}")
        }
    }
}
