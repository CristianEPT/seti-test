package com.cristian.test.setitest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cristian.test.setitest.dto.OrderPayload;
import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import org.junit.jupiter.api.Test;

class OrderMapperTest {

  private final OrderMapper mapper = new OrderMapper();

  @Test
  void mapsOrderPayloadToShipmentRequest() {
    OrderPayload payload =
        OrderPayload.builder()
            .orderNumber("75630275")
            .quantity("1")
            .eanCode("00110000765191002104587")
            .productName("Armario INVAL")
            .documentNumber("1113987400")
            .address("CR 72B 45 12 APT 301")
            .build();

    ShipmentRequest result = mapper.toShipmentRequest(payload);

    assertThat(result.getOrderNumber()).isEqualTo("75630275");
    assertThat(result.getQuantity()).isEqualTo("1");
    assertThat(result.getEanCode()).isEqualTo("00110000765191002104587");
    assertThat(result.getProductName()).isEqualTo("Armario INVAL");
    assertThat(result.getDocumentNumber()).isEqualTo("1113987400");
    assertThat(result.getAddress()).isEqualTo("CR 72B 45 12 APT 301");
  }

  @Test
  void mapsShipmentResponseToOrderResponse() {
    ShipmentResponse response =
        ShipmentResponse.builder()
            .code("80375472")
            .message("Entregado exitosamente al cliente")
            .build();

    OrderResponse result = mapper.toOrderResponse(response);

    assertThat(result.getOrderResponse()).isNotNull();
    assertThat(result.getOrderResponse().getShippingCode()).isEqualTo("80375472");
    assertThat(result.getOrderResponse().getStatus())
        .isEqualTo("Entregado exitosamente al cliente");
  }
}
