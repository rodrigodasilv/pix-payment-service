# Pix Payment Service

High-throughput Pix copy-and-paste generator API built with Spring Boot, stateless request processing, DDD-inspired domain modeling, hexagonal architecture, the Pipeline design pattern, CRC-16 validation, and Caffeine caching.

## Requirements

- Java 21
- Maven Wrapper included in the repository

## Run

On Windows:

```powershell
./mvnw.cmd spring-boot:run
```

On Linux or macOS:

```shell
./mvnw spring-boot:run
```

The service listens on `http://localhost:8080` by default.

## API

Swagger UI is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). The generated OpenAPI JSON is available at [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

Generate a payload:

```http
POST /api/charges/generate
Content-Type: application/json
```

```json
{
  "keyType": "EVP",
  "key": "123e4567-e89b-12d3-a456-426614174000",
  "amount": 50.00,
  "merchantName": "Example Store",
  "merchantCity": "Sao Paulo",
  "description": "Order 42"
}
```

The response contains the generated payload:

```json
{
  "pixCopyPaste": "000201..."
}
```

Invalid charge data raises a domain `InvalidChargeException` and returns HTTP `400` as a Problem Detail response. For example, an invalid CPF returns:

```json
{
  "detail": "Invalid CPF",
  "instance": "/api/charges/generate",
  "status": 400,
  "title": "Invalid request"
}
```

Supported key types are `CPF`, `CNPJ`, `EMAIL`, `PHONE`, and `EVP`. The selected key value must pass the corresponding domain validation. The amount must be positive and representable with two decimal places. A static transaction ID (`***`) is written to the BR Code additional data field.

## Architecture

The code uses DDD-oriented modeling and a hexagonal, ports-and-adapters structure around the charge-generation use case.

- The domain package owns `Charge`, `Money`, `Merchant`, and typed Pix key value objects. Constructors enforce domain invariants such as valid key shape and positive amounts.
- `PixCopyPasteService` is the application-facing port used by the HTTP controller. `PixCopyPasteAdapter` implements the port, maps the request DTO to the domain model through `ChargeMapper`, and delegates payload generation.
- `PixPaymentOrchestrator` coordinates `PixPaymentStep` implementations using the Pipeline design pattern. Spring orders the injected steps using `@Order`; the sequence writes the payload format, merchant account information, payment details, merchant details, additional data, and finally the CRC.
- `Crc16CalculatorService` is a port for checksum calculation. `Crc16CalculatorAdapter` provides the CRC-16/CCITT-FALSE implementation used by the final payload step.
- `ChargeController` is the HTTP entry point and translates the application result into the response DTO.

This is a DDD-oriented model for a stateless payload-generation service, not a persistence-backed domain. It uses domain value objects and a clear application boundary; it does not currently define repositories, database aggregates, or persistence workflows.

## Caching

`PixCopyPasteAdapter.generate` uses Spring Cache backed by Caffeine. The cache is configured in `CacheConfiguration` with a maximum of 10,000 entries and a one-hour write expiry. The request DTO is a Java record, so Spring's default cache key uses value equality across its fields; identical requests can reuse the generated string.

The cache is in-memory and local to each service instance. It is lost when the process stops and is not shared between replicas. Mapping and request validation still occur on cache misses; cached results correspond to the full request value.

## Tests

Run the test suite:

```shell
./mvnw test
```

The tests cover BR Code field assembly, CRC validation, and Spring's `@Order` injection of the payment steps.