package com.cristian.test.setitest.controller;

import com.cristian.test.setitest.dto.OrderRequest;
import com.cristian.test.setitest.dto.OrderResponse;
import com.cristian.test.setitest.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponse> sendOrder(@Valid @RequestBody OrderRequest request) {
    return ResponseEntity.ok(orderService.sendOrder(request));
  }
}
