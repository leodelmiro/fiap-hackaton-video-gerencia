package com.leodelmiro.gerencia.core.usecase.impl

import com.leodelmiro.gerencia.core.dataprovider.BuscaEnvioPorNomeEAutorGateway
import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase


class BuscaEnvioPorNomeEAutorUseCaseImpl(private val buscaEnvioPorNomeEAutorGateway: BuscaEnvioPorNomeEAutorGateway) :
    BuscaEnvioPorNomeEAutorUseCase {

    override fun executar(nome: String, autor: String): Envio? {
        return buscaEnvioPorNomeEAutorGateway.executar(nome, autor)
    }
}
