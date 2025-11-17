package com.curie.curie.data.model

data class Graduacao(
    val id: Long,
    val nome: String,
    val descricao: String,
    val duracao: Int,
    val areaCarreiraId: Long
)
