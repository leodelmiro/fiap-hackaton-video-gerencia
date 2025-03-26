package com.leodelmiro.gerencia.entrypoint.api

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status.Companion.fromValor
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorIdUseCase
import com.leodelmiro.gerencia.core.usecase.ListaEnviosPorAutorUseCase
import com.leodelmiro.gerencia.entrypoint.api.shared.GlobalControllerAdvice.ErrorResponse
import com.leodelmiro.gerencia.entrypoint.api.utils.TokenDecoder.decodeUsername
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.websocket.server.PathParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Tag(name = "Arquivos", description = "Endpoints relacionados aos envios de arquivos do Video")
@RestController
@RequestMapping("/api/v1/envios")
class EnvioApi(
    private val buscaEnvioPorIdUseCase: BuscaEnvioPorIdUseCase,
    private val listaEnviosPorAutorUseCase: ListaEnviosPorAutorUseCase
) {

    @Operation(
        summary = "Consulta Envio por Id",
        description = "Consulta Id de envio"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "202", description = "Video recebido com sucesso")])
    @PostMapping
    suspend fun buscaPorId(
        @PathParam("id") id: Long,
    ): ResponseEntity<Any> {
        return buscaEnvioPorIdUseCase.executar(id)?.let {
            ResponseEntity.ok(it)
        } ?: run {
            ResponseEntity(ErrorResponse("Not Found", "Id não encontrado"), HttpStatus.NOT_FOUND)
        }
    }

    @Operation(
        summary = "Lista envio por Autor",
        description = "Lista todos os envio de um autor"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "202", description = "Video recebido com sucesso")])
    @PostMapping
    suspend fun listarPorAutor(
        @RequestHeader("Authorization") token: String,
        @RequestParam(required = false) status: Int?
    ): List<Envio> {
        return listaEnviosPorAutorUseCase.executar(decodeUsername(token), status?.let { fromValor(status) })
    }
}