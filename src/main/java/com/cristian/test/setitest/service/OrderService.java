package com.cristian.test.setitest.service;

import com.cristian.test.setitest.dto.OrderRequest;
import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.mapper.OrderMapper;
import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final RestClient soapRestClient;
  private final OrderMapper orderMapper;
  private final SoapXmlConverter soapXmlConverter;

  public OrderResponse sendOrder(OrderRequest request) {
    ShipmentRequest shipmentRequest = orderMapper.toShipmentRequest(request.getOrder());

    String soapXml = soapXmlConverter.toSoapEnvelope(shipmentRequest);

    String responseXml = soapRestClient.post().body(soapXml).retrieve().body(String.class);

    ShipmentResponse shipmentResponse = soapXmlConverter.fromSoapResponse(responseXml);

    return orderMapper.toOrderResponse(shipmentResponse);
  }
}
