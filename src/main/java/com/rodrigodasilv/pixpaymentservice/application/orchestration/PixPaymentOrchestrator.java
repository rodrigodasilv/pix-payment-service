package com.rodrigodasilv.pixpaymentservice.application.orchestration;

import com.rodrigodasilv.pixpaymentservice.domain.entity.Charge;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PixPaymentOrchestrator {

    private final List<PixPaymentStep> steps;

    /**
     * Runs the Spring-ordered payload steps for a charge.
     *
     * @param charge domain charge to encode
     * @return the complete Pix BR Code payload, including its CRC
    * @throws InvalidChargeException if a charge field cannot be encoded in the Pix payload
     */
    public String generate(Charge charge) {
        PixPaymentContext context = new PixPaymentContext(charge);
        steps.forEach(step -> step.apply(context));
        return context.payload();
    }
}