package com.rodrigodasilv.pixpaymentservice.domain.entity;

public record Charge(
        Key key,
        Money amount,
        Merchant merchant,
        String description
) {}
