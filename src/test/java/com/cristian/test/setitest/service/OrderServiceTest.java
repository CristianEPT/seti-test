package com.cristian.test.setitest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cristian.test.setitest.dto.OrderPayload;
import com.cristian.test.setitest.dto.OrderRequest;
import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.mapper.OrderMapper;
import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private RestClient soapRestClient;

  @Mock private OrderMapper orderMapper;

  @Mock private SoapXmlConverter soapXmlConverter;

  @InjectMocks private OrderService orderService;

  @Test
  void sendOrderOrchestatesFullFlow() {
    OrderPayload payload = OrderPayload.builder().orderNumber("75630275").build();
    OrderRequest request = OrderRequest.builder().order(payload).build();
    ShipmentRequest shipmentRequest = ShipmentRequest.builder().orderNumber("75630275").build();
    ShipmentResponse shipmentResponse =
        ShipmentResponse.builder()
            .code("80375472")
            .message("Entregado exitosamente al cliente")
            .build();
    String soapXml = "<soapenv:Envelope/>";
    String rawResponseXml = "<soapenv:Envelope><EnvioPedidoResponse/></soapenv:Envelope>";

    when(orderMapper.toShipmentRequest(payload)).thenReturn(shipmentRequest);
    when(soapXmlConverter.toSoapEnvelope(shipmentRequest)).thenReturn(soapXml);
    when(soapRestClient.post().body(anyString()).retrieve().body(String.class))
        .thenReturn(rawResponseXml);
    when(soapXmlConverter.fromSoapResponse(rawResponseXml)).thenReturn(shipmentResponse);
    when(orderMapper.toOrderResponse(shipmentResponse)).thenReturn(OrderResponse.builder().build());

    OrderResponse result = orderService.sendOrder(request);

    assertThat(result).isNotNull();
    verify(orderMapper).toShipmentRequest(payload);
    verify(soapXmlConverter).toSoapEnvelope(shipmentRequest);
    verify(soapXmlConverter).fromSoapResponse(rawResponseXml);
    verify(orderMapper).toOrderResponse(shipmentResponse);
  }
}
