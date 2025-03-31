package com.leodelmiro.gerencia.entrypoint.queue.request

data class EnvioConsumidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
)