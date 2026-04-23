# seti-test

REST API that acts as an adapter between a JSON client and a SOAP/XML service for ACME's order shipment cycle.

## Architecture

```
Client (JSON)
    │
    ▼
POST /api/orders
    │
    ▼
OrderController → OrderService → OrderMapper → SoapXmlConverter
                                                      │
                                              SOAP XML envelope
                                                      │
                                                      ▼
                                             External SOAP endpoint
                                                      │
                                              XML response parsing
                                                      │
                                                      ▼
Client ← JSON response ← OrderMapper ← ShipmentResponse
```

### Field mapping

**JSON → SOAP XML**

| JSON (`enviarPedido`) | XML (`EnvioPedidoRequest`) |
|-----------------------|----------------------------|
| `numPedido`           | `<pedido>`                 |
| `cantidadPedido`      | `<Cantidad>`               |
| `codigoEAN`           | `<EAN>`                    |
| `nombreProducto`      | `<Producto>`               |
| `numDocumento`        | `<Cedula>`                 |
| `direccion`           | `<Direccion>`              |

**SOAP XML → JSON**

| XML (`EnvioPedidoResponse`) | JSON (`enviarPedidoRespuesta`) |
|-----------------------------|--------------------------------|
| `<Codigo>`                  | `codigoEnvio`                  |
| `<Mensaje>`                 | `estado`                       |

## Tech stack

- Java 25 / Spring Boot 4
- Spring WebMVC + RestClient
- JAXB (XML binding)
- Apache HttpClient 5
- Lombok
- JUnit 5 + Mockito

## Run with Docker

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

## Run locally

```bash
./gradlew bootRun
```

## API

### POST /api/orders

**Request**

```json
{
  "enviarPedido": {
    "numPedido": "75630275",
    "cantidadPedido": "1",
    "codigoEAN": "00110000765191002104587",
    "nombreProducto": "Armario INVAL",
    "numDocumento": "1113987400",
    "direccion": "CR 72B 45 12 APT 301"
  }
}
```

**Response `200 OK`**

```json
{
  "enviarPedidoRespuesta": {
    "codigoEnvio": "80375472",
    "estado": "Entregado exitosamente al cliente"
  }
}
```

**Response `400 Bad Request`** (missing or empty fields)

```json
{
  "errors": {
    "order.orderNumber": "numPedido is required"
  }
}
```

## Run tests

```bash
./gradlew test
```
