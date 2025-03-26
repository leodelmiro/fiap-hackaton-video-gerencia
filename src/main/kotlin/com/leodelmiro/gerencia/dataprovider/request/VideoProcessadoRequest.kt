package com.leodelmiro.gerencia.dataprovider.request

import com.leodelmiro.gerencia.core.domain.Envio

data class VideoProcessadoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String,
    val url: String
) {
    fun toArquivo() = Envio(nome = this.nome, descricao = this.descricao, autor = this.autor, url = this.url)
}