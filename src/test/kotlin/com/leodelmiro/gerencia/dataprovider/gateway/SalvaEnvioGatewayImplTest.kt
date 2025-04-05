package com.leodelmiro.gerencia.dataprovider.gateway

import com.leodelmiro.gerencia.dataprovider.repository.EnvioRepository
import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio
import kotlin.test.assertEquals

class SalvaEnvioGatewayImplTest {

    @Test
    fun `deve salvar envio e retornar o envio salvo`() {
        val envioRepository = mock(EnvioRepository::class.java)
        val salvaEnvioGateway = SalvaEnvioGatewayImpl(envioRepository)

        val envio = criaEnvio(id = 1)
        val envioEntity = EnvioEntity(envio)
        val envioEntitySalvo = envioEntity.copy(id = 1L)

        `when`(envioRepository.save(envioEntity)).thenReturn(envioEntitySalvo)

        val resultado = salvaEnvioGateway.executar(envio)

        assertEquals(envio, resultado)
        verify(envioRepository).save(envioEntity)
    }
}