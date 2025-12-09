package com.infinitetechies.ecommerce_api.service.impl;

import com.infinitetechies.ecommerce_api.exception.BadRequestException;
import com.infinitetechies.ecommerce_api.exception.ResourceNotFoundException;
import com.infinitetechies.ecommerce_api.model.Product;
import com.infinitetechies.ecommerce_api.model.User;
import com.infinitetechies.ecommerce_api.model.Wishlist;
import com.infinitetechies.ecommerce_api.model.WishlistItem;
import com.infinitetechies.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.ProductResponse;
import com.infinitetechies.ecommerce_api.model.dto.response.WishlistItemResponse;
import com.infinitetechies.ecommerce_api.model.dto.response.WishlistResponse;
import com.infinitetechies.ecommerce_api.repository.ProductRepository;
import com.infinitetechies.ecommerce_api.repository.WishlistItemRepository;
import com.infinitetechies.ecommerce_api.repository.WishlistRepository;
import com.infinitetechies.ecommerce_api.service.inf.IUserService;
import com.infinitetechies.ecommerce_api.service.inf.IWishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements IWishlistService {

    private final IUserService userService;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional
    public String addToWishlist(WishlistItemRequest request) {
        User currentUser = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseGet(()->createWishlistForUser(currentUser));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with Id: "+request.getProductId()));

        boolean isAlreadyExits = wishlist.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(request.getProductId()));

        if(isAlreadyExits){
            throw new BadRequestException("Product already in Wishlist!");
        }

        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();

        wishlist.getItems().add(wishlistItem);
        wishlistRepository.save(wishlist);
        return "Product Added into Wishlist Successfully!";
    }

    @Override
    @Transactional
    public WishlistResponse removeFromWishlist(Long id) {
        User currentUser = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Wishlist not found"));

        WishlistItem wishlistItem = wishlistItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Wishlist Item not found"));

        wishlist.getItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);
        wishlistRepository.save(wishlist);
        return mapToWishlistResponse(wishlist);
    }

    @Override
    public WishlistResponse getWishlist() {
        User currentUser = userService.getCurrentUser();
        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseGet(()->createWishlistForUser(currentUser));
        return mapToWishlistResponse(wishlist);
    }

    private Wishlist createWishlistForUser(User currentUser) {
        Wishlist wishlist = Wishlist.builder()
                .user(currentUser)
                .build();
        wishlistRepository.save(wishlist);
        return wishlist;
    }

    private WishlistResponse mapToWishlistResponse(Wishlist wishlist){
        List<WishlistItemResponse> wishlistItems = wishlist.getItems().stream()
                .map(this::mapToWishlistItemResponse)
                .toList();

        return WishlistResponse.builder()
                .items(wishlistItems)
                .build();

    }

    private WishlistItemResponse mapToWishlistItemResponse(WishlistItem wishlistItem){
        return WishlistItemResponse.builder()
                .id(wishlistItem.getId())
                .product(mapToProductResponse(wishlistItem.getProduct()))
                .build();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .brand(product.getBrand())
                .model(product.getModel())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .sellerId(product.getSeller().getId())
                .sellerName(product.getSeller().getFirstName()+" "+product.getSeller().getId())
                .build();
    }
}
