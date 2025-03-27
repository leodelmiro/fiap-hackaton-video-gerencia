package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorIdGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class BuscaEnvioPorIdGatewayImpl(
    @Autowired
    private val envioRepository: EnvioRepository,
) : BuscaEnvioPorIdGateway {

    override fun executar(id: Long): Envio? {
        return envioRepository.findById(id).getOrNull()?.toEnvio()
    }
}