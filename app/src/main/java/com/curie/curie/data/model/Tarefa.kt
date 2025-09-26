package com.curie.curie.data.model

data class Tarefa(
    val id: Int? = null,
    val userId: Int,
    val nome: String,
    val prazo: String,
    val prioridade: Prioridade,
    val status: Status
)