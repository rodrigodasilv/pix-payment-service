package com.rodrigodasilv.pixpaymentservice.domain.entity;

import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

public record Merchant(
        String name,
        String city
) {

    public Merchant {
        if (name == null || name.isBlank()) {
            throw new InvalidChargeException("Merchant name cannot be null or blank");
        }

        if (city == null || city.isBlank()) {
            throw new InvalidChargeException("Merchant city cannot be null or blank");
        }
    }
}