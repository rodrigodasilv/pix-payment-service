package com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.Key;

import java.util.UUID;

public record EvpKey(String value) implements Key {

    public EvpKey {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("EVP key cannot be null or blank");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid EVP key", exception);
        }
    }
}
