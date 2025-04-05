package com.leodelmiro.gerencia.core.dataprovider

import com.leodelmiro.gerencia.core.domain.Envio

interface BuscaEnvioPorNomeEAutorGateway {
    fun executar(nome: String, autor: String): Envio?
}