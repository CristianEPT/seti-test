package com.cristian.test.setitest.soap;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentRequestTest {

    private Marshaller marshaller;

    @BeforeEach
    void setUp() throws Exception {
        JAXBContext context = JAXBContext.newInstance(ShipmentRequest.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    @Test
    void marshalsToXmlWithSpecElementNames() throws Exception {
        ShipmentRequest request = ShipmentRequest.builder()
                .orderNumber("75630275")
                .quantity("1")
                .eanCode("00110000765191002104587")
                .productName("Armario INVAL")
                .documentNumber("1113987400")
                .address("CR 72B 45 12 APT 301")
                .build();

        StringWriter writer = new StringWriter();
        marshaller.marshal(request, writer);
        String xml = writer.toString();

        assertThat(xml).contains("<pedido>75630275</pedido>");
        assertThat(xml).contains("<Cantidad>1</Cantidad>");
        assertThat(xml).contains("<EAN>00110000765191002104587</EAN>");
        assertThat(xml).contains("<Producto>Armario INVAL</Producto>");
        assertThat(xml).contains("<Cedula>1113987400</Cedula>");
        assertThat(xml).contains("<Direccion>CR 72B 45 12 APT 301</Direccion>");
    }
}
