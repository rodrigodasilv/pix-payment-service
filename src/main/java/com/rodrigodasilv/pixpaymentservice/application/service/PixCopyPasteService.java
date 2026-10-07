package com.rodrigodasilv.pixpaymentservice.application.service;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

/**
 * Application port for generating a Pix copy-and-paste payload from a charge request.
 */
public interface PixCopyPasteService {

    /**
     * Generates an EMV BR Code payload for the supplied Pix charge details.
     *
     * @param request key, amount, and merchant details for the charge
     * @return the Pix copy-and-paste payload
    * @throws InvalidChargeException if the supplied key or charge details violate domain rules
     */
    String generate(CreateChargeRequest request);
}