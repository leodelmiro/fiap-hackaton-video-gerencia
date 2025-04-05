package com.leodelmiro.gerencia.core.usecase

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status

interface ListaEnviosPorAutorUseCase {
    fun executar(autor: String, status: Status?): List<Envio>
}