package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status

interface ListaEnviosPorAutorGateway {
    fun executar(autor: String, status: Status?): List<Envio>
}