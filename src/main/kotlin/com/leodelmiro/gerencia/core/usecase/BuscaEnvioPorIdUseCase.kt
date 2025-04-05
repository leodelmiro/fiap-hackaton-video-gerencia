package com.leodelmiro.gerencia.core.usecase

import com.leodelmiro.gerencia.core.domain.Envio

interface BuscaEnvioPorIdUseCase {
    fun executar(id: Long): Envio?
}