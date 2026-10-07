package com.rodrigodasilv.pixpaymentservice.domain.entity.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.Key;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

import java.util.UUID;

public record EvpKey(String value) implements Key {

    public EvpKey {
        if (value == null || value.isBlank()) {
            throw new InvalidChargeException("EVP key cannot be null or blank");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new InvalidChargeException("Invalid EVP key", exception);
        }
    }
}
