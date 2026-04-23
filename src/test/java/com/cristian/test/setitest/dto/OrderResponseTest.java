package com.cristian.test.setitest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void serializesToSpecJsonFieldNames() throws Exception {
        OrderResponse response = OrderResponse.builder()
                .orderResponse(OrderResponsePayload.builder()
                        .shippingCode("80375472")
                        .status("Entregado exitosamente al cliente")
                        .build())
                .build();

        String json = mapper.writeValueAsString(response);

        assertThat(json).contains("\"enviarPedidoRespuesta\"");
        assertThat(json).contains("\"codigoEnvio\":\"80375472\"");
        assertThat(json).contains("\"estado\":\"Entregado exitosamente al cliente\"");
    }

    @Test
    void deserializesFromSpecJson() throws Exception {
        String json = """
                {
                  "enviarPedidoRespuesta": {
                    "codigoEnvio": "80375472",
                    "estado": "Entregado exitosamente al cliente"
                  }
                }
                """;

        OrderResponse response = mapper.readValue(json, OrderResponse.class);

        assertThat(response.getOrderResponse()).isNotNull();
        assertThat(response.getOrderResponse().getShippingCode()).isEqualTo("80375472");
        assertThat(response.getOrderResponse().getStatus()).isEqualTo("Entregado exitosamente al cliente");
    }
}
