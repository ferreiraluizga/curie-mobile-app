package com.curie.curie.ui.screens.carreira.comportamento

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.ComportamentoApi
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.api.TipoComportamentoApi
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.data.model.Comportamento
import com.curie.curie.data.model.TipoComportamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComportamentoViewModel(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val comportamentoApi = RetrofitClient.createService(
        ComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    private val tipoComportamentoApi = RetrofitClient.createService(
        TipoComportamentoApi::class.java
    ) { tokenStorage.getToken() }

    // Estados observáveis
    private val _comportamentos = MutableStateFlow<List<Comportamento>>(emptyList())
    val comportamentos: StateFlow<List<Comportamento>> = _comportamentos

    private val _comportamento = MutableStateFlow<Comportamento?>(null)
    val comportamento: StateFlow<Comportamento?> = _comportamento

    private val _resultado = MutableStateFlow<TipoComportamento?>(null)
    val resultado: StateFlow<TipoComportamento?> = _resultado

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 🔹 Buscar comportamento por ID
    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = comportamentoApi.getById(id).execute()
                if (response.isSuccessful) {
                    _comportamento.value = response.body()
                } else {
                    _error.value = "Erro ao buscar comportamento: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao buscar comportamento: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Salvar novo comportamento
    fun save(comportamento: Comportamento) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = comportamentoApi.save(comportamento).execute()
                if (response.isSuccessful) {
                    // Atualiza estado atual
                    _comportamento.value = response.body()
                } else {
                    _error.value = "Erro ao salvar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao salvar: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 Excluir comportamento
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                val response = comportamentoApi.delete(id).execute()
                if (response.isSuccessful) {
                    _comportamentos.value = _comportamentos.value.filterNot { it.id == id }
                } else {
                    _error.value = "Erro ao excluir: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha ao excluir: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun finalizarTeste(resultadoNome: String, onResult: (Long?) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("ComportamentoVM", "Iniciando finalizarTeste para resultado: $resultadoNome")

                val nomeNormalizado = resultadoNome
                    .replace("–", "-")
                    .replace("—", "-")
                    .trim()
                    .split("-")
                    .joinToString("-") { parte ->
                        parte.trim().replaceFirstChar { it.uppercaseChar() }
                    }

                val tiposResponse = tipoComportamentoApi.getAll().execute()
                if (tiposResponse.isSuccessful) {
                    val tipos = tiposResponse.body() ?: emptyList()
                    val tipo = tipos.find { it.nome.equals(nomeNormalizado, ignoreCase = true) }

                    if (tipo != null) {
                        val comportamento = when (tipo.nome.lowercase()) {
                            "proativo" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Liderança, ação, decisão",
                                aprendizagem = "Aprende fazendo e enfrentando desafios",
                                descricaoEstudo = "Crie metas curtas e claras para manter o ritmo. Evite agir sem planejar."
                            )
                            "comunicativo" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Expressão, empatia, entusiasmo",
                                aprendizagem = "Aprende conversando e ensinando",
                                descricaoEstudo = "Use grupos de estudo, vídeos e debates. Cuidado com distrações."
                            )
                            "estável" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Paciência, constância, serenidade",
                                aprendizagem = "Aprende com rotina e repetição",
                                descricaoEstudo = "Estabeleça horários fixos e mantenha consistência. Evite deixar para depois."
                            )
                            "analítico" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Planejamento, organização, perfeccionismo",
                                aprendizagem = "Aprende lendo, escrevendo e estruturando",
                                descricaoEstudo = "Use resumos, esquemas e listas. Evite se prender demais aos detalhes."
                            )
                            "proativo–comunicativo" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Ação, entusiasmo, influência",
                                aprendizagem = "Aprende com projetos em grupo e desafios que envolvem comunicação",
                                descricaoEstudo = "Combine desafios práticos com trocas de ideias. Prefira estudos em equipe e metas visíveis."
                            )
                            "proativo–estável" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Foco, calma, persistência",
                                aprendizagem = "Aprende com metas e prazos claros, mas com ritmo constante",
                                descricaoEstudo = "Defina objetivos e cumpra-os com disciplina. Mantenha o equilíbrio entre agir rápido e respeitar seu tempo."
                            )
                            "proativo–analítico" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Planejamento, decisão, execução",
                                aprendizagem = "Aprende com desafios organizados e metas bem definidas",
                                descricaoEstudo = "Planeje antes de agir e divida o conteúdo em etapas práticas. Equilibre eficiência com atenção aos detalhes."
                            )
                            "comunicativo–estável" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Empatia, calma, colaboração",
                                aprendizagem = "Aprende em grupos cooperativos e ambientes tranquilos",
                                descricaoEstudo = "Prefira estudar em duplas ou grupos pequenos. Troque ideias com colegas, mas mantenha foco."
                            )
                            "comunicativo–analítico" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Expressão, raciocínio, clareza",
                                aprendizagem = "Aprende explicando, debatendo e analisando",
                                descricaoEstudo = "Use apresentações, mapas mentais e resumos explicativos."
                            )
                            "estável–analítico" -> Comportamento(
                                id = null,
                                tipoComportamentoId = tipo.id,
                                caracteristicas = "Constância, organização, responsabilidade",
                                aprendizagem = "Aprende com planejamento e repetição",
                                descricaoEstudo = "Monte cronogramas de estudo e revise periodicamente. Trabalhe com fichas e rotinas bem estruturadas."
                            )
                            else -> Comportamento(id = null, tipoComportamentoId = 0, caracteristicas = "", aprendizagem = "", descricaoEstudo = "")
                        }

                        Log.d("ComportamentoVM", "Salvando comportamento: $comportamento")

                        val saveResponse = comportamentoApi.save(comportamento).execute()
                        if (saveResponse.isSuccessful) {
                            val comportamentoSalvo = saveResponse.body()
                            Log.d("ComportamentoVM", "✅ Comportamento salvo com sucesso: $comportamentoSalvo")
                            _comportamento.value = comportamentoSalvo
                            withContext(Dispatchers.Main) { onResult(comportamentoSalvo?.id) }
                        } else {
                            val errorMsg = saveResponse.errorBody()?.string()
                            Log.e("ComportamentoVM", "❌ Erro ao salvar comportamento: ${saveResponse.code()} - $errorMsg")
                            _error.value = "Erro ao salvar comportamento: ${saveResponse.code()}"
                            withContext(Dispatchers.Main) { onResult(null) }
                        }
                    } else {
                        Log.e("ComportamentoVM", "⚠️ Tipo de comportamento não encontrado: $resultadoNome")
                        _error.value = "Tipo de comportamento não encontrado: $resultadoNome"
                        withContext(Dispatchers.Main) { onResult(null) }
                    }
                } else {
                    Log.e("ComportamentoVM", "❌ Erro ao buscar tipos: ${tiposResponse.code()}")
                    _error.value = "Erro ao buscar tipos: ${tiposResponse.code()}"
                    withContext(Dispatchers.Main) { onResult(null) }
                }
            } catch (e: Exception) {
                Log.e("ComportamentoVM", "💥 Falha ao finalizar teste", e)
                _error.value = "Falha ao finalizar teste: ${e.message}"
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }
}
