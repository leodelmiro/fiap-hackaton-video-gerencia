package com.leodelmiro.gerencia.config

import com.leodelmiro.gerencia.core.usecase.impl.SalvaEnvioUseCaseImpl
import com.leodelmiro.gerencia.dataprovider.gateway.SalvaEnvioGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SalvaEnvioUseCaseConfig {
    @Bean
    fun salvaEnvioUseCase(salvaArquivoGateway: SalvaEnvioGatewayImpl): SalvaEnvioUseCaseImpl {
        return SalvaEnvioUseCaseImpl(salvaArquivoGateway)
    }
}