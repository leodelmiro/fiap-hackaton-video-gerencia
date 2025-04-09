package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaErroProcessamentoVideoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class ErroDuranteProcessamentoConsumerTest {

    private val notificaErroProcessadoGateway = mock(NotificaErroProcessamentoVideoGateway::class.java)
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    private val salvaEnvioUseCase = mock(SalvaEnvioUseCase::class.java)
    private val buscaEnvioPorNomeEAutorUseCase = mock(BuscaEnvioPorNomeEAutorUseCase::class.java)
    private val erroDuranteProcessamentoConsumer = ErroDuranteProcessamentoConsumer(
        notificaErroProcessadoGateway,
        objectMapper,
        salvaEnvioUseCase,
        buscaEnvioPorNomeEAutorUseCase
    )

    @Test
    fun `deve processar mensagem e notificar erro quando envio for encontrado`() {
        val envio = criaEnvio(id = 1, status = Status.ERRO)
        val payload =
            """{"nome":"${envio.nome}","autor":"${envio.autor}","descricao":"${envio.descricao}","key":"test"}"""

        `when`(buscaEnvioPorNomeEAutorUseCase.executar(envio.nome, envio.autor)).thenReturn(envio)
        `when`(salvaEnvioUseCase.executar(envio)).thenReturn(envio)

        erroDuranteProcessamentoConsumer.escutaErroAoProcessar(payload)

        verify(salvaEnvioUseCase).executar(
            Envio(
                envio.id,
                envio.nome,
                envio.status,
                envio.descricao,
                envio.autor
            )
        )
        verify(notificaErroProcessadoGateway).executar(envio)
    }
}