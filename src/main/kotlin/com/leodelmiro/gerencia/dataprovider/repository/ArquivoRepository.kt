package com.leodelmiro.gerencia.dataprovider.repository

import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ArquivoRepository : JpaRepository<EnvioEntity, Long> {
    @Query("SELECT a FROM ArquivoEntity a WHERE a.nome = :nome AND a.autor = :autor")
    fun buscarAquivoPorNomeEAutor(@Param("nome") nome: String, @Param("autor") autor: String): EnvioEntity?

    @Query("SELECT a FROM ArquivoEntity a WHERE a.autor = :autor AND (:status IS NULL OR a.status = :status)")
    fun listarAquivosPorAutor(@Param("autor") autor: String, @Param("status") status: Int?): List<EnvioEntity>
}