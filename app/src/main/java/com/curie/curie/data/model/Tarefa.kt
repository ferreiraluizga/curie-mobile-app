package com.curie.curie.data.model

import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status

data class Tarefa(
    val id: Long,
    val userId: Long,
    val nome: String,
    val prazo: String,
    val prioridade: Prioridade,
    val status: Status
)