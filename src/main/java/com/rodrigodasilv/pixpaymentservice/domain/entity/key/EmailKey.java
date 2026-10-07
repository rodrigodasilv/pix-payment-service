package com.rodrigodasilv.pixpaymentservice.domain.entity.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.Key;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

public record EmailKey(String value) implements Key {

    private static final int MAX_LENGTH = 77;

    public EmailKey {
        if (value == null || value.isBlank()) {
            throw new InvalidChargeException("Email cannot be null or blank");
        }

        if (value.length() > MAX_LENGTH) {
            throw new InvalidChargeException("Email must have at most 77 characters");
        }

        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new InvalidChargeException("Invalid email");
        }
    }
}
