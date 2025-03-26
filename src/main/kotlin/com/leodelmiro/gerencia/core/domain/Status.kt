package com.leodelmiro.gerencia.core.domain

enum class Status(val valor: Int) {
    EM_PROCESSAMENTO(1), PROCESSADO(2), ERRO(3);

    companion object {
        fun fromValor(valor: Int): Status? {
            return entries.firstOrNull { it.valor == valor }
        }
    }
}
