package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.dataprovider.request.VideoProcessadoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class VideoProcessadoConsumer(
    private val salvaEnvioUseCase: SalvaEnvioUseCase,
    private val buscaEnvioPorNomeEAutorUseCase: BuscaEnvioPorNomeEAutorUseCase,
    private val objectMapper: ObjectMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(VideoProcessadoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.processamento-realizado}")
    fun escutaPagagamentoEfetuado(@Payload payload: String) {
        val message = objectMapper.readTree(payload)["Message"].asText()
        logger.info("Recebido que foi processado: $message")
        val videoMessage = objectMapper.readValue(message, VideoProcessadoRequest::class.java)
        buscaEnvioPorNomeEAutorUseCase.executar(videoMessage.nome, videoMessage.autor)?.let {
            salvaEnvioUseCase.executar(
                Envio(
                    it.id,
                    it.nome,
                    Status.PROCESSADO,
                    it.descricao,
                    it.autor,
                    videoMessage.url
                )
            )
            return
        } ?: run {
            logger.error("$videoMessage não encontrado")
        }
    }
}
