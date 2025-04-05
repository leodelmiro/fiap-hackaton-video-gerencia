package com.leodelmiro.gerencia.core.usecase.impl

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorIdGateway
import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorNomeEAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorIdUseCase
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase


class BuscaEnvioPorIdUseCaseImpl(private val buscaEnvioPorIdGateway: BuscaEnvioPorIdGateway) :
    BuscaEnvioPorIdUseCase {

    override fun executar(id: Long): Envio? {
        return buscaEnvioPorIdGateway.executar(id)
    }
}
