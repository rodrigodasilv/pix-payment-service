package com.rodrigodasilv.pixpaymentservice.application.dto;

import java.math.BigDecimal;

public record CreateChargeRequest(
        PixKeyType keyType,
        String key,
        BigDecimal amount,
        String merchantName,
        String merchantCity,
        String description
) {
}