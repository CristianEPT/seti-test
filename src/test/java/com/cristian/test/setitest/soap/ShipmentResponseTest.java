package com.cristian.test.setitest.soap;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentResponseTest {

    private Unmarshaller unmarshaller;

    @BeforeEach
    void setUp() throws Exception {
        JAXBContext context = JAXBContext.newInstance(ShipmentResponse.class);
        unmarshaller = context.createUnmarshaller();
    }

    @Test
    void unmarshalsFromSpecXml() throws Exception {
        String xml = """
                <EnvioPedidoResponse>
                    <Codigo>80375472</Codigo>
                    <Mensaje>Entregado exitosamente al cliente</Mensaje>
                </EnvioPedidoResponse>
                """;

        ShipmentResponse response = (ShipmentResponse) unmarshaller.unmarshal(new StringReader(xml));

        assertThat(response.getCode()).isEqualTo("80375472");
        assertThat(response.getMessage()).isEqualTo("Entregado exitosamente al cliente");
    }
}
