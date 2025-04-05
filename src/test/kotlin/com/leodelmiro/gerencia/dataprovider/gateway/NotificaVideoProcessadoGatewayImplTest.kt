package com.leodelmiro.gerencia.dataprovider.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import utils.criaEnvio

class NotificaVideoProcessadoGatewayImplTest {
    private val sqsTemplate = mock(SqsTemplate::class.java)
    private val objectMapper = mock(ObjectMapper::class.java)
    private val notificaVideoProcessado = "test-queue"

    private val notificaVideoProcessadoGateway = NotificaVideoProcessadoGatewayImpl(
        sqsTemplate = sqsTemplate,
        notificaVideoProcessado = notificaVideoProcessado,
        objectMapper = objectMapper
    )

    @Test
    fun `deve enviar mensagem para a fila SQS`() {
        val envio = criaEnvio(id = 1)
        val envioJson = """{"id":${envio.id},"autor":"${envio.autor}"}"""

        Mockito.`when`(objectMapper.writeValueAsString(envio)).thenReturn(envioJson)

        notificaVideoProcessadoGateway.executar(envio)

        verify(sqsTemplate).send(notificaVideoProcessado, envioJson)
    }
}