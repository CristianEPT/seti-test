package com.cristian.test.setitest.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import org.junit.jupiter.api.Test;

class SoapXmlConverterTest {

  private final SoapXmlConverter converter = new SoapXmlConverter();

  @Test
  void toSoapEnvelopeContainsCorrectXmlElementNames() {
    ShipmentRequest request =
        ShipmentRequest.builder()
            .orderNumber("75630275")
            .quantity("1")
            .eanCode("00110000765191002104587")
            .productName("Armario INVAL")
            .documentNumber("1113987400")
            .address("CR 72B 45 12 APT 301")
            .build();

    String xml = converter.toSoapEnvelope(request);

    assertThat(xml).contains("soapenv:Envelope");
    assertThat(xml).contains("env:EnvioPedidoAcme");
    assertThat(xml).contains("<pedido>75630275</pedido>");
    assertThat(xml).contains("<Cantidad>1</Cantidad>");
    assertThat(xml).contains("<EAN>00110000765191002104587</EAN>");
    assertThat(xml).contains("<Producto>Armario INVAL</Producto>");
    assertThat(xml).contains("<Cedula>1113987400</Cedula>");
    assertThat(xml).contains("<Direccion>CR 72B 45 12 APT 301</Direccion>");
  }

  @Test
  void fromSoapResponseExtractsCodeAndMessage() {
    String soapXml =
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

    ShipmentResponse response = converter.fromSoapResponse(soapXml);

    assertThat(response.getCode()).isEqualTo("80375472");
    assertThat(response.getMessage()).isEqualTo("Entregado exitosamente al cliente");
  }
}
