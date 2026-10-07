package com.rodrigodasilv.pixpaymentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generated Pix BR Code payload")
public record PixCopyPasteResponse(
        String pixCopyPaste
) {}