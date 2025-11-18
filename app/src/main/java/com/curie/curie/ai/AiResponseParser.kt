package com.curie.curie.ai

/**
 * Objeto utilitário para extrair dados estruturados
 * das respostas de texto geradas pela IA.
 */
object AiResponseParser {

    /**
     * Função genérica para extrair **o primeiro valor** entre tags específicas de um texto.
     * @param texto O texto completo retornado pela IA.
     * @param tag O nome da tag (ex: "PROF", "GRAD", "POS").
     * @return Uma string com o primeiro valor encontrado ou uma string vazia ("") se não encontrar.
     */
    private fun extrairPrimeiroPorTag(texto: String, tag: String): String {
        // A parte (.*?) captura TUDO, incluindo espaços, entre as tags.
        val regex = "<$tag>(.*?)</$tag>".toRegex(RegexOption.DOT_MATCHES_ALL)

        val match = regex.find(texto)

        // Retorna o valor capturado (grupo 1), remove espaços extras (trim) ou retorna "" se não encontrar.
        return match?.groupValues?.get(1)?.trim() ?: ""
    }

    /**
     * Extrai a primeira profissão (tag <PROF>) da resposta da IA.
     */
    fun extrairPrimeiraProfissao(respostaIA: String): String {
        return extrairPrimeiroPorTag(respostaIA, "PROF")
    }

    /**
     * Extrai a primeira graduação (tag <GRAD>) da resposta da IA.
     */
    fun extrairPrimeiraGraduacao(respostaIA: String): String {
        return extrairPrimeiroPorTag(respostaIA, "GRAD")
    }

    /**
     * Extrai a primeira pós-graduação (tag <POS>) da resposta da IA.
     */
    fun extrairPrimeiraPosGraduacao(respostaIA: String): String {
        return extrairPrimeiroPorTag(respostaIA, "POS")
    }

    /**
     * Extrai o Resumo Final Motivacional (seção 7).
     * @param respostaIA O texto completo retornado pela IA.
     * @return Uma string contendo o texto do resumo.
     */
    fun extrairResumoFinal(respostaIA: String): String {
        // Regex para encontrar "7) Resumo Final Motivacional" e capturar o texto a seguir.
        // \\s*: Captura quaisquer espaços em branco, incluindo quebras de linha.
        // (.*): Captura o resumo. Como é a última seção, ele captura até o fim do texto.
        val regex = "7\\) Resumo Final Motivacional\\s*(.*)".toRegex(RegexOption.DOT_MATCHES_ALL)

        val match = regex.find(respostaIA)

        // Retorna o texto capturado (grupo 1) ou uma string vazia.
        return match?.groupValues?.get(1)?.trim() ?: ""
    }
}