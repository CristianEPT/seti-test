package com.cristian.test.setitest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayload {

    @JsonProperty("numPedido")
    private String orderNumber;

    @JsonProperty("cantidadPedido")
    private String quantity;

    @JsonProperty("codigoEAN")
    private String eanCode;

    @JsonProperty("nombreProducto")
    private String productName;

    @JsonProperty("numDocumento")
    private String documentNumber;

    @JsonProperty("direccion")
    private String address;
}
