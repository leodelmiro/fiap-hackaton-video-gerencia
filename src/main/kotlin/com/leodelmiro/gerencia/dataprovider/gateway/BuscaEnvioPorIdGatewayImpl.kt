package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorIdGateway
import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorNomeEAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.dataprovider.repository.ArquivoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class BuscaEnvioPorIdGatewayImpl(
    @Autowired
    private val arquivoRepository: ArquivoRepository,
) : BuscaEnvioPorIdGateway {

    override fun executar(id: Long): Envio? {
        return arquivoRepository.findById(id).getOrNull()?.toEnvio()
    }
}