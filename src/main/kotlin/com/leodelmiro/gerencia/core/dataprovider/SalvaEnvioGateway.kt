package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio

interface SalvaEnvioGateway {
    fun executar(envio: Envio): Envio
}