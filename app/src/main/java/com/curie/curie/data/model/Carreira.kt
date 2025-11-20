package com.curie.curie.data.model

data class Carreira(
    val id: Long?,
    val user: User,
    val descricao: String?,
    val profissaoId: Long?,
    val graduacaoId: Long?,
    val posGraduacaoId: Long?
)
