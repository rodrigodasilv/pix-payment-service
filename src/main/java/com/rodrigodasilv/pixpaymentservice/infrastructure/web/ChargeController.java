package com.rodrigodasilv.pixpaymentservice.infrastructure.web;

import com.rodrigodasilv.pixpaymentservice.application.dto.CreateChargeRequest;
import com.rodrigodasilv.pixpaymentservice.application.dto.PixCopyPasteResponse;
import com.rodrigodasilv.pixpaymentservice.application.service.PixCopyPasteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charges")
@Tag(name = "Pix charges", description = "Operations for generating Pix copy-and-paste payloads")
public class ChargeController {

    private final PixCopyPasteService pixCopyPasteService;

    @PostMapping("/generate")
        @Operation(
            operationId = "generatePixCopyPaste",
            summary = "Generate a Pix copy-and-paste payload",
            description = "Builds a charge from the supplied Pix key, amount, and merchant details, then returns an EMV BR Code payload."
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pix payload generated"),
            @ApiResponse(
                responseCode = "400",
                description = "Request body is malformed or contains invalid charge data",
                content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                        name = "Invalid CPF",
                        summary = "CPF validation failed",
                        value = """
                            {
                              "detail": "Invalid CPF",
                              "instance": "/api/charges/generate",
                              "status": 400,
                              "title": "Invalid request"
                            }
                            """
                    )
                )
            )
        })
    public PixCopyPasteResponse createPixCopyPaste(@RequestBody CreateChargeRequest request) {
        String pixCopyPaste = pixCopyPasteService.generate(request);
        return new PixCopyPasteResponse(pixCopyPaste);
    }
}