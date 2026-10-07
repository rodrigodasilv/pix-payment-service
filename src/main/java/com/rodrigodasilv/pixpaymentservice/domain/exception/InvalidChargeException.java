package com.rodrigodasilv.pixpaymentservice.domain.exception;

/**
 * Indicates that supplied charge data violates a domain or Pix payload constraint.
 * The HTTP boundary exposes this exception as a client-facing 400 Problem Detail.
 */
public class InvalidChargeException extends RuntimeException {

    public InvalidChargeException(String message) {
        super(message);
    }

    public InvalidChargeException(String message, Throwable cause) {
        super(message, cause);
    }
}