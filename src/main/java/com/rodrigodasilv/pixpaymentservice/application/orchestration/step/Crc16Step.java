package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import com.rodrigodasilv.pixpaymentservice.application.service.Crc16CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Order(6)
@Component
@RequiredArgsConstructor
public class Crc16Step implements PixPaymentStep {

    private final Crc16CalculatorService crc16CalculatorService;

    @Override
    public void apply(PixPaymentContext context) {
        context.appendRaw(PixPayloadField.CRC.id() + PixPayloadField.CRC.fixedValue());
        int crc = crc16CalculatorService.calculate(context.payload());
        context.appendRaw(String.format(Locale.ROOT, "%04X", crc));
    }
}