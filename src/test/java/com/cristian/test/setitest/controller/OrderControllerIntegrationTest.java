package com.cristian.test.setitest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cristian.test.setitest.service.SoapXmlConverter;
import com.cristian.test.setitest.soap.ShipmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

  private static final String REQUEST_JSON =
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

  @Autowired private MockMvc mockMvc;

  @MockitoBean(name = "soapRestClient")
  private RestClient soapRestClient;

  @MockitoBean private SoapXmlConverter soapXmlConverter;

  @BeforeEach
  void setUp() {
    RestClient.RequestBodyUriSpec uriSpec =
        Mockito.mock(RestClient.RequestBodyUriSpec.class, Answers.RETURNS_DEEP_STUBS);
    when(soapRestClient.post()).thenReturn(uriSpec);
    when(uriSpec.body(anyString()).retrieve().body(String.class)).thenReturn("<soapResponse/>");

    when(soapXmlConverter.toSoapEnvelope(any())).thenReturn("<soapRequest/>");
    when(soapXmlConverter.fromSoapResponse(anyString()))
        .thenReturn(
            ShipmentResponse.builder()
                .code("80375472")
                .message("Entregado exitosamente al cliente")
                .build());
  }

  @Test
  void fullContextLoadsAndReturnsExpectedJsonResponse() throws Exception {
    mockMvc
        .perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(REQUEST_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.enviarPedidoRespuesta.codigoEnvio").value("80375472"))
        .andExpect(
            jsonPath("$.enviarPedidoRespuesta.estado").value("Entregado exitosamente al cliente"));
  }
}
