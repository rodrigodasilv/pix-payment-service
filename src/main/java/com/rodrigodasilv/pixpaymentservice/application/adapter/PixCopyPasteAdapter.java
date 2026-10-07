package com.rodrigodasilv.pixpaymentservice.application.adapter;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.enums.PixPayloadField;
import com.rodrigodasilv.pixpaymentservice.application.mapper.ChargeMapper;
import com.rodrigodasilv.pixpaymentservice.application.service.Crc16CalculatorService;
import com.rodrigodasilv.pixpaymentservice.application.service.PixCopyPasteService;
import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PixCopyPasteAdapter implements PixCopyPasteService {

    private static final int MAX_FIELD_LENGTH = 99;

    private final ChargeMapper chargeMapper;
    private final Crc16CalculatorService crc16CalculatorService;

    @Override
    @Cacheable(cacheNames = "pixCopyPaste")
    public String generate(CreateChargeRequest request) {
        Charge charge = chargeMapper.toCharge(request);
        return generatePayload(charge);
    }

    private String generatePayload(Charge charge) {
        StringBuilder payload = new StringBuilder();
        appendFixedField(payload, PixPayloadField.PAYLOAD_FORMAT);
        appendMerchantAccountInformation(payload, charge);
        appendFixedField(payload, PixPayloadField.MERCHANT_CATEGORY_CODE);
        appendFixedField(payload, PixPayloadField.CURRENCY_CODE);
        appendAmount(payload, charge);
        appendFixedField(payload, PixPayloadField.COUNTRY_CODE);
        appendField(payload, PixPayloadField.MERCHANT_NAME, charge.getMerchant().name());
        appendField(payload, PixPayloadField.MERCHANT_CITY, charge.getMerchant().city());
        appendAdditionalDataField(payload);

        return appendCrc(payload);
    }

    private void appendAdditionalDataField(StringBuilder payload) {
        StringBuilder additionalData = new StringBuilder();
        appendFixedField(additionalData, PixPayloadField.TRANSACTION_ID);
        appendField(payload, PixPayloadField.ADDITIONAL_DATA_FIELD_TEMPLATE, additionalData.toString());
    }

    private void appendMerchantAccountInformation(StringBuilder payload, Charge charge) {
        StringBuilder merchantAccount = new StringBuilder();
        appendFixedField(merchantAccount, PixPayloadField.PIX_GUI);
        appendField(merchantAccount, PixPayloadField.PIX_KEY, charge.getKey().value());
        if (charge.getDescription() != null && !charge.getDescription().isBlank()) {
            appendField(merchantAccount, PixPayloadField.PIX_DESCRIPTION, charge.getDescription());
        }
        appendField(payload, PixPayloadField.MERCHANT_ACCOUNT_INFORMATION, merchantAccount.toString());
    }

    private void appendAmount(StringBuilder payload, Charge charge) {
        String amount = charge.getAmount().value()
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();
        appendField(payload, PixPayloadField.AMOUNT, amount);
    }

    private String appendCrc(StringBuilder payload) {
        String payloadWithCrcTag = payload.append(PixPayloadField.CRC.id())
                .append(PixPayloadField.CRC.fixedValue())
                .toString();
        String checksum = String.format(Locale.ROOT, "%04X", crc16CalculatorService.calculate(payloadWithCrcTag));
        return payloadWithCrcTag + checksum;
    }

    private void appendFixedField(StringBuilder payload, PixPayloadField field) {
        appendField(payload, field, field.fixedValue());
    }

    private void appendField(StringBuilder payload, PixPayloadField field, String value) {
        int byteLength = value.getBytes(StandardCharsets.UTF_8).length;
        if (byteLength > MAX_FIELD_LENGTH) {
            throw new IllegalArgumentException("Pix field " + field.name() + " exceeds 99 bytes");
        }
        payload.append(field.id())
                .append(String.format(Locale.ROOT, "%02d", byteLength))
                .append(value);
    }
}