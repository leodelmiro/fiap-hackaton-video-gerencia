package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.SalvaEnvioGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SalvaEnvioGatewayImpl(
    @Autowired
    private val envioRepository: EnvioRepository,
) : SalvaEnvioGateway {

    override fun executar(envio: Envio): Envio {
        return envioRepository.save(EnvioEntity(envio)).toEnvio()
    }
}