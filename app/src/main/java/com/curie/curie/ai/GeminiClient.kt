package com.curie.curie.ai

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiClient(
    private val apiKey: String
) {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    /**
     * 🔹 Função principal de geração
     */
    suspend fun gerarPlanoDeCarreira(
        areasRecomendadas: List<String>,
        graduacoes: List<String>,
        posGraduacoes: List<String>,
        profissoes: List<String>,
        temperamento: String,
        comportamento: String
    ): String {

        val prompt = construirPrompt(
            areasRecomendadas,
            graduacoes,
            posGraduacoes,
            profissoes,
            temperamento,
            comportamento
        )

        return gerar(prompt)
    }

    /**
     * 🔹 Tratamento seguro de chamadas ao Gemini
     */
    private suspend fun gerar(prompt: String): String {
        return withContext(Dispatchers.IO) {

            try {
                Log.d("GeminiClient", "🔵 Enviando prompt para Gemini...")
                val response = model.generateContent(prompt)
                val text = response.text ?: ""

                Log.d("GeminiClient", "🟢 Resposta recebida do Gemini.")
                text
            }
            catch (e: GoogleGenerativeAIException) {

                Log.e("GeminiClient", "❌ Erro do Gemini: ${e.message}")

                return@withContext when {
                    e.message?.contains("503") == true ||
                            e.message?.contains("UNAVAILABLE") == true ||
                            e.message?.contains("overloaded", ignoreCase = true) == true ->
                        "⚠ O modelo está sobrecarregado agora. Tente novamente em instantes."

                    e.message?.contains("429") == true ->
                        "⚠ Muitas requisições recentes. Tente novamente mais tarde."

                    else ->
                        "⚠ Não foi possível gerar o plano no momento. Tente novamente."
                }
            }
            catch (e: Exception) {
                Log.e("GeminiClient", "❌ Erro inesperado: ${e.message}")
                return@withContext "⚠ Erro inesperado ao gerar sua análise."
            }
        }
    }

    /**
     * 🔹 Construção do prompt oficial
     */
    private fun construirPrompt(
        areasRecomendadas: List<String>,
        graduacoes: List<String>,
        posGraduacoes: List<String>,
        profissoes: List<String>,
        temperamento: String?,
        comportamento: String?
    ): String {

        val instrucaoGrad =
            "IMPORTANTE: Para CADA graduação sugerida, coloque o nome exato dentro de <GRAD> e </GRAD>."
        val instrucaoPos =
            "IMPORTANTE: Para CADA pós-graduação sugerida, coloque o nome exato dentro de <POS> e </POS>."
        val instrucaoProf =
            "IMPORTANTE: Para CADA profissão sugerida, coloque o nome exato dentro de <PROF> e </PROF>."

        return """
            Você é um orientador profissional especializado em análise vocacional,
            comportamento e temperamento. Seu objetivo é gerar um **Plano de Carreira Completo**
            claro, objetivo e personalizado.

            ANALISE OS DADOS:

            • Áreas Recomendadas: ${areasRecomendadas.joinToString(", ")}
            • Graduações Relacionadas: ${graduacoes.joinToString(", ")}
            • Pós-Graduações Relacionadas: ${posGraduacoes.joinToString(", ")}
            • Possíveis Profissões: ${profissoes.joinToString(", ")}
            • Temperamento: ${temperamento ?: "Não informado"}
            • Comportamento: ${comportamento ?: "Não informado"}

            Gere:

            1) Perfil Geral do Usuário (comportamento + temperamento)
            2) Indicação da Área de Carreira ideal
            3) Sugestão de Graduação
               - $instrucaoGrad
            4) Sugestão de Pós-Graduação
               - $instrucaoPos
            5) Sugestão de Profissão
               - $instrucaoProf
            6) Plano de Carreira Estruturado:
               - Curto prazo (0-1 ano)
               - Médio prazo (1-3 anos)
               - Longo prazo (3-5 anos)
               - Competências a desenvolver
            7) Resumo Final Motivacional

            Responda em português do Brasil, com clareza e objetividade.
        """.trimIndent()
    }
}
