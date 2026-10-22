package com.rodrigodasilv.pixpaymentservice;

import com.rodrigodasilv.pixpaymentservice.application.mapper.ChargeMapper;
import com.rodrigodasilv.pixpaymentservice.application.adapter.Crc16CalculatorAdapter;
import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.dto.PixKeyType;
import com.rodrigodasilv.pixpaymentservice.application.service.PixCopyPasteService;
import com.rodrigodasilv.pixpaymentservice.infrastructure.web.ApiExceptionHandler;
import com.rodrigodasilv.pixpaymentservice.domain.exception.InvalidChargeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PixPaymentServiceApplicationTests {

	@Autowired
	private PixCopyPasteService pixCopyPasteService;

	@Autowired
	private Crc16CalculatorAdapter crc16Calculator;

	@Test
	void generatesPixCopyPasteWithAmountAndValidCrc() {
		CreateChargeRequest request = new CreateChargeRequest(
				PixKeyType.EVP,
				"123e4567-e89b-12d3-a456-426614174000",
				new BigDecimal("12.50"),
				"Example Store",
				"Sao Paulo",
				"Order 42"
		);

		String payload = pixCopyPasteService.generate(request);

		assertTrue(payload.startsWith("000201"));
		assertTrue(payload.contains("540512.50"));
		assertTrue(payload.contains("5913Example Store"));
		assertTrue(payload.contains("62070503***"));
		String crcInput = payload.substring(0, payload.length() - 4);
		assertTrue(payload.endsWith(String.format("6304%04X", crc16Calculator.calculate(crcInput))));
	}

	@Test
	void calculatesCrc16CcittFalseKnownVector() {
		assertEquals(0x29B1, new Crc16CalculatorAdapter().calculate("123456789"));
	}

	@Test
	void returnsBadRequestDetailForInvalidCpf() {
		CreateChargeRequest request = new CreateChargeRequest(
				PixKeyType.CPF,
				"129031449741212312",
				new BigDecimal("51.00"),
				"Example Store",
				"Sao Paulo",
				"Order 42"
		);
		InvalidChargeException exception = assertThrows(
				InvalidChargeException.class,
				() -> new ChargeMapper().toCharge(request)
		);

		var response = new ApiExceptionHandler().handleInvalidCharge(exception);

		assertEquals(400, response.getStatusCode().value());
		assertEquals("Invalid CPF", response.getBody().getDetail());
	}

	@Test
	void returnsBadRequestDetailForAmountWithMoreThanTwoDecimalPlaces() {
		CreateChargeRequest request = new CreateChargeRequest(
				PixKeyType.EVP,
				"123e4567-e89b-12d3-a456-426614174000",
				new BigDecimal("51.0001"),
				"Example Store",
				"Sao Paulo",
				"Order 42"
		);
		InvalidChargeException exception = assertThrows(
				InvalidChargeException.class,
				() -> new ChargeMapper().toCharge(request)
		);

		var response = new ApiExceptionHandler().handleInvalidCharge(exception);

		assertEquals(400, response.getStatusCode().value());
		assertEquals("Amount must have at most two decimal places", response.getBody().getDetail());
	}

}
