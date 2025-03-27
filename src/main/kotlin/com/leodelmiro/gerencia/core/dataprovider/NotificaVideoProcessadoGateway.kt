package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio


interface NotificaVideoProcessadoGateway {
    fun executar(envio: Envio)
}