package com.cristian.test.setitest.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "EnvioPedidoRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentRequest {

    @XmlElement(name = "pedido")
    private String orderNumber;

    @XmlElement(name = "Cantidad")
    private String quantity;

    @XmlElement(name = "EAN")
    private String eanCode;

    @XmlElement(name = "Producto")
    private String productName;

    @XmlElement(name = "Cedula")
    private String documentNumber;

    @XmlElement(name = "Direccion")
    private String address;
}
