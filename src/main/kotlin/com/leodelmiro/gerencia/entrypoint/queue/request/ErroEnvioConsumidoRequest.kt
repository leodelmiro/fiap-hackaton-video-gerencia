package com.leodelmiro.gerencia.entrypoint.queue.request

data class ErroEnvioConsumidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val key: String
)