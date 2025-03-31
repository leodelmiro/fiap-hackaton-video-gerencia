package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaErroProcessamentoVideoGateway
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.entrypoint.queue.request.VideoProcessadoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class VideoProcessadoConsumer(
    private val salvaEnvioUseCase: SalvaEnvioUseCase,
    private val buscaEnvioPorNomeEAutorUseCase: BuscaEnvioPorNomeEAutorUseCase,
    private val objectMapper: ObjectMapper,
    private val notificaVideoProcessadoGateway: NotificaVideoProcessadoGateway,
    private val notificaErroProcessadoGateway: NotificaErroProcessamentoVideoGateway,
) {

    private val logger: Logger = LoggerFactory.getLogger(VideoProcessadoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.processamento-realizado}")
    fun escutaVideoProcessado(@Payload payload: String) {
        logger.info("Recebido que foi processado: $payload")
        val videoMessage = objectMapper.readValue(payload, VideoProcessadoRequest::class.java)
        buscaEnvioPorNomeEAutorUseCase.executar(videoMessage.nome, videoMessage.autor)?.let {
            val envio = Envio(
                it.id,
                it.nome,
                Status.PROCESSADO,
                it.descricao,
                it.autor,
                videoMessage.url,
                it.criadoEm
            )
            salvaEnvioUseCase.executar(envio)
            notificaVideoProcessadoGateway.executar(envio)
            return
        } ?: run {
            logger.error("$videoMessage não encontrado")
            notificaErroProcessadoGateway.executar(
                Envio(
                    nome = videoMessage.nome,
                    status = Status.ERRO,
                    descricao = videoMessage.descricao,
                    autor = videoMessage.autor
                )
            )
        }
    }
}
