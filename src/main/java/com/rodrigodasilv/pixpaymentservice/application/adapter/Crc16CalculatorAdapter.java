package com.rodrigodasilv.pixpaymentservice.application.adapter;

import com.rodrigodasilv.pixpaymentservice.application.service.Crc16CalculatorService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class Crc16CalculatorAdapter implements Crc16CalculatorService {

    @Override
    public int calculate(String value) {
        int crc = 0xFFFF;
        for (byte currentByte : value.getBytes(StandardCharsets.UTF_8)) {
            crc ^= (currentByte & 0xFF) << 8;
            for (int bit = 0; bit < 8; bit++) {
                crc = (crc & 0x8000) != 0 ? (crc << 1) ^ 0x1021 : crc << 1;
                crc &= 0xFFFF;
            }
        }
        return crc;
    }
}