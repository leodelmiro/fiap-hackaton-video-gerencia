package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.ListaEnviosPorAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.dataprovider.repository.ArquivoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ListaEnviosPorAutorGatewayImpl(
    @Autowired
    private val arquivoRepository: ArquivoRepository,
) : ListaEnviosPorAutorGateway {

    override fun executar(autor: String, status: Status?): List<Envio> {
        return arquivoRepository.listarAquivosPorAutor(autor, status?.valor).map { arquivoEntity -> arquivoEntity.toEnvio() }
    }
}