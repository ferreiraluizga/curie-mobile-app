package com.curie.curie.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val descricao: String,
    val nascimento: String, // enviaremos em formato ISO, ex: "2025-11-06T00:00:00"
    val telefone: String
)
