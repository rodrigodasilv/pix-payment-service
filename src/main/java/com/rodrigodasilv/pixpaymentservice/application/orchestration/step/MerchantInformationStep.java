package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(4)
@Component
public class MerchantInformationStep implements PixPaymentStep {

    @Override
    public void apply(PixPaymentContext context) {
        context.appendField(PixPayloadField.MERCHANT_NAME, context.charge().merchant().name());
        context.appendField(PixPayloadField.MERCHANT_CITY, context.charge().merchant().city());
    }
}