package com.rodrigodasilv.pixpaymentservice;

import com.rodrigodasilv.pixpaymentservice.application.mapper.ChargeMapper;
import com.rodrigodasilv.pixpaymentservice.application.adapter.Crc16CalculatorAdapter;
import com.rodrigodasilv.pixpaymentservice.application.adapter.PixCopyPasteAdapter;
import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.dto.PixKeyType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PixPaymentServiceApplicationTests {

	@Test
	void generatesPixCopyPasteWithAmountAndValidCrc() {
		PixCopyPasteAdapter service = new PixCopyPasteAdapter(
				new ChargeMapper(),
				new Crc16CalculatorAdapter()
		);
		CreateChargeRequest request = new CreateChargeRequest(
				PixKeyType.EVP,
				"123e4567-e89b-12d3-a456-426614174000",
				new BigDecimal("12.50"),
				"Example Store",
				"Sao Paulo",
				"Order 42"
		);

		String payload = service.generate(request);

		assertTrue(payload.contains("540512.50"));
		assertTrue(payload.contains("5913Example Store"));
		assertTrue(payload.contains("62070503***6304"));
		assertEquals(
				String.format("%04X", new Crc16CalculatorAdapter().calculate(payload.substring(0, payload.length() - 4))),
				payload.substring(payload.length() - 4)
		);
	}

	@Test
	void calculatesCrc16CcittFalseKnownVector() {
		assertEquals(0x29B1, new Crc16CalculatorAdapter().calculate("123456789"));
	}

}
