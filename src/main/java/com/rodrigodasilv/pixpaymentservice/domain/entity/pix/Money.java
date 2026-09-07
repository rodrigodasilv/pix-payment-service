package com.rodrigodasilv.pixpaymentservice.domain.entity.pix;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (value.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
