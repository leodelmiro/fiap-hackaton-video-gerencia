package com.leodelmiro.gerencia.dataprovider.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.dataprovider.NotificaVideoProcessadoGateway
import com.leodelmiro.gerencia.core.domain.Envio
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NotificaVideoProcessadoGatewayImpl(
    private val sqsTemplate: SqsTemplate,
    @Value("\${spring.cloud.aws.sqs.queues.notifica-video-processado}")
    private val notificaVideoProcessado: String? = null,
    private val objectMapper: ObjectMapper,
) : NotificaVideoProcessadoGateway {

    override fun executar(envio: Envio) {
        sqsTemplate.send(notificaVideoProcessado, objectMapper.writeValueAsString(envio))
    }
}