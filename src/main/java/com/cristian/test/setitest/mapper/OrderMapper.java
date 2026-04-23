package com.cristian.test.setitest.mapper;

import com.cristian.test.setitest.dto.OrderPayload;
import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.dto.OrderResponsePayload;
import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

  public ShipmentRequest toShipmentRequest(OrderPayload payload) {
    return ShipmentRequest.builder()
        .orderNumber(payload.getOrderNumber())
        .quantity(payload.getQuantity())
        .eanCode(payload.getEanCode())
        .productName(payload.getProductName())
        .documentNumber(payload.getDocumentNumber())
        .address(payload.getAddress())
        .build();
  }

  public OrderResponse toOrderResponse(ShipmentResponse response) {
    return OrderResponse.builder()
        .orderResponse(
            OrderResponsePayload.builder()
                .shippingCode(response.getCode())
                .status(response.getMessage())
                .build())
        .build();
  }
}
