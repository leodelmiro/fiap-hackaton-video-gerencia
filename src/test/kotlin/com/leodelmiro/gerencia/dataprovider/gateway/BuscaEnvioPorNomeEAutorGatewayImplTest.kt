package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio
import java.time.LocalDateTime

class BuscaEnvioPorNomeEAutorGatewayImplTest {

    private val envioRepository = mock(EnvioRepository::class.java)
    private val buscaEnvioPorNomeEAutorGateway = BuscaEnvioPorNomeEAutorGatewayImpl(envioRepository)

    @Test
    fun `deve retornar envio quando encontrado no repository`() {
        val criadoEm = LocalDateTime.now()
        val envioEntity = EnvioEntity(criaEnvio(id = 1, criadoEm = criadoEm))
        val envio = criaEnvio(id = 1, criadoEm = criadoEm)
        `when`(envioRepository.buscarEnvioPorNomeEAutor(envio.nome, envio.autor)).thenReturn(envioEntity)

        val resultado = buscaEnvioPorNomeEAutorGateway.executar(envio.nome, envio.autor)

        assertEquals(envio, resultado)
        verify(envioRepository, times(1)).buscarEnvioPorNomeEAutor(envio.nome, envio.autor)
    }

    @Test
    fun `deve retornar null quando envio nao for encontrado no repository`() {
        val nome = "envio1"
        val autor = "autor1"
        `when`(envioRepository.buscarEnvioPorNomeEAutor(nome, autor)).thenReturn(null)

        val resultado = buscaEnvioPorNomeEAutorGateway.executar(nome, autor)

        assertNull(resultado)
        verify(envioRepository, times(1)).buscarEnvioPorNomeEAutor(nome, autor)
    }
}