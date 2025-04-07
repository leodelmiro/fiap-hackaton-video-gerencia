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
            "Bearer eyJraWQiOiJQbXZWWlBPT1wvbWZuS3lNUlBpU1NJVXNQOUFINnVZRUF5WjRCQ0tMOEVsTT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NGY4NzQyOC04MDQxLTcwNjEtZGIyMy05YmU3NWZmNmZhY2QiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9qZ0NUWW9TaDYiLCJjb2duaXRvOnVzZXJuYW1lIjoiam9obmRvZSIsIm9yaWdpbl9qdGkiOiIyMzE1MGU2NS05OGUxLTQxNDMtOTgxYS02OTQ1ODQzYzQwZjkiLCJhdWQiOiIzZDRhZm40dmpsbDg5N3YyaG5iYTFrc3NmbiIsImV2ZW50X2lkIjoiOWQxNTAzNmItNjEzMy00MzE4LWFlYTAtMmNjNzlkYWIwMWVjIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3NDQwNDk4ODUsImV4cCI6MTc0NDA1MzQ4NSwiaWF0IjoxNzQ0MDQ5ODg1LCJqdGkiOiI5OGVjNjQyYy0yM2NlLTQwMjYtYjUyYy0wMTZmMzQ0YmU2YzYiLCJlbWFpbCI6ImpvaG5kb2VAZXhhbXBsZS5jb20ifQ.AvH9oDEXTHwGfkvIgAOy4_rdxiZkQvH10B3qWCNBZ_PAoW1ZET_TsSGsN3byRj53nFwIIK961oiGfh1aQhjp44sSX8Yapt1kyeKE50QptOBJRQNCGLBbc2BSLuivVsJdFGtHREPqST3KUONfWlEfDx6BQnw2xIgcIGcHN-7LyzM2rlu0Zbgmy-yHkMVOUx5HjZBKEBsEbJ1-vo1mA00BjCZIf-0rLFaO1a_o66H8bcL3nI-o8pC2S3OJ_8CnnG0O-ELK64loh71exeDFgmOK7SEgikZ5mXaQH2YGiosG74L2lNQcoBVTnvsm8vzN8_s5g-y9q03erqGsJfWPeJfEEw"
        val username = "johndoe"
        `when`(buscaEnvioPorNomeEAutorUseCase.executar(nome, username)).thenReturn(criaEnvio(id = 1))

        val response = envioApi.buscaPorNomeAutor(nome, token)

        assertEquals(ResponseEntity.ok(true), response)
    }

    @Test
    fun `deve retornar false quando envio nao for encontrado por nome e autor`() {
        val nome = "envio1"
        val token =
            "Bearer eyJraWQiOiJQbXZWWlBPT1wvbWZuS3lNUlBpU1NJVXNQOUFINnVZRUF5WjRCQ0tMOEVsTT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NGY4NzQyOC04MDQxLTcwNjEtZGIyMy05YmU3NWZmNmZhY2QiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9qZ0NUWW9TaDYiLCJjb2duaXRvOnVzZXJuYW1lIjoiam9obmRvZSIsIm9yaWdpbl9qdGkiOiIyMzE1MGU2NS05OGUxLTQxNDMtOTgxYS02OTQ1ODQzYzQwZjkiLCJhdWQiOiIzZDRhZm40dmpsbDg5N3YyaG5iYTFrc3NmbiIsImV2ZW50X2lkIjoiOWQxNTAzNmItNjEzMy00MzE4LWFlYTAtMmNjNzlkYWIwMWVjIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3NDQwNDk4ODUsImV4cCI6MTc0NDA1MzQ4NSwiaWF0IjoxNzQ0MDQ5ODg1LCJqdGkiOiI5OGVjNjQyYy0yM2NlLTQwMjYtYjUyYy0wMTZmMzQ0YmU2YzYiLCJlbWFpbCI6ImpvaG5kb2VAZXhhbXBsZS5jb20ifQ.AvH9oDEXTHwGfkvIgAOy4_rdxiZkQvH10B3qWCNBZ_PAoW1ZET_TsSGsN3byRj53nFwIIK961oiGfh1aQhjp44sSX8Yapt1kyeKE50QptOBJRQNCGLBbc2BSLuivVsJdFGtHREPqST3KUONfWlEfDx6BQnw2xIgcIGcHN-7LyzM2rlu0Zbgmy-yHkMVOUx5HjZBKEBsEbJ1-vo1mA00BjCZIf-0rLFaO1a_o66H8bcL3nI-o8pC2S3OJ_8CnnG0O-ELK64loh71exeDFgmOK7SEgikZ5mXaQH2YGiosG74L2lNQcoBVTnvsm8vzN8_s5g-y9q03erqGsJfWPeJfEEw"
        val username = "johndoe"
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
            "Bearer eyJraWQiOiJQbXZWWlBPT1wvbWZuS3lNUlBpU1NJVXNQOUFINnVZRUF5WjRCQ0tMOEVsTT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NGY4NzQyOC04MDQxLTcwNjEtZGIyMy05YmU3NWZmNmZhY2QiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9qZ0NUWW9TaDYiLCJjb2duaXRvOnVzZXJuYW1lIjoiam9obmRvZSIsIm9yaWdpbl9qdGkiOiIyMzE1MGU2NS05OGUxLTQxNDMtOTgxYS02OTQ1ODQzYzQwZjkiLCJhdWQiOiIzZDRhZm40dmpsbDg5N3YyaG5iYTFrc3NmbiIsImV2ZW50X2lkIjoiOWQxNTAzNmItNjEzMy00MzE4LWFlYTAtMmNjNzlkYWIwMWVjIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3NDQwNDk4ODUsImV4cCI6MTc0NDA1MzQ4NSwiaWF0IjoxNzQ0MDQ5ODg1LCJqdGkiOiI5OGVjNjQyYy0yM2NlLTQwMjYtYjUyYy0wMTZmMzQ0YmU2YzYiLCJlbWFpbCI6ImpvaG5kb2VAZXhhbXBsZS5jb20ifQ.AvH9oDEXTHwGfkvIgAOy4_rdxiZkQvH10B3qWCNBZ_PAoW1ZET_TsSGsN3byRj53nFwIIK961oiGfh1aQhjp44sSX8Yapt1kyeKE50QptOBJRQNCGLBbc2BSLuivVsJdFGtHREPqST3KUONfWlEfDx6BQnw2xIgcIGcHN-7LyzM2rlu0Zbgmy-yHkMVOUx5HjZBKEBsEbJ1-vo1mA00BjCZIf-0rLFaO1a_o66H8bcL3nI-o8pC2S3OJ_8CnnG0O-ELK64loh71exeDFgmOK7SEgikZ5mXaQH2YGiosG74L2lNQcoBVTnvsm8vzN8_s5g-y9q03erqGsJfWPeJfEEw"
        val username = "johndoe"
        val envios = listOf(criaEnvio(), criaEnvio())
        `when`(listaEnviosPorAutorUseCase.executar(username, null)).thenReturn(envios)

        val response = envioApi.listarPorAutor(token, null)

        assertEquals(envios, response)
    }
}