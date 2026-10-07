package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class PayloadFormatStep implements PixPaymentStep {

    @Override
    public void apply(PixPaymentContext context) {
        context.appendField(PixPayloadField.PAYLOAD_FORMAT, PixPayloadField.PAYLOAD_FORMAT.fixedValue());
    }
}