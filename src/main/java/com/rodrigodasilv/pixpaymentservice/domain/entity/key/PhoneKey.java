package com.rodrigodasilv.pixpaymentservice.domain.entity.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.Key;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

public record PhoneKey(String value) implements Key {

    public PhoneKey {
        if (value == null || !value.matches("^\\+[1-9]\\d{7,14}$")) {
            throw new InvalidChargeException("Invalid phone number");
        }
    }
}
