package com.leodelmiro.gerencia.entrypoint.api

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status.Companion.fromValor
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorIdUseCase
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.ListaEnviosPorAutorUseCase
import com.leodelmiro.gerencia.entrypoint.api.shared.GlobalControllerAdvice.ErrorResponse
import com.leodelmiro.gerencia.entrypoint.api.utils.TokenDecoder.decodeUsername
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Tag(name = "Envios", description = "Endpoints relacionados aos envios de arquivos do Video")
@RestController
@RequestMapping("/api/v1/envios")
class EnvioApi(
    private val buscaEnvioPorIdUseCase: BuscaEnvioPorIdUseCase,
    private val buscaEnvioPorNomeEAutorUseCase: BuscaEnvioPorNomeEAutorUseCase,
    private val listaEnviosPorAutorUseCase: ListaEnviosPorAutorUseCase
) {

    @Operation(
        summary = "Verifica nome envio existente",
        description = "Verifica se existe Envio por Nome e Autor"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Video recebido com sucesso")])
    @GetMapping("/verifica")
    fun buscaPorNomeAutor(
        @RequestParam("nome") nome: String,
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<Any> {
        return buscaEnvioPorNomeEAutorUseCase.executar(nome, decodeUsername(token))?.let {
            ResponseEntity.ok(true)
        } ?: run {
            ResponseEntity.ok(false)
        }
    }

    @Operation(
        summary = "Consulta Envio por Id",
        description = "Consulta Id de envio"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Video recebido com sucesso")])
    @GetMapping("/{id}")
    fun buscaPorId(
        @PathVariable("id") id: Long,
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
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Video recebido com sucesso")])
    @GetMapping
    fun listarPorAutor(
        @RequestHeader("Authorization") token: String,
        @RequestParam(required = false) status: Int?
    ): List<Envio> {
        return listaEnviosPorAutorUseCase.executar(decodeUsername(token), status?.let { fromValor(status) })
    }
}