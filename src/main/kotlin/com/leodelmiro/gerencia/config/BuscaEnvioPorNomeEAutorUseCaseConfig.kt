package com.leodelmiro.gerencia.config

import com.leodelmiro.gerencia.core.usecase.impl.BuscaEnvioPorNomeEAutorUseCaseImpl
import com.leodelmiro.gerencia.dataprovider.gateway.BuscaEnvioPorNomeEAutorGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BuscaEnvioPorNomeEAutorUseCaseConfig {
    @Bean
    fun buscaEnvioPorNomeEAutorUseCase(
        buscaEnvioPorNomeEAutorGateway: BuscaEnvioPorNomeEAutorGatewayImpl
    ): BuscaEnvioPorNomeEAutorUseCaseImpl {
        return BuscaEnvioPorNomeEAutorUseCaseImpl(buscaEnvioPorNomeEAutorGateway)
    }
}