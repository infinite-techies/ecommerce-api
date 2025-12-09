package com.infinitetechies.ecommerce_api.service.inf;

import com.infinitetechies.ecommerce_api.model.dto.request.CartItemRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.CartResponse;

public interface ICartService {

    String addToCart(CartItemRequest request);
    CartResponse updateCart(CartItemRequest request);
    CartResponse removeFromCart(Long cartItemId);
    CartResponse getCart();
}
