package com.cristian.test.setitest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.dto.OrderResponsePayload;
import com.cristian.test.setitest.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private OrderService orderService;

  @Test
  void postOrderReturns200WithExpectedJsonStructure() throws Exception {
    OrderResponse serviceResponse =
        OrderResponse.builder()
            .orderResponse(
                OrderResponsePayload.builder()
                    .shippingCode("80375472")
                    .status("Entregado exitosamente al cliente")
                    .build())
            .build();

    when(orderService.sendOrder(any())).thenReturn(serviceResponse);

    mockMvc
        .perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {
                                  "enviarPedido": {
                                    "numPedido": "75630275",
                                    "cantidadPedido": "1",
                                    "codigoEAN": "00110000765191002104587",
                                    "nombreProducto": "Armario INVAL",
                                    "numDocumento": "1113987400",
                                    "direccion": "CR 72B 45 12 APT 301"
                                  }
                                }
                                """))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.enviarPedidoRespuesta.codigoEnvio").value("80375472"))
        .andExpect(
            jsonPath("$.enviarPedidoRespuesta.estado").value("Entregado exitosamente al cliente"));
  }

  @Test
  void postOrderWithEmptyBodyReturns400() throws Exception {
    mockMvc
        .perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest());
  }
}
