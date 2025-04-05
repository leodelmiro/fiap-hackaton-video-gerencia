package com.leodelmiro.gerencia.core.usecase

import com.leodelmiro.gerencia.core.domain.Envio

interface BuscaEnvioPorNomeEAutorUseCase {
    fun executar(nome: String, autor: String): Envio?
}