package com.rodrigodasilv.pixpaymentservice.domain.entity.pix;


public record Merchant(
        String name,
        String city
) {

    public Merchant {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Merchant name cannot be null or blank");
        }

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("Merchant city cannot be null or blank");
        }
    }
}