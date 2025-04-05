package com.leodelmiro.gerencia.core.usercase.impl

import com.leodelmiro.gerencia.core.dataprovider.ListaEnviosPorAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.impl.ListaEnviosPorAutorUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class ListaEnviosPorAutorUseCaseImplTest {

    private val listaEnviosPorAutorGateway = mock(ListaEnviosPorAutorGateway::class.java)
    private val listaEnviosPorAutorUseCase = ListaEnviosPorAutorUseCaseImpl(listaEnviosPorAutorGateway)

    @Test
    fun `deve retornar lista de envios quando encontrados por autor e status`() {
        val autor = criaEnvio().autor
        val status = Status.PROCESSADO
        val envios = listOf(
            criaEnvio(id = 1, status = Status.PROCESSADO),
            criaEnvio(id = 2, status = Status.PROCESSADO)
        )
        `when`(listaEnviosPorAutorGateway.executar(autor, status)).thenReturn(envios)

        val resultado = listaEnviosPorAutorUseCase.executar(autor, status)

        assertEquals(envios, resultado)
        verify(listaEnviosPorAutorGateway, times(1)).executar(autor, status)
    }

    @Test
    fun `deve retornar lista vazia quando nenhum envio for encontrado`() {
        val autor = "autor1"
        val status = Status.ERRO
        `when`(listaEnviosPorAutorGateway.executar(autor, status)).thenReturn(emptyList())

        val resultado = listaEnviosPorAutorUseCase.executar(autor, status)

        assertEquals(emptyList<Envio>(), resultado)
        verify(listaEnviosPorAutorGateway, times(1)).executar(autor, status)
    }

    @Test
    fun `deve retornar lista de envios quando status for null`() {
        val autor = criaEnvio().autor
        val envios = listOf(
            criaEnvio(id = 1),
            criaEnvio(id = 2)
        )
        `when`(listaEnviosPorAutorGateway.executar(autor, null)).thenReturn(envios)

        val resultado = listaEnviosPorAutorUseCase.executar(autor, null)

        assertEquals(envios, resultado)
        verify(listaEnviosPorAutorGateway, times(1)).executar(autor, null)
    }
}