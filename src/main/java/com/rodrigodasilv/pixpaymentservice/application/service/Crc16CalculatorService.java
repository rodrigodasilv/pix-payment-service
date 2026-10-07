package com.rodrigodasilv.pixpaymentservice.application.service;

public interface Crc16CalculatorService {
    int calculate(String value);
}