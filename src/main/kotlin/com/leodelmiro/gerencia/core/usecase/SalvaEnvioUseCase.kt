package com.leodelmiro.gerencia.core.usecase

import com.leodelmiro.gerencia.core.domain.Envio

interface SalvaEnvioUseCase {
    fun executar(envio: Envio): Envio
}