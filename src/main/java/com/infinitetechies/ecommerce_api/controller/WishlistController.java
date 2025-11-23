package com.infinitetechies.ecommerce_api.controller;

import com.infinitetechies.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.WishlistResponse;
import com.infinitetechies.ecommerce_api.service.inf.IWishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    @PostMapping("/item")
    public ResponseEntity<String> addToWishlist(@Valid @RequestBody WishlistItemRequest wishlistItemRequest){
        String response = wishlistService.addToWishlist(wishlistItemRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<WishlistResponse> removeFromWishlist(@PathVariable Long itemId){
        WishlistResponse response = wishlistService.removeFromWishlist(itemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<WishlistResponse> getWishlist(){
        WishlistResponse response = wishlistService.getWishlist();
        return ResponseEntity.ok(response);
    }
}
