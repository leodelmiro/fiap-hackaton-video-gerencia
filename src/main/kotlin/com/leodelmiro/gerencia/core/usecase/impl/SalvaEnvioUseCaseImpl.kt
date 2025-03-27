package com.leodelmiro.gerencia.core.usecase.impl

import com.leodelmiro.gerencia.core.dataprovider.SalvaEnvioGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase


class SalvaEnvioUseCaseImpl(private val salvaEnvioGateway: SalvaEnvioGateway) : SalvaEnvioUseCase {

    override fun executar(envio: Envio): Envio {
        return salvaEnvioGateway.executar(envio)
    }
}
