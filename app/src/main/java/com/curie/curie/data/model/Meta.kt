package com.curie.curie.data.model

import com.curie.curie.data.model.enums.Prioridade
import com.curie.curie.data.model.enums.Status

data class Meta (
    val id: Long,
    val userId: Long,
    val objetivo: String,
    val descricao: String,
    val inicio: String,
    val fim: String,
    val prioridade: Prioridade,
    val status: Status
)