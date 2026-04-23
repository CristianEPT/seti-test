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
@XmlRootElement(name = "EnvioPedidoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentResponse {

  @XmlElement(name = "Codigo")
  private String code;

  @XmlElement(name = "Mensaje")
  private String message;
}
