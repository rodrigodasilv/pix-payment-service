package com.rodrigodasilv.pixpaymentservice.application.mapper;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.dto.PixKeyType;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Charge;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Key;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Merchant;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Money;
import com.rodrigodasilv.pixpaymentservice.domain.entity.key.CnpjKey;
import com.rodrigodasilv.pixpaymentservice.domain.entity.key.CpfKey;
import com.rodrigodasilv.pixpaymentservice.domain.entity.key.EmailKey;
import com.rodrigodasilv.pixpaymentservice.domain.entity.key.EvpKey;
import com.rodrigodasilv.pixpaymentservice.domain.entity.key.PhoneKey;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;
import org.springframework.stereotype.Component;

@Component
public class ChargeMapper {

    public Charge toCharge(CreateChargeRequest request) {
        if (request == null) {
            throw new InvalidChargeException("Charge request cannot be null");
        }

        return new Charge(
                toKey(request.keyType(), request.key()),
                new Money(request.amount()),
                new Merchant(request.merchantName(), request.merchantCity()),
                request.description()
        );
    }

    private Key toKey(PixKeyType keyType, String value) {
        if (keyType == null) {
            throw new InvalidChargeException("Pix key type cannot be null");
        }

        return switch (keyType) {
            case CPF -> new CpfKey(value);
            case CNPJ -> new CnpjKey(value);
            case EMAIL -> new EmailKey(value);
            case PHONE -> new PhoneKey(value);
            case EVP -> new EvpKey(value);
        };
    }
}