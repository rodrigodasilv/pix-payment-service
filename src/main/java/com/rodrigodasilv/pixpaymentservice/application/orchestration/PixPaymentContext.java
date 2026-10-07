package com.rodrigodasilv.pixpaymentservice.application.orchestration;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Charge;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

public final class PixPaymentContext {

    private static final int MAX_FIELD_LENGTH = 99;

    private final Charge charge;
    private final StringBuilder payload = new StringBuilder();

    public PixPaymentContext(Charge charge) {
        this.charge = charge;
    }

    public Charge charge() {
        return charge;
    }

    public void appendField(PixPayloadField field, String value) {
        payload.append(encodeField(field, value));
    }

    public String encodeField(PixPayloadField field, String value) {
        int byteLength = value.getBytes(StandardCharsets.UTF_8).length;
        if (byteLength > MAX_FIELD_LENGTH) {
            throw new InvalidChargeException("Pix field " + field.name() + " exceeds 99 bytes");
        }
        return field.id() + String.format(Locale.ROOT, "%02d", byteLength) + value;
    }

    public String payload() {
        return payload.toString();
    }

    public void appendRaw(String value) {
        payload.append(value);
    }
}