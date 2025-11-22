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
            } catch (e: GoogleGenerativeAIException) {

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
            } catch (e: Exception) {
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
            "OBRIGATÓRIO: escolha EXATAMENTE 1 (uma) graduação da lista fornecida. " +
                    "Você NÃO PODE criar novas, sugerir alternativas, usar sinônimos ou alterar ortografia. " +
                    "Se tentar usar algo fora da lista, você DEVE automaticamente selecionar a PRIMEIRA opção. " +
                    "A graduação escolhida DEVE corresponder EXATAMENTE (caracter por caracter) a um dos itens da lista: " +
                    graduacoes.joinToString(", ") + ". " +
                    "Coloque o valor escolhido dentro de <GRAD> e </GRAD> e NUNCA deixe vazio."

        val instrucaoPos =
            "OBRIGATÓRIO: escolha EXATAMENTE 1 (uma) pós-graduação da lista fornecida. " +
                    "Você NÃO PODE criar novas, sugerir alternativas ou alterar ortografia. " +
                    "Se você tentar criar qualquer coisa fora da lista, considere automaticamente a PRIMEIRA opção. " +
                    "A pós escolhida DEVE corresponder exatamente (caracter por caracter) a um dos itens da lista: " +
                    posGraduacoes.joinToString(", ") + ". " +
                    "Coloque o valor dentro de <POS> e </POS>."

        val instrucaoProf =
            "OBRIGATÓRIO: escolha EXATAMENTE 1 (uma) profissão da lista fornecida. " +
                    "Você NÃO PODE inventar novas profissões, sugerir sinônimos, misturar termos ou alterar ortografia. " +
                    "Se tentar usar qualquer valor fora da lista, você deve automaticamente selecionar a PRIMEIRA opção. " +
                    "A profissão escolhida DEVE corresponder EXATAMENTE (caracter por caracter) a um dos itens da lista: " +
                    profissoes.joinToString(", ") + ". " +
                    "Coloque o valor dentro de <PROF> e </PROF>."

        return """
        Você é um orientador profissional especializado.
        
        ⚠ REGRAS ABSOLUTAS (SIGA SEM EXCEÇÃO):
        1. Escolha EXATAMENTE UMA opção de cada categoria:
           - 1 graduação
           - 1 pós-graduação
           - 1 profissão
        2. TODAS as escolhas devem vir exclusivamente das listas fornecidas.
        3. É proibido inventar novos nomes.
        4. É proibido sugerir mais de uma opção por categoria.
        5. É proibido deixar a marcação vazia.
        6. Se necessário, escolha a mais compatível com base nas áreas recomendadas.
        
        ANALISE OS DADOS:
        
        • Áreas recomendadas: ${areasRecomendadas.joinToString(", ")}
        • Graduações disponíveis: ${graduacoes.joinToString(", ")}
        • Pós disponíveis: ${posGraduacoes.joinToString(", ")}
        • Profissões disponíveis: ${profissoes.joinToString(", ")}
        • Temperamento: ${temperamento ?: "Não informado"}
        • Comportamento: ${comportamento ?: "Não informado"}
        
        Gere:
        
        1) Perfil Geral:
        <PERFIL> Aqui você escreve o perfil geral baseado nos dados. </PERFIL>
        2) Área de carreira escolhida:
        <AREA> Aqui você escreve a área final escolhida. </AREA>
        3) Graduação escolhida:
           - $instrucaoGrad
        4) Pós-graduação escolhida:
           - $instrucaoPos
        5) Profissão escolhida:
           - $instrucaoProf
        6) Plano de carreira estruturado
        <PLANO> Aqui você descreve o plano estruturado. </PLANO>
        7) Resumo (<RESUMO></RESUMO>), no máximo 240 caracteres
        
        ⚠ REGRA DE CONTINGÊNCIA:
        Se você não identificar nenhuma opção realmente compatível em uma categoria,
        você deve automaticamente selecionar a PRIMEIRA opção da lista enviada.
        Nunca deixe <GRAD>, <POS> ou <PROF> vazios.
        """.trimIndent()
    }
}
