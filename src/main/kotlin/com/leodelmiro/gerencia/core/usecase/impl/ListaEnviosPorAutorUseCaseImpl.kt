package com.leodelmiro.gerencia.core.usecase.impl

import com.leodelmiro.gerencia.core.dataprovider.ListaEnviosPorAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import com.leodelmiro.gerencia.core.usecase.ListaEnviosPorAutorUseCase


class ListaEnviosPorAutorUseCaseImpl(private val listaEnviosPorAutorGateway: ListaEnviosPorAutorGateway) :
    ListaEnviosPorAutorUseCase {

    override fun executar(autor: String, status: Status?): List<Envio> {
        return listaEnviosPorAutorGateway.executar(autor, status)
    }
}
