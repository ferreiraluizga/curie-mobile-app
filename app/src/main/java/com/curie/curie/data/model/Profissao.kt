package com.curie.curie.data.model

import com.curie.curie.data.model.enums.Demanda

data class Profissao(
    val id: Long,
    val nome: String,
    val descricao: String,
    val salario: Double,
    val demanda: Demanda
)
