package com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.Key;

public record PhoneKey(String value) implements Key {

    public PhoneKey {
        if (value == null || !value.matches("^\\+[1-9]\\d{7,14}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
