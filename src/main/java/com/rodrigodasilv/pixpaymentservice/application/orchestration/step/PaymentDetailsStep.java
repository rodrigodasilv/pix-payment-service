package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Order(3)
@Component
public class PaymentDetailsStep implements PixPaymentStep {

    @Override
    public void apply(PixPaymentContext context) {
        context.appendField(PixPayloadField.MERCHANT_CATEGORY_CODE,
                PixPayloadField.MERCHANT_CATEGORY_CODE.fixedValue());
        context.appendField(PixPayloadField.CURRENCY_CODE, PixPayloadField.CURRENCY_CODE.fixedValue());
        context.appendField(PixPayloadField.AMOUNT, context.charge().amount().value()
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString());
        context.appendField(PixPayloadField.COUNTRY_CODE, PixPayloadField.COUNTRY_CODE.fixedValue());
    }
}