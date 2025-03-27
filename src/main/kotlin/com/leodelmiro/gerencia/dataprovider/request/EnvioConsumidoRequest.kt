package com.leodelmiro.gerencia.dataprovider.request

data class EnvioConsumidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
)