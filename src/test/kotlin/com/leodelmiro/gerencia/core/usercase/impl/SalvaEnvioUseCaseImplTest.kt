package com.leodelmiro.gerencia.core.usercase.impl

import com.leodelmiro.gerencia.core.dataprovider.SalvaEnvioGateway
import com.leodelmiro.gerencia.core.usecase.impl.SalvaEnvioUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class SalvaEnvioUseCaseImplTest {

    private val salvaEnvioGateway = mock(SalvaEnvioGateway::class.java)
    private val salvaEnvioUseCase = SalvaEnvioUseCaseImpl(salvaEnvioGateway)

    @Test
    fun `deve salvar envio e retornar o envio salvo`() {
        val envio = criaEnvio(id = 1)
        `when`(salvaEnvioGateway.executar(envio)).thenReturn(envio)

        val resultado = salvaEnvioUseCase.executar(envio)

        assertEquals(envio, resultado)
        verify(salvaEnvioGateway, times(1)).executar(envio)
    }
}