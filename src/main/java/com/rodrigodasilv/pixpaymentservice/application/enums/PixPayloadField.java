package com.rodrigodasilv.pixpaymentservice.application.enums;

public enum PixPayloadField {
    PAYLOAD_FORMAT("00", "01"),
    MERCHANT_ACCOUNT_INFORMATION("26"),
    PIX_GUI("00", "br.gov.bcb.pix"),
    PIX_KEY("01"),
    PIX_DESCRIPTION("02"),
    MERCHANT_CATEGORY_CODE("52", "0000"),
    CURRENCY_CODE("53", "986"),
    AMOUNT("54"),
    COUNTRY_CODE("58", "BR"),
    MERCHANT_NAME("59"),
    MERCHANT_CITY("60"),
    ADDITIONAL_DATA_FIELD_TEMPLATE("62"),
    TRANSACTION_ID("05", "***"),
    CRC("63", "04");

    private final String id;
    private final String fixedValue;

    PixPayloadField(String id) {
        this(id, null);
    }

    PixPayloadField(String id, String fixedValue) {
        this.id = id;
        this.fixedValue = fixedValue;
    }

    public String id() {
        return id;
    }

    public String fixedValue() {
        return fixedValue;
    }
}