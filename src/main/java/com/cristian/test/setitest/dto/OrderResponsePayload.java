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
public class OrderResponsePayload {

    @JsonProperty("codigoEnvio")
    private String shippingCode;

    @JsonProperty("estado")
    private String status;
}
