package com.cristian.test.setitest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

class OrderRequestTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void deserializesFromSpecJson() {
    String json =
        """
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
                """;

    OrderRequest request = mapper.readValue(json, OrderRequest.class);

    assertThat(request.getOrder()).isNotNull();
    assertThat(request.getOrder().getOrderNumber()).isEqualTo("75630275");
    assertThat(request.getOrder().getQuantity()).isEqualTo("1");
    assertThat(request.getOrder().getEanCode()).isEqualTo("00110000765191002104587");
    assertThat(request.getOrder().getProductName()).isEqualTo("Armario INVAL");
    assertThat(request.getOrder().getDocumentNumber()).isEqualTo("1113987400");
    assertThat(request.getOrder().getAddress()).isEqualTo("CR 72B 45 12 APT 301");
  }

  @Test
  void serializesToSpecJsonFieldNames() throws Exception {
    OrderRequest request =
        OrderRequest.builder()
            .order(
                OrderPayload.builder()
                    .orderNumber("75630275")
                    .quantity("1")
                    .eanCode("00110000765191002104587")
                    .productName("Armario INVAL")
                    .documentNumber("1113987400")
                    .address("CR 72B 45 12 APT 301")
                    .build())
            .build();

    String json = mapper.writeValueAsString(request);

    assertThat(json).contains("\"enviarPedido\"");
    assertThat(json).contains("\"numPedido\":\"75630275\"");
    assertThat(json).contains("\"codigoEAN\":\"00110000765191002104587\"");
  }
}
