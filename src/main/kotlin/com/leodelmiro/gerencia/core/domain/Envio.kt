package com.leodelmiro.gerencia.core.domain

import java.time.LocalDateTime

data class Envio(
    val id: Long? = null,
    val nome: String,
    var status: Status,
    val descricao: String,
    val autor: String,
    val url: String? = null,
    val criadoEm: LocalDateTime? = null
)