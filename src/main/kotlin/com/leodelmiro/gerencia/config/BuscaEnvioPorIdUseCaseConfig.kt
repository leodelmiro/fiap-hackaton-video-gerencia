package com.leodelmiro.gerencia.config

import com.leodelmiro.gerencia.core.usecase.impl.BuscaEnvioPorIdUseCaseImpl
import com.leodelmiro.gerencia.dataprovider.gateway.BuscaEnvioPorIdGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BuscaEnvioPorIdUseCaseConfig {
    @Bean
    fun buscaEnvioPorIdUseCase(
        buscaEnvioPorIdGateway: BuscaEnvioPorIdGatewayImpl
    ): BuscaEnvioPorIdUseCaseImpl {
        return BuscaEnvioPorIdUseCaseImpl(buscaEnvioPorIdGateway)
    }
}