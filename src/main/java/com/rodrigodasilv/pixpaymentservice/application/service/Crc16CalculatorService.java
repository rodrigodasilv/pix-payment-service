package com.rodrigodasilv.pixpaymentservice.application.service;

/**
 * Application port for calculating the CRC-16 checksum used by Pix BR Code payloads.
 */
public interface Crc16CalculatorService {

    /**
     * Calculates the CRC-16/CCITT-FALSE checksum of a UTF-8 value.
     *
     * @param value input to checksum
     * @return the unsigned 16-bit checksum as an integer
     */
    int calculate(String value);
}