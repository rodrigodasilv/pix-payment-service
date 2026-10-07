package com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.Key;

public record CnpjKey(String value) implements Key {

    public CnpjKey {
        if (value == null || !value.matches("\\d{14}")) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }

        if (!isValidCnpj(value)) {
            throw new IllegalArgumentException("Invalid CNPJ check digits");
        }
    }

    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(cnpj, 12);
        int secondDigit = calculateDigit(cnpj, 13);

        return cnpj.charAt(12) - '0' == firstDigit
                && cnpj.charAt(13) - '0' == secondDigit;
    }

    private static int calculateDigit(String cnpj, int length) {
        int[] weights = length == 12
                ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
                : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }

        int remainder = sum % 11;

        return remainder < 2 ? 0 : 11 - remainder;
    }
}
