package com.leodelmiro.gerencia.dataprovider.repository

import com.leodelmiro.gerencia.dataprovider.repository.entity.EnvioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EnvioRepository : JpaRepository<EnvioEntity, Long> {
    @Query("SELECT e FROM EnvioEntity e WHERE e.nome = :nome AND e.autor = :autor")
    fun buscarAquivoPorNomeEAutor(@Param("nome") nome: String, @Param("autor") autor: String): EnvioEntity?

    @Query("SELECT e FROM EnvioEntity e WHERE e.autor = :autor AND (:status IS NULL OR e.status = :status)")
    fun listarAquivosPorAutor(@Param("autor") autor: String, @Param("status") status: Int?): List<EnvioEntity>
}