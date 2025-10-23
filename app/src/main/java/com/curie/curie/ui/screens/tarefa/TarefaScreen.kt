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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModel
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModelFactory
import com.curie.curie.ui.theme.CurieTheme

// --- ViewModel real ---
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaScreen(
    userId: Long,
    tokenStorage: TokenStorage
) {
    // FACTORIES DOS VIEWMODELS
    val tarefaFactory = remember { TarefaViewModelFactory(tokenStorage) }
    val metaFactory = remember { MetaViewModelFactory(tokenStorage) }

    // INSTÂNCIAS DOS VIEWMODELS
    val tarefaViewModel: TarefaViewModel = viewModel(factory = tarefaFactory)
    val metaViewModel: MetaViewModel = viewModel(factory = metaFactory)

    // ESTADOS DE TAREFAS
    val tarefas by tarefaViewModel.tarefas.collectAsStateWithLifecycle()
    val tarefasLoading by tarefaViewModel.loading.collectAsStateWithLifecycle()
    val tarefasError by tarefaViewModel.error.collectAsStateWithLifecycle()

    // ESTADOS DE METAS
    val metas by metaViewModel.metas.collectAsStateWithLifecycle()
    val metasLoading by metaViewModel.loading.collectAsStateWithLifecycle()
    val metasError by metaViewModel.error.collectAsStateWithLifecycle()

    // BUSCA OS DADOS QUANDO A TELA É ABERTA
    LaunchedEffect(Unit) {
        tarefaViewModel.getByUsuario(userId)
        metaViewModel.getByUsuario(userId)
    }

    // EXIBE ESTADO DE LOADING GERAL
    when {
        tarefasLoading || metasLoading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        tarefasError != null || metasError != null -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tarefasError ?: metasError ?: "Erro desconhecido",
                color = MaterialTheme.colorScheme.error
            )
        }

        else -> TarefaScreenContent(
            tarefas = tarefas,
            metas = metas,
            userId = userId,
            onEditTarefa = { tarefa ->
                tarefaViewModel.update(tarefa.id, tarefa)
            },
            onDeleteTarefa = { tarefa ->
                tarefaViewModel.delete(tarefa.id)
            },
            onSaveTarefa = { tarefa ->
                tarefaViewModel.save(tarefa)
            },
            onEditMeta = { meta ->
                metaViewModel.update(meta.id, meta)
            },
            onDeleteMeta = { meta ->
                metaViewModel.delete(meta.id)
            },
            onSaveMeta = { meta ->
                metaViewModel.save(meta)
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
        Tarefa(1, 1, "Estudar Compose", "2025-10-20", Prioridade.alta, Status.pendente),
        Tarefa(2, 1, "Finalizar projeto", "2025-10-25", Prioridade.media, Status.concluida),
        Tarefa(3, 1, "Revisar código", "2025-10-18", Prioridade.baixa, Status.pendente)
    )

    val fakeMetas = listOf(
        Meta(1, 1, "Meta de Estudo", "Aprimorar habilidades em Kotlin", inicio = "2025-10-15", fim = "2025-10-15", prioridade = Prioridade.alta, status = Status.pendente),
        Meta(2, 1, "Meta de Saúde", "Praticar exercícios 3x por semana", inicio = "2025-10-15", fim = "2025-10-15", prioridade = Prioridade.alta, status = Status.pendente)
    )

    CurieTheme {
        TarefaScreenContent(
            tarefas = fakeTarefas,
            metas = fakeMetas,
            userId = 1L
        )
    }
}
