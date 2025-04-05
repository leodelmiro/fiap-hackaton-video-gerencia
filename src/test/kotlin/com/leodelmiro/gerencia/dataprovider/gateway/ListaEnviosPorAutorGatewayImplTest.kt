package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import utils.criaEnvio
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ListaEnviosPorAutorGatewayImplTest {
    private val envioRepository: EnvioRepository = mock(EnvioRepository::class.java)

    private val listaEnviosPorAutorGateway = ListaEnviosPorAutorGatewayImpl(envioRepository)

    @Test
    fun `deve retornar lista de envios quando autor e status forem fornecidos`() {
        val criadoEm = LocalDateTime.now()
        val arquivoEntity = EnvioEntity(criaEnvio(id = 1, criadoEm = criadoEm))
        val envioEsperado = criaEnvio(id = 1, criadoEm = criadoEm)
        Mockito.`when`(envioRepository.listarEnviosPorAutor(envioEsperado.autor, envioEsperado.status.valor))
            .thenReturn(listOf(arquivoEntity))

        val resultado = listaEnviosPorAutorGateway.executar(envioEsperado.autor, envioEsperado.status)

        assertEquals(1, resultado.size)
        assertEquals(envioEsperado, resultado[0])
    }

    @Test
    fun `deve retornar lista vazia quando nenhum envio for encontrado`() {
        val autor = "autor1"
        val status = Status.EM_PROCESSAMENTO

        Mockito.`when`(envioRepository.listarEnviosPorAutor(autor, status.valor))
            .thenReturn(emptyList())

        val resultado = listaEnviosPorAutorGateway.executar(autor, status)

        assertEquals(0, resultado.size)
    }
}