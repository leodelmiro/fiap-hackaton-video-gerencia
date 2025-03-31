package com.leodelmiro.gerencia.core.usercase.impl

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorIdGateway
import com.leodelmiro.gerencia.core.usecase.impl.BuscaEnvioPorIdUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class BuscaEnvioPorIdUseCaseImplTest {

    private val buscaEnvioPorIdGateway = mock(BuscaEnvioPorIdGateway::class.java)
    private val buscaEnvioPorIdUseCase = BuscaEnvioPorIdUseCaseImpl(buscaEnvioPorIdGateway)

    @Test
    fun `deve retornar envio quando encontrado pelo id`() {
        val id = 1L
        val envio = criaEnvio(id = id)
        `when`(buscaEnvioPorIdGateway.executar(id)).thenReturn(envio)

        val resultado = buscaEnvioPorIdUseCase.executar(id)

        assertEquals(envio, resultado)
        verify(buscaEnvioPorIdGateway, times(1)).executar(id)
    }

    @Test
    fun `deve retornar null quando envio nao for encontrado pelo id`() {
        val id = 1L
        `when`(buscaEnvioPorIdGateway.executar(id)).thenReturn(null)

        val resultado = buscaEnvioPorIdUseCase.executar(id)

        assertNull(resultado)
        verify(buscaEnvioPorIdGateway, times(1)).executar(id)
    }
}