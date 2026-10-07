package com.rodrigodasilv.pixpaymentservice.domain.entity;

import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null) {
            throw new InvalidChargeException("Amount cannot be null");
        }

        if (value.signum() <= 0) {
            throw new InvalidChargeException("Amount must be greater than zero");
        }

        if (value.stripTrailingZeros().scale() > 2) {
            throw new InvalidChargeException("Amount must have at most two decimal places");
        }
    }
}
