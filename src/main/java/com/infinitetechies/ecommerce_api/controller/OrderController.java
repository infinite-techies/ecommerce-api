package com.infinitetechies.ecommerce_api.controller;

import com.infinitetechies.ecommerce_api.model.dto.request.OrderRequest;
import com.infinitetechies.ecommerce_api.model.dto.request.OrderStatusUpdateRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.OrderResponse;
import com.infinitetechies.ecommerce_api.service.inf.IOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name="Order", description = "APIs for adding, updating and removing product from order.")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(){
        List<OrderResponse> orders = orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping()
    public ResponseEntity<OrderResponse> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest request){
        OrderResponse order = orderService.updateOrderStatus(request);
        return ResponseEntity.ok(order);
    }

}
