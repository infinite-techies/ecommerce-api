package com.infinitetechies.ecommerce_api.controller;

import com.infinitetechies.ecommerce_api.model.dto.request.CartItemRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.CartResponse;
import com.infinitetechies.ecommerce_api.service.inf.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;

    @PostMapping("/items")
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartItemRequest request){
        String response = cartService.addToCart(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateCart(@Valid @RequestBody CartItemRequest request){
        CartResponse response = cartService.updateCart(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeFromCart(@PathVariable Long cartItemId){
        CartResponse response = cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<CartResponse> getCart(){
        CartResponse response = cartService.getCart();
        return ResponseEntity.ok(response);
    }

}
