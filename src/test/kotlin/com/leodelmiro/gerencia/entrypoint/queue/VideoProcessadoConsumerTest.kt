package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaErroProcessamentoVideoGateway
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.entrypoint.queue.request.VideoProcessadoRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaEnvio

class VideoProcessadoConsumerTest {

    private val salvaEnvioUseCase = mock(SalvaEnvioUseCase::class.java)
    private val buscaEnvioPorNomeEAutorUseCase = mock(BuscaEnvioPorNomeEAutorUseCase::class.java)
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    private val notificaVideoProcessadoGateway = mock(NotificaVideoProcessadoGateway::class.java)
    private val notificaErroProcessadoGateway = mock(NotificaErroProcessamentoVideoGateway::class.java)
    private val videoProcessadoConsumer = VideoProcessadoConsumer(
        salvaEnvioUseCase,
        buscaEnvioPorNomeEAutorUseCase,
        objectMapper,
        notificaVideoProcessadoGateway,
        notificaErroProcessadoGateway
    )

    @Test
    fun `deve salvar envio com status PROCESSADO e notificar sucesso`() {
        val envio = criaEnvio(id = 1, status = Status.EM_PROCESSAMENTO)
        val videoRequest = VideoProcessadoRequest(
            nome = envio.nome,
            autor = envio.autor,
            descricao = envio.descricao,
            url = "http://video-url.com",
            key = "test"
        )
        val payload = objectMapper.writeValueAsString(videoRequest)

        `when`(buscaEnvioPorNomeEAutorUseCase.executar(envio.nome, envio.autor)).thenReturn(envio)

        videoProcessadoConsumer.escutaVideoProcessado(payload)

        val envioProcessado = Envio(
            id = envio.id,
            nome = envio.nome,
            status = Status.PROCESSADO,
            descricao = envio.descricao,
            autor = envio.autor,
            url = videoRequest.url,
            criadoEm = envio.criadoEm
        )

        verify(salvaEnvioUseCase).executar(envioProcessado)
        verify(notificaVideoProcessadoGateway).executar(envioProcessado)
        verifyNoInteractions(notificaErroProcessadoGateway)
    }

    @Test
    fun `deve notificar erro quando envio nao for encontrado`() {
        val videoRequest = VideoProcessadoRequest(
            nome = "video-inexistente",
            autor = "autor-desconhecido",
            descricao = "descricao",
            url = "http://video-url.com",
            key = "test"
        )
        val payload = objectMapper.writeValueAsString(videoRequest)

        `when`(buscaEnvioPorNomeEAutorUseCase.executar(videoRequest.nome, videoRequest.autor)).thenReturn(null)

        videoProcessadoConsumer.escutaVideoProcessado(payload)

        val envioErro = Envio(
            nome = videoRequest.nome,
            status = Status.ERRO,
            descricao = videoRequest.descricao,
            autor = videoRequest.autor
        )

        verify(notificaErroProcessadoGateway).executar(envioErro)
        verifyNoInteractions(salvaEnvioUseCase, notificaVideoProcessadoGateway)
    }
}