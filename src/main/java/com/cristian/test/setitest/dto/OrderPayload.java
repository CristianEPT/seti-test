package com.cristian.test.setitest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayload {

  @NotBlank(message = "numPedido is required")
  @JsonProperty("numPedido")
  private String orderNumber;

  @NotBlank(message = "cantidadPedido is required")
  @JsonProperty("cantidadPedido")
  private String quantity;

  @NotBlank(message = "codigoEAN is required")
  @JsonProperty("codigoEAN")
  private String eanCode;

  @NotBlank(message = "nombreProducto is required")
  @JsonProperty("nombreProducto")
  private String productName;

  @NotBlank(message = "numDocumento is required")
  @JsonProperty("numDocumento")
  private String documentNumber;

  @NotBlank(message = "direccion is required")
  @JsonProperty("direccion")
  private String address;
}
