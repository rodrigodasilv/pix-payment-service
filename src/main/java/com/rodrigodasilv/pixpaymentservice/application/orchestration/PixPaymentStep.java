package com.rodrigodasilv.pixpaymentservice.application.orchestration;

import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;

/**
 * One ordered operation in the construction of a Pix BR Code payload.
 */
public interface PixPaymentStep {

    /**
     * Applies this payload-generation operation to the shared context.
     *
     * @param context charge data and the payload assembled by preceding steps
    * @throws InvalidChargeException if charge data cannot be represented in this payload step
     */
    void apply(PixPaymentContext context);
}