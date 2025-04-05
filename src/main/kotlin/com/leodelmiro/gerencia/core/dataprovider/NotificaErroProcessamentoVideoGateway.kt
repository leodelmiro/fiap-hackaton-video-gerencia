package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio


interface NotificaErroProcessamentoVideoGateway {
    fun executar(envio: Envio)
}