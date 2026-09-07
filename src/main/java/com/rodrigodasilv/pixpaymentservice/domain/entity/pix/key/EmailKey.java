package com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key;

public record EmailKey(String value) implements Key {

    private static final int MAX_LENGTH = 77;

    public EmailKey {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Email must have at most 77 characters");
        }

        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
