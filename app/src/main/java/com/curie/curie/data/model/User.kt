package com.curie.curie.data.model

data class User(
    val id: Long,
    val nome: String,
    val telefone: String,
    val descricao: String,
    val nascimento: String,
    val email: String,
    val password: String,
    val imagemUrl: String? = null
)
