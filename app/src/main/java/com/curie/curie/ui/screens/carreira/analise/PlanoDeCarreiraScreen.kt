// PlanoDeCarreiraScreen.kt (Crie este novo arquivo)

package com.curie.curie.ui.screens.carreira.analise

import CarreiraViewModelFactory
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curie.curie.ai.GeminiClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Perfil
import com.curie.curie.ui.theme.BlueNavy // Supondo sua cor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanoDeCarreiraScreen(
    tokenStorage: TokenStorage,
    geminiClient: GeminiClient, // O GeminiClient é necessário para criar o ViewModel
    onPlanoSalvo: () -> Unit,
    onBack: () -> Unit
) {
    // --- FACTORY e VIEWMODEL ---
    // 1. Cria a Factory, passando as dependências
    val carreiraViewModelFactory = remember {
        CarreiraViewModelFactory(tokenStorage, geminiClient)
    }

    // 2. Obtém o ViewModel usando a Factory
    val viewModel: CarreiraViewModel = viewModel(factory = carreiraViewModelFactory)

    // 1. Cria a Factory, passando as dependências
    val perfilViewModelFactory = remember {
        PerfilViewModelFactory(tokenStorage)
    }

    // 2. Obtém o ViewModel usando a Factory
    val perfilViewModel: PerfilViewModel = viewModel(factory = perfilViewModelFactory)

    // --- ESTADOS ---
    val isLoading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val planoTexto by viewModel.planoGeradoEmTexto.collectAsStateWithLifecycle()
    val carreiraSalva by viewModel.carreira.collectAsStateWithLifecycle()
    val profissao by viewModel.profissaoGerada.collectAsStateWithLifecycle()
    val graduacao by viewModel.graduacaoGerada.collectAsStateWithLifecycle()
    val pos by viewModel.posGerada.collectAsStateWithLifecycle()
    val resumo by viewModel.resumoGerado.collectAsStateWithLifecycle()
    val perfil by viewModel.perfilGerado.collectAsStateWithLifecycle()
    val areaEscolhida by viewModel.areaGerada.collectAsStateWithLifecycle()
    val planoCarreira by viewModel.planoGerado.collectAsStateWithLifecycle()


    // 3. EFEITO: Dispara a geração quando a tela é carregada
    LaunchedEffect(Unit) {
        // A geração do plano só deve ocorrer uma vez.
        // Se o planoTexto for null, significa que é a primeira vez.
        if (planoTexto == null) {
            viewModel.loadAllProfissoes() // Garantir que os dados para lookup estejam prontos
            viewModel.loadAllForAreas()
            viewModel.gerarEExibirPlano()
        }
    }

    // 4. EFEITO: Navega após o salvamento bem-sucedido
    LaunchedEffect(carreiraSalva) {
        if (carreiraSalva != null) {
            viewModel.clearPlanoGerado() // Limpa o estado para o próximo uso
            onPlanoSalvo()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plano de Carreira IA", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueNavy)
            )
        },
        bottomBar = {
            if (planoTexto != null && !isLoading) {
                // Botão de salvar visível apenas se o plano tiver sido gerado
                BottomAppBar(
                    actions = { Spacer(Modifier.weight(1f)) },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = {
                                viewModel.salvarPlanoGerado()
                                perfilViewModel.save(
                                    Perfil(
                                        id = null,
                                        userId = tokenStorage.getUserId(),
                                        descricao = "Análise de Perfil",
                                        comportamentoId = tokenStorage.getComportamentoId(),
                                        temperamentoId = tokenStorage.getTemperamentoId(),
                                        forcaId = tokenStorage.getForcaEducacionalId(),
                                        fraquezaId = tokenStorage.getFraquezaEducacionalId()
                                    )
                                )
                                      },
                            icon = { Icon(Icons.Default.Done, contentDescription = "Salvar") },
                            text = { Text("Salvar e Concluir") },
                            containerColor = BlueNavy,
                            contentColor = Color.White
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    // ESTADO: CARREGANDO
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = BlueNavy)
                        Spacer(Modifier.height(16.dp))
                        Text("Gerando análise de perfil e plano...", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                error != null -> {
                    // ESTADO: ERRO
                    // Você pode usar o AlertDialog aqui, ou apenas um texto simples.
                    Text("Erro: ${error!!}", color = MaterialTheme.colorScheme.error)
                    // Limpar o erro após exibir
                    LaunchedEffect(error) { viewModel.clearError() }
                }

                planoTexto != null -> {
                    // ESTADO: PLANO PRONTO
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 80.dp) // espaço pro botão
                    ) {

                        // 🔹 Título geral
                        Text(
                            text = "Resultado da Análise",
                            style = MaterialTheme.typography.headlineSmall,
                            color = BlueNavy
                        )
                        Spacer(Modifier.height(16.dp))

                        // =============================================================
                        // 🔹 PROFISSÃO
                        // =============================================================
                        ResultadoCard(
                            titulo = "Profissão Ideal",
                            conteudo = profissao ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 GRADUAÇÃO
                        // =============================================================
                        ResultadoCard(
                            titulo = "Graduação Recomendada",
                            conteudo = graduacao ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 PÓS-GRADUAÇÃO
                        // =============================================================
                        ResultadoCard(
                            titulo = "Pós-Graduação Indicada",
                            conteudo = pos ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 RESUMO FINAL / DESCRIÇÃO
                        // =============================================================
                        ResultadoCard(
                            titulo = "Resumo da Análise",
                            conteudo = resumo ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 PERFIL COMPORTAMENTAL
                        // =============================================================
                        ResultadoCard(
                            titulo = "Perfil Identificado",
                            conteudo = perfil ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 ÁREA DE CARREIRA
                        // =============================================================
                        ResultadoCard(
                            titulo = "Área de Carreira",
                            conteudo = areaEscolhida ?: "Carregando..."
                        )

                        // =============================================================
                        // 🔹 PLANO DE CARREIRA COMPLETO
                        // =============================================================
                        ResultadoCard(
                            titulo = "Plano de Carreira",
                            conteudo = planoCarreira ?: "Carregando..."
                        )

                        Spacer(Modifier.height(120.dp)) // espaço extra para scrolling
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoCard(
    titulo: String,
    conteudo: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = BlueNavy
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = conteudo,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}
