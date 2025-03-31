package com.leodelmiro.gerencia.dataprovider.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.gerencia.core.domain.Envio
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Value
import utils.criaEnvio

class NotificaErroProcessamentoVideoGatewayImplTest {

    private val sqsTemplate = mock(SqsTemplate::class.java)
    private val objectMapper = mock(ObjectMapper::class.java)
    private val notificaVideoProcessado = "test-queue"

    private val gateway = NotificaErroProcessamentoVideoGatewayImpl(
        sqsTemplate = sqsTemplate,
        notificaVideoProcessado = notificaVideoProcessado,
        objectMapper = objectMapper
    )

    @Test
    fun `deve enviar mensagem para a fila SQS`() {
        val envio = criaEnvio(id=1)
        val envioJson = """{"id":1,"nome":"${envio.nome}","autor":"${envio.autor}"}"""

        `when`(objectMapper.writeValueAsString(envio)).thenReturn(envioJson)

        gateway.executar(envio)

        verify(objectMapper, times(1)).writeValueAsString(envio)
        verify(sqsTemplate, times(1)).send(notificaVideoProcessado, envioJson)
    }
}