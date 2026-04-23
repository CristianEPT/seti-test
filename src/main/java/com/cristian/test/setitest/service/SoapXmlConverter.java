package com.cristian.test.setitest.service;

import com.cristian.test.setitest.soap.ShipmentRequest;
import com.cristian.test.setitest.soap.ShipmentResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@Component
public class SoapXmlConverter {

  private static final String SOAP_ENVELOPE_TEMPLATE =
      """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:env="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
               <soapenv:Header/>
               <soapenv:Body>
                  <env:EnvioPedidoAcme>
                     %s
                  </env:EnvioPedidoAcme>
               </soapenv:Body>
            </soapenv:Envelope>""";

  private final JAXBContext requestContext;
  private final JAXBContext responseContext;

  public SoapXmlConverter() {
    try {
      requestContext = JAXBContext.newInstance(ShipmentRequest.class);
      responseContext = JAXBContext.newInstance(ShipmentResponse.class);
    } catch (JAXBException e) {
      throw new IllegalStateException("Failed to initialize JAXB contexts", e);
    }
  }

  public String toSoapEnvelope(ShipmentRequest request) {
    try {
      Marshaller marshaller = requestContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
      StringWriter writer = new StringWriter();
      marshaller.marshal(request, writer);
      return String.format(SOAP_ENVELOPE_TEMPLATE, writer);
    } catch (JAXBException e) {
      throw new RuntimeException("Failed to marshal SOAP request", e);
    }
  }

  public ShipmentResponse fromSoapResponse(String soapXml) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(soapXml)));
      Node responseNode = doc.getElementsByTagName("EnvioPedidoResponse").item(0);
      return responseContext
          .createUnmarshaller()
          .unmarshal(responseNode, ShipmentResponse.class)
          .getValue();
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse SOAP response", e);
    }
  }
}
