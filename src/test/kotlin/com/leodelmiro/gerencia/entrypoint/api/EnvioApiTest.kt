package com.leodelmiro.gerencia.entrypoint.api

import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorIdUseCase
import com.leodelmiro.gerencia.core.usecase.BuscaEnvioPorNomeEAutorUseCase
import com.leodelmiro.gerencia.core.usecase.ListaEnviosPorAutorUseCase
import com.leodelmiro.gerencia.entrypoint.api.shared.GlobalControllerAdvice.ErrorResponse
import com.leodelmiro.gerencia.entrypoint.api.utils.TokenDecoder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import utils.criaEnvio

class EnvioApiTest {

    private val buscaEnvioPorIdUseCase = mock(BuscaEnvioPorIdUseCase::class.java)
    private val buscaEnvioPorNomeEAutorUseCase = mock(BuscaEnvioPorNomeEAutorUseCase::class.java)
    private val listaEnviosPorAutorUseCase = mock(ListaEnviosPorAutorUseCase::class.java)
    private val envioApi = EnvioApi(buscaEnvioPorIdUseCase, buscaEnvioPorNomeEAutorUseCase, listaEnviosPorAutorUseCase)

    @Test
    fun `deve retornar true quando envio for encontrado por nome e autor`() {
        val nome = "envio1"
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.jvCvbPXc27QMKIqwwT9QpkY7iD0Nw6XRe1mAqJ-CgJw"
        val username = "testuser"
        `when`(buscaEnvioPorNomeEAutorUseCase.executar(nome, username)).thenReturn(criaEnvio(id = 1))

        val response = envioApi.buscaPorNomeAutor(nome, token)

        assertEquals(ResponseEntity.ok(true), response)
    }

    @Test
    fun `deve retornar false quando envio nao for encontrado por nome e autor`() {
        val nome = "envio1"
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.jvCvbPXc27QMKIqwwT9QpkY7iD0Nw6XRe1mAqJ-CgJw"
        val username = "testuser"
        `when`(buscaEnvioPorNomeEAutorUseCase.executar(nome, username)).thenReturn(null)

        val response = envioApi.buscaPorNomeAutor(nome, token)

        assertEquals(ResponseEntity.ok(false), response)
    }

    @Test
    fun `deve retornar envio quando encontrado por id`() {
        val id = 1L
        val envio = criaEnvio(id = id)
        `when`(buscaEnvioPorIdUseCase.executar(id)).thenReturn(envio)

        val response = envioApi.buscaPorId(id)

        assertEquals(ResponseEntity.ok(envio), response)
    }

    @Test
    fun `deve retornar erro 404 quando envio nao for encontrado por id`() {
        val id = 1L
        `when`(buscaEnvioPorIdUseCase.executar(id)).thenReturn(null)

        val response = envioApi.buscaPorId(id)

        assertEquals(ResponseEntity(ErrorResponse("Not Found", "Id não encontrado"), HttpStatus.NOT_FOUND), response)
    }

    @Test
    fun `deve listar envios por autor`() {
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.jvCvbPXc27QMKIqwwT9QpkY7iD0Nw6XRe1mAqJ-CgJw"
        val username = "testuser"
        val envios = listOf(criaEnvio(), criaEnvio())
        `when`(listaEnviosPorAutorUseCase.executar(username, null)).thenReturn(envios)

        val response = envioApi.listarPorAutor(token, null)

        assertEquals(envios, response)
    }
}