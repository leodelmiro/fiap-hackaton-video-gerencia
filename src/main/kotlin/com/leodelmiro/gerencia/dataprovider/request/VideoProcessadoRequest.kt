package com.leodelmiro.gerencia.dataprovider.request

data class VideoProcessadoRequest(
    val key: String,
    val nome: String,
    val descricao: String,
    val autor: String,
    val url: String
)