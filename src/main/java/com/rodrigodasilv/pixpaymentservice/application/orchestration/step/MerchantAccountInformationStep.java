package com.rodrigodasilv.pixpaymentservice.application.orchestration.step;

import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentContext;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class MerchantAccountInformationStep implements PixPaymentStep {

    @Override
    public void apply(PixPaymentContext context) {
        StringBuilder merchantAccount = new StringBuilder();
        merchantAccount.append(context.encodeField(PixPayloadField.PIX_GUI, PixPayloadField.PIX_GUI.fixedValue()));
        merchantAccount.append(context.encodeField(PixPayloadField.PIX_KEY, context.charge().key().value()));

        String description = context.charge().description();
        if (description != null && !description.isBlank()) {
            merchantAccount.append(context.encodeField(PixPayloadField.PIX_DESCRIPTION, description));
        }

        context.appendField(PixPayloadField.MERCHANT_ACCOUNT_INFORMATION, merchantAccount.toString());
    }
}