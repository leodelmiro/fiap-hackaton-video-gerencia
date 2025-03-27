package com.leodelmiro.gerencia.core.domain

import java.time.LocalDateTime

class Envio(
    val id: Long? = null,
    val nome: String,
    val status: Status,
    val descricao: String,
    val autor: String,
    val url: String? = null,
    val criadoEm: LocalDateTime? = null
)