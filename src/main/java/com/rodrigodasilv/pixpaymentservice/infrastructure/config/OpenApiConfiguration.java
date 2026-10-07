package com.rodrigodasilv.pixpaymentservice.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pix Payment Service API",
                version = "v1",
                description = "HTTP API for creating Pix copy-and-paste payment payloads."
        )
)
public class OpenApiConfiguration {
}