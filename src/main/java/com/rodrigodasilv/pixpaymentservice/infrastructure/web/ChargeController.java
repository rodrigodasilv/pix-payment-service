package com.rodrigodasilv.pixpaymentservice.infrastructure.web;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.dto.PixCopyPasteResponse;
import com.rodrigodasilv.pixpaymentservice.application.service.PixCopyPasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charges")
public class ChargeController {

    private final PixCopyPasteService pixCopyPasteService;

    @PostMapping("/generate")
    public PixCopyPasteResponse createPixCopyPaste(@RequestBody CreateChargeRequest request) {
        String pixCopyPaste = pixCopyPasteService.generate(request);
        return new PixCopyPasteResponse(pixCopyPaste);
    }
}