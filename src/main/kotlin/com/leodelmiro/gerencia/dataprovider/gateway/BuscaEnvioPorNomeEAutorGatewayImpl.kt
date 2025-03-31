package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorNomeEAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BuscaEnvioPorNomeEAutorGatewayImpl(
    @Autowired
    private val envioRepository: EnvioRepository,
) : BuscaEnvioPorNomeEAutorGateway {

    override fun executar(nome: String, autor: String): Envio? {
        return envioRepository.buscarEnvioPorNomeEAutor(nome, autor)?.toEnvio()
    }
}