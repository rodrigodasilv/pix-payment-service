package com.rodrigodasilv.pixpaymentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Input data used to create a Pix charge payload")
public record CreateChargeRequest(
        @Schema(description = "Pix key type", example = "EVP", requiredMode = Schema.RequiredMode.REQUIRED)
        PixKeyType keyType,
        @Schema(description = "Pix key value matching keyType", example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String key,
        @Schema(description = "Positive amount in BRL", example = "50.00",
                requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal amount,
        @Schema(description = "Merchant display name", example = "Example Store",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String merchantName,
        @Schema(description = "Merchant city", example = "Sao Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
        String merchantCity,
        @Schema(description = "Optional payment description", example = "Order 42")
        String description
) {}