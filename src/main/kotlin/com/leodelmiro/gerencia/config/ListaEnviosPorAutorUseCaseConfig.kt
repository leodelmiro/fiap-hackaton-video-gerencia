package com.leodelmiro.gerencia.config

import com.leodelmiro.gerencia.core.dataprovider.ListaEnviosPorAutorGateway
import com.leodelmiro.gerencia.core.usecase.impl.ListaEnviosPorAutorUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ListaEnviosPorAutorUseCaseConfig {
    @Bean
    fun listaArquivosPorAutorUseCase(
        listaEnviosPorAutorGateway: ListaEnviosPorAutorGateway
    ): ListaEnviosPorAutorUseCaseImpl {
        return ListaEnviosPorAutorUseCaseImpl(listaEnviosPorAutorGateway)
    }
}