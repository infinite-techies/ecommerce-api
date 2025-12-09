package com.infinitetechies.ecommerce_api.service.inf;

import com.infinitetechies.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.WishlistResponse;

public interface IWishlistService {

    String addToWishlist(WishlistItemRequest request);
    WishlistResponse removeFromWishlist(Long id);
    WishlistResponse getWishlist();
}
