package com.leodelmiro.gerencia.core.domain

class Envio(
    val id: Long? = null,
    val nome: String,
    val status: Status,
    val descricao: String,
    val autor: String,
    val url: String? = null
)