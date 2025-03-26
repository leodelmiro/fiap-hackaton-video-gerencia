package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.dataprovider.request.NovoVideoRecebidoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class NovoVideoEmProcessamentoConsumer(
    private val salvaEnvioUseCase: SalvaEnvioUseCase,
    private val objectMapper: ObjectMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(NovoVideoEmProcessamentoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.video-em-processamento}")
    fun escutaPagagamentoEfetuado(@Payload payload: String) {
        val message = objectMapper.readTree(payload)["Message"].asText()
        logger.info("Recebido novo video para processamento: $message")
        val videoMessage = objectMapper.readValue(message, NovoVideoRecebidoRequest::class.java)
        salvaEnvioUseCase.executar(videoMessage.toArquivo())
    }
}
