package com.cristian.test.setitest.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class OrderControllerIntegrationTest {

  private static final String SOAP_RESPONSE =
      """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:env="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
               <soapenv:Header/>
               <soapenv:Body>
                  <env:EnvioPedidoAcmeResponse>
                     <EnvioPedidoResponse>
                        <Codigo>80375472</Codigo>
                        <Mensaje>Entregado exitosamente al cliente</Mensaje>
                     </EnvioPedidoResponse>
                  </env:EnvioPedidoAcmeResponse>
               </soapenv:Body>
            </soapenv:Envelope>
            """;

  @Autowired private MockMvc mockMvc;

  @Autowired private MockRestServiceServer mockSoapServer;

  @Test
  void fullFlowReturnsExpectedJsonResponse() throws Exception {
    mockSoapServer
        .expect(requestTo(Matchers.containsString("mocky.io")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(SOAP_RESPONSE, MediaType.TEXT_XML));

    mockMvc
        .perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
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
                    """))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.enviarPedidoRespuesta.codigoEnvio").value("80375472"))
        .andExpect(
            jsonPath("$.enviarPedidoRespuesta.estado").value("Entregado exitosamente al cliente"));

    mockSoapServer.verify();
  }
}
