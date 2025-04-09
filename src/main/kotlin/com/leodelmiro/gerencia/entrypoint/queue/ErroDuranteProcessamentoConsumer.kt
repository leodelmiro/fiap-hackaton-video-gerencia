package com.leodelmiro.gerencia.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaErroProcessamentoVideoGateway
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.SalvaEnvioUseCase
import com.leodelmiro.gerencia.entrypoint.queue.request.EnvioConsumidoRequest
import com.leodelmiro.gerencia.entrypoint.queue.request.ErroEnvioConsumidoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ErroDuranteProcessamentoConsumer(
    private val notificaErroProcessadoGateway: NotificaErroProcessamentoVideoGateway,
    private val objectMapper: ObjectMapper,
    private val salvaEnvioUseCase: SalvaEnvioUseCase,
    private val buscaEnvioPorNomeEAutorUseCase: BuscaEnvioPorNomeEAutorUseCase,
) {

    private val logger: Logger = LoggerFactory.getLogger(ErroDuranteProcessamentoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.erro-processamento}")
    fun escutaErroAoProcessar(@Payload payload: String) {
        logger.info("Recebido novo video com erro para processamento: $payload")
        val videoMessage = objectMapper.readValue(payload, ErroEnvioConsumidoRequest::class.java)
        buscaEnvioPorNomeEAutorUseCase.executar(videoMessage.nome, videoMessage.autor)?.let {
            salvaEnvioUseCase.executar(
                Envio(
                    it.id,
                    it.nome,
                    Status.ERRO,
                    it.descricao,
                    it.autor
                )
            )
            notificaErroProcessadoGateway.executar(it)
            return
        } ?: run {
            logger.error("$videoMessage não encontrado")
        }

    }
}
