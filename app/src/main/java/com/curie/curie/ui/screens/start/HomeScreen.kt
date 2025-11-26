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
import com.curie.curie.ui.screens.carreira.analise.PerfilViewModel
import com.curie.curie.ui.screens.carreira.analise.PerfilViewModelFactory
import com.curie.curie.ui.screens.perfil.UserViewModel
import com.curie.curie.ui.screens.perfil.UserViewModelFactory
import com.curie.curie.ui.screens.tarefa.TarefaViewModel
import com.curie.curie.ui.screens.tarefa.TarefaViewModelFactory
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModel
import com.curie.curie.ui.screens.tarefa.meta.MetaViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    tokenStorage: TokenStorage,
    geminiClient: GeminiClient
) {
    //-----------------------------------------------------------
    // FACTORIES DOS VIEWMODELS
    //-----------------------------------------------------------
    val tarefaFactory = remember { TarefaViewModelFactory(tokenStorage) }
    val metaFactory = remember { MetaViewModelFactory(tokenStorage) }
    val carreiraFactory = remember { CarreiraViewModelFactory(tokenStorage, geminiClient) }
    val perfilFactory = remember { PerfilViewModelFactory(tokenStorage) }

    //-----------------------------------------------------------
    // INSTÂNCIAS DOS VIEWMODELS
    //-----------------------------------------------------------
    val tarefaViewModel: TarefaViewModel = viewModel(factory = tarefaFactory)
    val metaViewModel: MetaViewModel = viewModel(factory = metaFactory)
    val carreiraViewModel: CarreiraViewModel = viewModel(factory = carreiraFactory)
    val perfilViewModel: PerfilViewModel = viewModel(factory = perfilFactory)

    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(tokenStorage)
    )

    //-----------------------------------------------------------
    // ESTADOS (flow → compose)
    //-----------------------------------------------------------
    val tarefas by tarefaViewModel.tarefas.collectAsStateWithLifecycle()
    val tarefasLoading by tarefaViewModel.loading.collectAsStateWithLifecycle()

    val metas by metaViewModel.metas.collectAsStateWithLifecycle()
    val metasLoading by metaViewModel.loading.collectAsStateWithLifecycle()

    val carreira by carreiraViewModel.carreira.collectAsStateWithLifecycle()
    val carreiraLoading by carreiraViewModel.loading.collectAsStateWithLifecycle()

    val perfil by perfilViewModel.perfil.collectAsStateWithLifecycle()
    val perfilLoading by perfilViewModel.loading.collectAsStateWithLifecycle()

    val profissao by carreiraViewModel.profissao.collectAsStateWithLifecycle()
    val graduacao by carreiraViewModel.graduacao.collectAsStateWithLifecycle()
    val posGraduacao by carreiraViewModel.posGraduacao.collectAsStateWithLifecycle()

    val user by userViewModel.user.collectAsStateWithLifecycle()

    //-----------------------------------------------------------
    // BUSCAR DADOS
    //-----------------------------------------------------------
    val userId = tokenStorage.getUserId()

    // 1. Efeito para buscar todos os dados de usuário ao carregar a tela
    // Depende apenas do userId (que é estático após o login)
    LaunchedEffect(userId) {
        if (userId != null) {
            tarefaViewModel.getByUsuario(userId)
            metaViewModel.getByUsuario(userId)
            carreiraViewModel.getCarreiraByUsuario(userId)
            perfilViewModel.getMaisRecente(userId)
            userViewModel.getById(userId)
        }
    }

    // 2. Efeito para buscar detalhes do plano de carreira (Profissão/Graduação/Pós)
    // Depende do objeto 'carreira' ter sido carregado (ou de ter mudado)
    LaunchedEffect(carreira) {
        if (carreira != null) {
            // Só executa se a carreira foi encontrada (não é nula)
            carreira!!.profissaoId?.let { carreiraViewModel.getProfissaoById(it) }
            carreira!!.graduacaoId?.let { carreiraViewModel.getGraduacaoById(it) }
            carreira!!.posGraduacaoId?.let { carreiraViewModel.getPosGraduacaoById(it) }
        }
        // Se 'carreira' for null, significa que o usuário não tem plano salvo.
        // Neste caso, 'profissao', 'graduacao' e 'posGraduacao' permanecerão null,
        // e a UI exibirá a mensagem de que o plano não foi realizado.
    }

    //-----------------------------------------------------------
    // LOADING GERAL
    //-----------------------------------------------------------
    // Simplificado para checar apenas o loading dos dados principais.
    if (tarefasLoading || metasLoading || carreiraLoading || perfilLoading || user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val areaEducacionalIds = mapOf(
        "Ciências Humanas" to 1L,
        "Matemática e suas Tecnologias" to 2L,
        "Ciências da Natureza" to 3L,
        "Linguagens e Códigos" to 4L
    )

    // Função utilitária para buscar nome pelo ID
    fun getAreaNomePorId(id: Long?): String {
        return areaEducacionalIds.entries.find { it.value == id }?.key ?: ""
    }

    val descricaoPerfil = perfil?.descricao ?: ""
    val forcaEducacional = getAreaNomePorId(perfil?.forcaId)
    val fraquezaEducacional = getAreaNomePorId(perfil?.fraquezaId)


    //-----------------------------------------------------------
    // CONTEÚDO FINAL
    //-----------------------------------------------------------
    HomeContent(
        userName = user?.nome,
        tarefas = tarefas,
        metas = metas,
        descricaoPerfil = descricaoPerfil,
        forcaEducacional = forcaEducacional,
        fraquezaEducacional = fraquezaEducacional,
        profissao = profissao,
        posGraduacao = posGraduacao,
        graduacao = graduacao
    )
}