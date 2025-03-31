package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio
import java.util.*

class BuscaEnvioPorIdGatewayImplTest {

    private val envioRepository = mock(EnvioRepository::class.java)
    private val buscaEnvioPorIdGateway = BuscaEnvioPorIdGatewayImpl(envioRepository)

    @Test
    fun `deve retornar envio quando encontrado no repository`() {
        val id = 1L
        val envioEntity = EnvioEntity(criaEnvio(id = id))
        val envio = criaEnvio(id = id)
        `when`(envioRepository.findById(id)).thenReturn(Optional.of(envioEntity))

        val resultado = buscaEnvioPorIdGateway.executar(id)

        assertEquals(envio, resultado)
        verify(envioRepository, times(1)).findById(id)
    }

    @Test
    fun `deve retornar null quando envio nao for encontrado no repository`() {
        val id = 1L
        `when`(envioRepository.findById(id)).thenReturn(Optional.empty())

        val resultado = buscaEnvioPorIdGateway.executar(id)

        assertNull(resultado)
        verify(envioRepository, times(1)).findById(id)
    }
}