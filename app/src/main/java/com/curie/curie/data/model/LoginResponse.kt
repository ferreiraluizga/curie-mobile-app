package com.curie.curie.data.model

data class LoginResponse(
    val token: String,
    val userId: Long,
    val username: String
)