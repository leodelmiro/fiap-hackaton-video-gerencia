package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio

interface BuscaEnvioPorIdGateway {
    fun executar(id: Long): Envio?
}