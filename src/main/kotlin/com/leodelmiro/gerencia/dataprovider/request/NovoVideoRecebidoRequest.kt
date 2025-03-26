package com.leodelmiro.gerencia.dataprovider.request

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status

data class NovoVideoRecebidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
) {
    fun toArquivo() =
        Envio(nome = this.nome, status = Status.EM_PROCESSAMENTO, descricao = this.descricao, autor = this.autor)
}