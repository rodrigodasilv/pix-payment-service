package com.rodrigodasilv.pixpaymentservice.application.service;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;

public interface PixCopyPasteService {
    String generate(CreateChargeRequest request);
}