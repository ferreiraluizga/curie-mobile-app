package com.curie.curie.ui.screens.carreira.analise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.ai.AiResponseParser
import com.curie.curie.ai.GeminiClient
import com.curie.curie.data.api.CarreiraApi
import com.curie.curie.data.api.ComportamentoApi
import com.curie.curie.data.api.GraduacaoApi
import com.curie.curie.data.api.PosGraduacaoApi
import com.curie.curie.data.api.ProfissaoApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TemperamentoApi
import com.curie.curie.data.api.TipoComportamentoApi
import com.curie.curie.data.api.TipoTemperamentoApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Carreira
import com.curie.curie.data.model.Graduacao
import com.curie.curie.data.model.PosGraduacao
import com.curie.curie.data.model.Profissao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarreiraViewModel(
    private val tokenStorage: TokenStorage,
    private val geminiClient: GeminiClient
) : ViewModel() {

    // --- Instâncias de API ---

    private val carreiraApi = RetrofitClient.createService(
        CarreiraApi::class.java
    ) { tokenStorage.getToken() }

    private val graduacaoApi = RetrofitClient.createService(
        GraduacaoApi::class.java
    ) { tokenStorage.getToken() }

    private val posGraduacaoApi = RetrofitClient.createService(
        PosGraduacaoApi::class.java
    ) { tokenStorage.getToken() }

    private val profissaoApi = RetrofitClient.createService(
        ProfissaoApi::class.java
    ) { tokenStorage.getToken() }

    private val comportamentoApi = RetrofitClient.createService(
        ComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val temperamentoApi = RetrofitClient.createService(
        TemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoComportamentoApi = RetrofitClient.createService(
        TipoComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoTemperamentoApi = RetrofitClient.createService(
        TipoTemperamentoApi::class.java
    ) { tokenStorage.getToken() }

    // --- StateFlows para o CRUD Padrão ---

    private val _carreiras = MutableStateFlow<List<Carreira>>(emptyList())
    val carreiras: StateFlow<List<Carreira>> = _carreiras

    private val _carreira = MutableStateFlow<Carreira?>(null)
    val carreira: StateFlow<Carreira?> = _carreira

    private val _graduacoes = MutableStateFlow<List<Graduacao>>(emptyList())
    val graduacoes: StateFlow<List<Graduacao>> = _graduacoes

    private val _posGraduacoes = MutableStateFlow<List<PosGraduacao>>(emptyList())
    val posGraduacoes: StateFlow<List<PosGraduacao>> = _posGraduacoes

    private val _profissoes = MutableStateFlow<List<Profissao>>(emptyList())
    val profissoes: StateFlow<List<Profissao>> = _profissoes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- StateFlows para Geração de Plano (convertidos de LiveData) ---

    // Armazena o TEXTO da IA para exibir na UI
    private val _planoGeradoEmTexto = MutableStateFlow<String?>(null)
    val planoGeradoEmTexto: StateFlow<String?> = _planoGeradoEmTexto

    // Armazena o objeto Carreira completo, pronto para ser salvo
    private var carreiraProntaParaSalvar: Carreira? = null


    // ================================================================
    // 🔹 PASSO 1: Gerar Plano (Chama IA e prepara o objeto)
    // ================================================================

    // ================================================================
// 🔹 PASSO 1: Gerar Plano (Chama IA e prepara o objeto)
// ================================================================

    fun gerarEExibirPlano() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            _planoGeradoEmTexto.value = null
            carreiraProntaParaSalvar = null

            try {
                // 1. BUSCAR TODOS OS DADOS DE INPUT
                val comportamentoId = tokenStorage.getComportamentoId()
                val temperamentoId = tokenStorage.getTemperamentoId()

                val comportamento = comportamentoId?.let { comportamentoApi.getById(it).execute().body() }
                val tipoComportamento = comportamento?.let { tipoComportamentoApi.getById(it.tipoComportamentoId).execute().body() }
                val temperamento = temperamentoId?.let { temperamentoApi.getById(it).execute().body() }
                val tipoTemperamento = temperamento?.let { tipoTemperamentoApi.getById(it.tipoTemperamentoId).execute().body() }

                val areas = tokenStorage.getAreasRecomendadas()
                val graduacoes = tokenStorage.getGraduacoes()
                val pos = tokenStorage.getPosGraduacoes()
                val profs = tokenStorage.getProfissoes()

                val nomeTemperamento = tipoTemperamento?.nome ?: "Não informado"
                val nomeComportamento = tipoComportamento?.nome ?: "Não informado"

                // 2. CHAMAR A IA (GEMINI)
                val respostaCompletaIA = geminiClient.gerarPlanoDeCarreira(
                    areasRecomendadas = areas,
                    graduacoes = graduacoes,
                    posGraduacoes = pos,
                    profissoes = profs,
                    temperamento = nomeTemperamento,
                    comportamento = nomeComportamento
                )

                // 3. ATUALIZAR A UI IMEDIATAMENTE (mostra o texto)
                _planoGeradoEmTexto.value = respostaCompletaIA

                // 4. PREPARAR O OBJETO 'Carreira' PARA SALVAR DEPOIS

                // 4a. Extrai os nomes únicos sugeridos (CORRIGIDO: Chamando as funções que retornam String)
                val profissaoNome = AiResponseParser.extrairPrimeiraProfissao(respostaCompletaIA)
                val graduacaoNome = AiResponseParser.extrairPrimeiraGraduacao(respostaCompletaIA)
                val posNome = AiResponseParser.extrairPrimeiraPosGraduacao(respostaCompletaIA)

                // NOVO: Extrai o resumo para usar como descrição
                val resumoDescricao = AiResponseParser.extrairResumoFinal(respostaCompletaIA)


                // 4b. REALIZA O LOOKUP DO ID
                // OBSERVAÇÃO: Depende que os métodos loadAll... tenham sido chamados antes!
                val profissaoId = _profissoes.value.find { it.nome == profissaoNome }?.id
                Log.d("CarreiraViewModel", "Profissão sugerida: $profissaoNome -> ID: $profissaoId")

                val graduacaoId = _graduacoes.value.find { it.nome == graduacaoNome }?.id
                Log.d("CarreiraViewModel", "Graduação sugerida: $graduacaoNome -> ID: $graduacaoId")

                val posGraduacaoId = _posGraduacoes.value.find { it.nome == posNome }?.id
                Log.d("CarreiraViewModel", "Pós sugerida: $posNome -> ID: $posGraduacaoId")


                // 4c. Crie o objeto Carreira com os IDs
                carreiraProntaParaSalvar = Carreira(
                    id = null,
                    userId = tokenStorage.getUserId(),
                    descricao = resumoDescricao,
                    profissaoId = profissaoId,
                    graduacaoId = graduacaoId,
                    posGraduacaoId = posGraduacaoId
                )

            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao GERAR plano", e)
                _error.value = "Falha ao gerar seu plano: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // ================================================================
    // 🔹 PASSO 2: Salvar Plano (Pega o objeto pronto e envia)
    // ================================================================

    fun salvarPlanoGerado() {
        val carreiraParaSalvar = carreiraProntaParaSalvar

        if (carreiraParaSalvar == null) {
            _error.value = "Nenhum plano foi gerado para salvar."
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null

            try {
                Log.d("CarreiraViewModel", "Enviando carreira para salvar: $carreiraParaSalvar")

                // Esta é a ÚNICA chamada de rede neste método
                val response = carreiraApi.save(carreiraParaSalvar).execute()

                if (response.isSuccessful) {
                    val carreiraSalva = response.body()
                    Log.d("CarreiraViewModel", "✅ Carreira salva: $carreiraSalva")

                    // Atualiza o StateFlow principal da carreira
                    _carreira.value = carreiraSalva

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CarreiraViewModel", "❌ Erro ao salvar carreira: ${response.code()} - $errorBody")
                    _error.value = "Erro ao salvar carreira: ${response.code()}"
                }

            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao SALVAR carreira", e)
                _error.value = "Falha ao salvar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // ================================================================
    // 🔹 MÉTODO 'save' ANTIGO (REMOVIDO)
    // ================================================================

    /* // REMOVIDO - Este método é ineficiente.
    // Ele busca vários dados da API que não são usados.
    // Use 'salvarPlanoGerado()' em vez dele.

    fun save(carreira: Carreira) {
        // ... código antigo e problemático ...
    }
    */


    // ================================================================
    // 🔹 OUTROS MÉTODOS CRUD (getById, delete, etc.)
    // ================================================================

    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("CarreiraViewModel", "Buscando carreira com ID: $id")
                val response = carreiraApi.getById(id).execute()
                if (response.isSuccessful) {
                    _carreira.value = response.body()
                    Log.d("CarreiraViewModel", "📄 Carreira encontrada: ${response.body()}")
                } else {
                    Log.e("CarreiraViewModel", "❌ Erro ao buscar carreira: ${response.code()}")
                    _error.value = "Erro ao buscar carreira: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao buscar carreira", e)
                _error.value = "Falha ao buscar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                Log.d("CarreiraViewModel", "Deletando carreira com ID: $id")
                val response = carreiraApi.delete(id).execute()
                if (response.isSuccessful) {
                    Log.d("CarreiraViewModel", "🗑️ Carreira deletada com sucesso")
                    _carreira.value = null
                } else {
                    Log.e("CarreiraViewModel", "❌ Erro ao deletar carreira: ${response.code()}")
                    _error.value = "Erro ao deletar carreira: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("CarreiraViewModel", "💥 Falha ao deletar carreira", e)
                _error.value = "Falha ao deletar carreira: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadAllForAreas() { // Removido o parâmetro areaIds: List<Long>
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                // 🚨 CORREÇÃO: Pega os IDs diretamente do TokenStorage
                val areaIds = tokenStorage.getAreasIds()
                Log.d("CarreiraViewModel", "Carregando graduações/pós para IDs de Área: $areaIds")

                if (areaIds.isEmpty()) {
                    Log.w("CarreiraViewModel", "Nenhum ID de área encontrado no TokenStorage. Pulando o carregamento de graduações/pós.")
                    return@launch
                }

                val g = graduacaoApi.getAll().execute()
                val pg = posGraduacaoApi.getAll().execute()

                if (g.isSuccessful) {
                    val todas = g.body() ?: emptyList()
                    val filtradas = todas.filter { grad ->
                        areaIds.contains(grad.areaCarreiraId)
                    }
                    _graduacoes.value = filtradas
                    tokenStorage.saveGraduacoes(
                        filtradas.map { it.nome }
                    )
                    Log.d("CarreiraViewModel", "Graduações salvas no storage: ${filtradas.size}")
                }

                if (pg.isSuccessful) {
                    val todas = pg.body() ?: emptyList()
                    val filtradas = todas.filter { pos ->
                        areaIds.contains(pos.areaCarreiraId)
                    }
                    _posGraduacoes.value = filtradas
                    tokenStorage.savePosGraduacoes(
                        filtradas.map { it.nome }
                    )
                    Log.d("CarreiraViewModel", "Pós-graduações salvas no storage: ${filtradas.size}")
                }

            } catch (e: Exception) {
                _error.value = "Erro ao carregar dados: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadAllProfissoes() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = profissaoApi.getAll().execute()
                if (response.isSuccessful) {
                    val todas = response.body() ?: emptyList()
                    _profissoes.value = todas
                    tokenStorage.saveProfissoes(
                        todas.map { it.nome }
                    )
                } else {
                    _error.value = "Erro ao listar profissões: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Erro ao carregar profissões: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    // Função utilitária para limpar o erro (opcional, mas bom para StateFlow)
    fun clearError() {
        _error.value = null
    }

    // Função para limpar o plano gerado (caso o usuário volte a tela)
    fun clearPlanoGerado() {
        _planoGeradoEmTexto.value = null
        carreiraProntaParaSalvar = null
    }
}