package com.cristian.test.setitest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  @Valid
  @NotNull(message = "enviarPedido is required")
  @JsonProperty("enviarPedido")
  private OrderPayload order;
}
