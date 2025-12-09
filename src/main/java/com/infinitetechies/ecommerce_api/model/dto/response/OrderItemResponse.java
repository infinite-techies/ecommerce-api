package com.infinitetechies.ecommerce_api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double priceAtPurchase;
    private String productTitle;
    private String productModel;
    private String productBrand;
    private double subTotal;
}
