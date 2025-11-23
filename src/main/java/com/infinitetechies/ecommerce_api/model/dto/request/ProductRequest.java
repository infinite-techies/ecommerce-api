package com.infinitetechies.ecommerce_api.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Title is required!")
    @Size(min = 2, max = 100, message = "Title should be between 4 to 100 characters")
    private String title;

    @NotBlank(message = "Brand is required!")
    @Size(min = 2, max = 100, message = "Brand should be between 4 to 100 characters")
    private String brand;

    @NotBlank(message = "Model is required!")
    @Size(min = 2, max = 100, message = "Model should be between 4 to 100 characters")
    private String model;

    @NotBlank(message = "Description is required!")
    @Size(min = 10, max = 1000, message = "Description should be between 10 to 1000 characters")
    private String description;

    @NotNull(message = "Price is required!")
    @Min(value = 10, message = "Price must be greater than 10")
    private Double price;

    @NotNull(message = "Stock quantity is required!")
    @Min(value = 1, message = "Stock quantity should be at least 1")
    private Integer stockQuantity;

    @NotBlank(message = "Category is required!")
    @Size(min = 2, max = 100, message = "Category should be between 4 to 100 characters")
    private String category;

    @NotBlank(message = "Image URL is required!")
    private String imageUrl;
}
