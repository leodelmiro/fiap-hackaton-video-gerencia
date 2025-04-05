package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class VideoEmProcessamentoConsumerTest {

    private val salvaEnvioUseCase = mock(SalvaEnvioUseCase::class.java)
    private val buscaEnvioPorNomeEAutorUseCase = mock(BuscaEnvioPorNomeEAutorUseCase::class.java)
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    private val notificaErroProcessadoGateway = mock(NotificaVideoProcessadoGateway::class.java)

    private val videoEmProcessamentoConsumer = VideoEmProcessamentoConsumer(
        salvaEnvioUseCase,
        buscaEnvioPorNomeEAutorUseCase,
        objectMapper,
        notificaErroProcessadoGateway
    )

    @Test
    fun `deve salvar envio com status EM_PROCESSAMENTO`() {
        val envio = criaEnvio(id = 1, status = Status.EM_PROCESSAMENTO)
        val payload =
            """{"Message":"{\"videoKey\":\"key123\",\"nome\":\"${envio.nome}\",\"descricao\":\"${envio.descricao}\",\"autor\":\"${envio.autor}\"}"}"""
        `when`(salvaEnvioUseCase.executar(envio)).thenReturn(envio)

        videoEmProcessamentoConsumer.escutaEmProcessamento(payload)

        verifyNoInteractions(notificaErroProcessadoGateway)
    }

    @Test
    fun `deve notificar erro quando salvar envio falhar`() {
        val envio = criaEnvio(status = Status.EM_PROCESSAMENTO, criadoEm = null, url = null)
        val envioErro = criaEnvio(status = Status.ERRO, criadoEm = null, url = null)
        val payload =
            """{"Message":"{\"videoKey\":\"key123\",\"nome\":\"${envio.nome}\",\"descricao\":\"${envio.descricao}\",\"autor\":\"${envio.autor}\"}"}"""

        `when`(salvaEnvioUseCase.executar(envio)).thenThrow(RuntimeException("Erro ao salvar envio"))

        videoEmProcessamentoConsumer.escutaEmProcessamento(payload)

        verify(notificaErroProcessadoGateway).executar(envioErro)
    }
}