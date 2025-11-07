package com.curie.curie.data.model

data class Comportamento(
    val id: Long?,
    val tipoComportamentoId: Long,
    val caracteristicas: String,
    val aprendizagem: String,
    val descricaoEstudo: String
)
