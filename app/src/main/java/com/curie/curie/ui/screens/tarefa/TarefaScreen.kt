package com.curie.curie.ui.screens.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.theme.CurieTheme

// --- ViewModel real ---
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreen(
    userId: Long,
    tokenStorage: TokenStorage = TokenStorage(LocalContext.current),
    viewModel: TarefaViewModel = viewModel(factory = TarefaViewModelFactory(tokenStorage))
) {
    val tarefas by viewModel.tarefas.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getByUsuario(userId)
    }

    when {
        loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error ?: "Erro desconhecido", color = MaterialTheme.colorScheme.error)
        }

        else -> TarefaScreenContent(
            tarefas = tarefas,
            onEdit = { tarefa ->
                // Aqui a edição vai disparar o dialog dentro do TarefaScreenContent
            },
            onDelete = { tarefa -> viewModel.delete(tarefa.id) },
            onSave = { tarefa ->
                if (tarefa.id == 0L) viewModel.save(tarefa)
                else viewModel.update(tarefa.id, tarefa)
            }
        )
    }
}

// --- Preview com dados fictícios ---
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TarefaScreenPreview() {
    val fakeTarefas = listOf(
        Tarefa(1, "Estudar Compose", "2025-10-20", Prioridade.alta, Status.pendente),
        Tarefa(2, "Finalizar projeto", "2025-10-25", Prioridade.media, Status.concluida),
        Tarefa(3, "Revisar código", "2025-10-18", Prioridade.baixa, Status.pendente)
    )

    CurieTheme {
        TarefaScreenContent(
            tarefas = fakeTarefas,
            onSave = { /* no preview não salva */ },
            onDelete = { /* no preview não deleta */ }
        )
    }
}