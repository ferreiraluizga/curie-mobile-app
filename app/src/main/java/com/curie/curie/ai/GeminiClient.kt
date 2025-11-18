package com.curie.curie.ai

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiClient(
    private val apiKey: String
) {

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    /**
     * 🔹 Função principal para gerar o plano de carreira.
     * Você só chama ela e recebe o texto pronto.
     */
    suspend fun gerarPlanoDeCarreira(
        areasRecomendadas: List<String>,
        graduacoes: List<String>,
        posGraduacoes: List<String>,
        profissoes: List<String>,
        temperamento: String?,
        comportamento: String?
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
     * 🔹 Gemini — método genérico
     */
    private suspend fun gerar(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = model.generateContent(prompt)
                response.text ?: "Nenhuma resposta gerada."
            } catch (e: Exception) {
                Log.e("GeminiClient", "Erro ao gerar conteúdo", e)
                "Erro ao processar análise: ${e.message}"
            }
        }
    }

    /**
     * 🔹 Aqui fica seu prompt oficial de análise de carreira
     * (MODIFICADO COM AS TAGS)
     */
    private fun construirPrompt(
        areasRecomendadas: List<String>,
        graduacoes: List<String>,
        posGraduacoes: List<String>,
        profissoes: List<String>,
        temperamento: String?,
        comportamento: String?
    ): String {

        // Instruções claras para a IA sobre as tags
        val instrucaoGrad = "IMPORTANTE: Para CADA graduação sugerida, coloque o nome exato dentro de tags <GRAD> e </GRAD>. Exemplo: <GRAD>Administração</GRAD>."
        val instrucaoPos = "IMPORTANTE: Para CADA pós-graduação sugerida, coloque o nome exato dentro de tags <POS> e </POS>. Exemplo: <POS>MBA em Gestão de Projetos</POS>."
        val instrucaoProf = "IMPORTANTE: Para CADA profissão sugerida, coloque o nome exato dentro de tags <PROF> e </PROF>. Exemplo: <PROF>Analista de Negócios</PROF>."

        return """
            Você é um orientador profissional especializado em análise vocacional,
            comportamento e temperamento. Seu objetivo é gerar um **Plano de Carreira Completo**
            com linguagem clara, objetiva e motivadora.

            ANALISE OS DADOS A SEGUIR:

            • Áreas Recomendadas: ${areasRecomendadas.joinToString(", ")}
            • Graduações Relacionadas: ${graduacoes.joinToString(", ")}
            • Pós-Graduações Relacionadas: ${posGraduacoes.joinToString(", ")}
            • Possíveis Profissões: ${profissoes.joinToString(", ")}
            • Temperamento do Usuário: ${temperamento ?: "Não informado"}
            • Comportamento do Usuário: ${comportamento ?: "Não informado"}

            Gere uma análise completa contendo:

            1) **Perfil Geral do Usuário** - Faça uma síntese combinando temperamento + comportamento.

            2) **Indicação de Área de Carreira** - Explique por que as áreas recomendadas fazem sentido para o perfil.

            3) **Sugestão de Graduação** - Indique 1 graduação principal e por quê.
            - $instrucaoGrad

            4) **Sugestão de Pós-Graduação** - Aponte 1 especialização adequada ao perfil e área.
            - $instrucaoPos

            5) **Sugestão de Profissões** - Liste 1 profissão com breves justificativas.
            - $instrucaoProf

            6) **Plano de Carreira Estruturado (Passo a Passo)** - Curto prazo (0-1 ano)
            - Médio prazo (1-3 anos)
            - Longo prazo (3-5 anos)
            - Competências a desenvolver

            7) **Resumo Final Motivacional**

            Responda tudo em português do Brasil, com clara e tom profissional,
            evitando repetição e mantendo objetividade.
        """.trimIndent()
    }
}