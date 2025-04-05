package com.leodelmiro.gerencia.core.usercase.impl

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorNomeEAutorGateway
import com.leodelmiro.gerencia.core.usecase.impl.BuscaEnvioPorNomeEAutorUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class BuscaEnvioPorNomeEAutorUseCaseImplTest {

    private val buscaEnvioPorNomeEAutorGateway = mock(BuscaEnvioPorNomeEAutorGateway::class.java)
    private val buscaEnvioPorNomeEAutorUseCase = BuscaEnvioPorNomeEAutorUseCaseImpl(buscaEnvioPorNomeEAutorGateway)

    @Test
    fun `deve retornar envio quando encontrado por nome e autor`() {
        val envio = criaEnvio()
        `when`(buscaEnvioPorNomeEAutorGateway.executar(envio.nome, envio.autor)).thenReturn(envio)

        val resultado = buscaEnvioPorNomeEAutorUseCase.executar(envio.nome, envio.autor)

        assertEquals(envio, resultado)
        verify(buscaEnvioPorNomeEAutorGateway, times(1)).executar(envio.nome, envio.autor)
    }

    @Test
    fun `deve retornar null quando envio nao for encontrado por nome e autor`() {
        val nome = "envio1"
        val autor = "autor1"
        `when`(buscaEnvioPorNomeEAutorGateway.executar(nome, autor)).thenReturn(null)

        val resultado = buscaEnvioPorNomeEAutorUseCase.executar(nome, autor)

        assertNull(resultado)
        verify(buscaEnvioPorNomeEAutorGateway, times(1)).executar(nome, autor)
    }
}