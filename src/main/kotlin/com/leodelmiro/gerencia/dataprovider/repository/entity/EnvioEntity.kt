package com.leodelmiro.gerencia.dataprovider.repository.entity

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "tb_envio")
data class EnvioEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private var id: Long? = null,
    private val nome: String = "",
    private val status: Status = Status.EM_PROCESSAMENTO,
    private val descricao: String = "",
    private val autor: String = "",
    private val url: String? = null,
    @CreationTimestamp
    private var criadoEm: LocalDateTime? = null
) {
    constructor(envio: Envio) : this(
        envio.id,
        envio.nome,
        envio.status,
        envio.descricao,
        envio.autor,
        envio.url,
        envio.criadoEm
    )

    fun toEnvio() = Envio(
        this.id,
        this.nome,
        this.status,
        this.descricao,
        this.autor,
        this.url,
        this.criadoEm
    )
}