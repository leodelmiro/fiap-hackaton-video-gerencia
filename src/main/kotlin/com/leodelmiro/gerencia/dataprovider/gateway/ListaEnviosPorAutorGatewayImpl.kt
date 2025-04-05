package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.dataprovider.ListaEnviosPorAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ListaEnviosPorAutorGatewayImpl(
    @Autowired
    private val envioRepository: EnvioRepository,
) : ListaEnviosPorAutorGateway {

    override fun executar(autor: String, status: Status?): List<Envio> {
        return envioRepository.listarEnviosPorAutor(autor, status?.valor).map { envioEntity -> envioEntity.toEnvio() }
    }
}