package com.rodrigodasilv.pixpaymentservice.infrastructure.web;

import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidChargeException.class)
    public ResponseEntity<ProblemDetail> handleInvalidCharge(InvalidChargeException exception) {
        String detail = exception.getMessage() == null ? "Invalid request" : exception.getMessage();
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problem.setTitle("Invalid request");
        return ResponseEntity.badRequest().body(problem);
    }
}