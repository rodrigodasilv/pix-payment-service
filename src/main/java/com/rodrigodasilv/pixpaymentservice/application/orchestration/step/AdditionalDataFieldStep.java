package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(5)
@Component
public class AdditionalDataFieldStep implements PixPaymentStep {

    @Override
    public void apply(PixPaymentContext context) {
        String transactionId = context.encodeField(
                PixPayloadField.TRANSACTION_ID,
                PixPayloadField.TRANSACTION_ID.fixedValue());
        context.appendField(PixPayloadField.ADDITIONAL_DATA_FIELD_TEMPLATE, transactionId);
    }
}