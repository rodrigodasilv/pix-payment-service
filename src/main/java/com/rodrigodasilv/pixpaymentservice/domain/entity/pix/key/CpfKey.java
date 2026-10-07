package com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key;

import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.Key;

public record CpfKey(String value) implements Key {

    public CpfKey {
        if (value == null || !value.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalid CPF");
        }

        if (!isValidCpf(value)) {
            throw new IllegalArgumentException("Invalid CPF check digits");
        }
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(cpf, 9);
        int secondDigit = calculateDigit(cpf, 10);

        return cpf.charAt(9) - '0' == firstDigit
                && cpf.charAt(10) - '0' == secondDigit;
    }

    private static int calculateDigit(String cpf, int length) {
        int sum = 0;
        int weight = length + 1;

        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int remainder = sum % 11;

        return remainder < 2 ? 0 : 11 - remainder;
    }
}
