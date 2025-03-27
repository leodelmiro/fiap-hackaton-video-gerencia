package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.dataprovider.request.EnvioConsumidoRequest
import com.leodelmiro.gerencia.dataprovider.request.VideoProcessadoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class VideoEmProcessamentoConsumer(
    private val salvaEnvioUseCase: SalvaEnvioUseCase,
    private val buscaEnvioPorNomeEAutorUseCase: BuscaEnvioPorNomeEAutorUseCase,
    private val objectMapper: ObjectMapper,
    private val notificaErroProcessadoGateway: NotificaVideoProcessadoGateway,

    ) {

    private val logger: Logger = LoggerFactory.getLogger(VideoEmProcessamentoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.video-em-processamento}")
    fun escutaEmProcessamento(@Payload payload: String) {
        val message = objectMapper.readTree(payload)["Message"].asText()
        logger.info("Recebido video: $message")
        val videoMessage = objectMapper.readValue(message, EnvioConsumidoRequest::class.java)
        try {
            salvaEnvioUseCase.executar(
                Envio(
                    nome = videoMessage.nome,
                    status = Status.EM_PROCESSAMENTO,
                    descricao = videoMessage.descricao,
                    autor = videoMessage.autor
                )
            )
        } catch (exception: Exception) {
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
