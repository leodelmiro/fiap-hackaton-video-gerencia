package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.SalvaEnvioGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.dataprovider.repository.ArquivoRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SalvaEnvioGatewayImpl(
    @Autowired
    private val arquivoRepository: ArquivoRepository,
) : SalvaEnvioGateway {

    override fun executar(envio: Envio): Envio {
        return arquivoRepository.save(EnvioEntity(envio)).toEnvio()
    }
}