package com.curie.curie.ui.screens.start

import CarreiraViewModelFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.ai.GeminiClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.carreira.analise.CarreiraViewModel
import com.curie.curie.ui.screens.tarefa.TarefaViewModel
import com.curie.curie.ui.screens.tarefa.TarefaViewModelFactory
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModel
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    userId: Long,
    tokenStorage: TokenStorage,
    geminiClient: GeminiClient,
    onLogout: () -> Unit
) {
    //-----------------------------------------------------------
    // FACTORIES DOS VIEWMODELS
    //-----------------------------------------------------------
    val tarefaFactory = remember { TarefaViewModelFactory(tokenStorage) }
    val metaFactory = remember { MetaViewModelFactory(tokenStorage) }
    val carreiraFactory = remember { CarreiraViewModelFactory(tokenStorage, geminiClient) }

    //-----------------------------------------------------------
    // INSTÂNCIAS DOS VIEWMODELS
    //-----------------------------------------------------------
    val tarefaViewModel: TarefaViewModel = viewModel(factory = tarefaFactory)
    val metaViewModel: MetaViewModel = viewModel(factory = metaFactory)
    val carreiraViewModel: CarreiraViewModel = viewModel(factory = carreiraFactory)

    //-----------------------------------------------------------
    // ESTADOS (flow → compose)
    //-----------------------------------------------------------
    val tarefas by tarefaViewModel.tarefas.collectAsStateWithLifecycle()
    val tarefasLoading by tarefaViewModel.loading.collectAsStateWithLifecycle()

    val metas by metaViewModel.metas.collectAsStateWithLifecycle()
    val metasLoading by metaViewModel.loading.collectAsStateWithLifecycle()

    val carreira by carreiraViewModel.carreira.collectAsStateWithLifecycle()
    val carreiraLoading by carreiraViewModel.loading.collectAsStateWithLifecycle()

    //-----------------------------------------------------------
    // BUSCAR DADOS
    //-----------------------------------------------------------
    LaunchedEffect(Unit) {
        tarefaViewModel.getByUsuario(userId)
        metaViewModel.getByUsuario(userId)
        carreiraViewModel.getCarreiraByUsuario(userId)
    }

    //-----------------------------------------------------------
    // LOADING GERAL
    //-----------------------------------------------------------
    if (tarefasLoading || metasLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    //-----------------------------------------------------------
    // ERRO (opcional, caso você tenha erro nos VMs)
    //-----------------------------------------------------------
    // aqui você pode colocar um Box de erro se quiser

    //-----------------------------------------------------------
    // CONTEÚDO FINAL
    //-----------------------------------------------------------
}

