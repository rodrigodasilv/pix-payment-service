package com.rodrigodasilv.pixpaymentservice.application.adapter;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.mapper.ChargeMapper;
import com.rodrigodasilv.pixpaymentservice.application.orchestration.PixPaymentOrchestrator;
import com.rodrigodasilv.pixpaymentservice.application.service.PixCopyPasteService;
import com.rodrigodasilv.pixpaymentservice.domain.entity.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PixCopyPasteAdapter implements PixCopyPasteService {

    private final ChargeMapper chargeMapper;
    private final PixPaymentOrchestrator pixPaymentOrchestrator;

    @Override
    @Cacheable(cacheNames = "pixCopyPaste")
    public String generate(CreateChargeRequest request) {
        Charge charge = chargeMapper.toCharge(request);
        return pixPaymentOrchestrator.generate(charge);
    }
}