package com.infinitetechies.ecommerce_api.controller;

import com.infinitetechies.ecommerce_api.model.dto.request.ProductRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.ProductResponse;
import com.infinitetechies.ecommerce_api.service.inf.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name="Product", description = "APIs for adding, updating and removing product from Product.")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String category){
        List<ProductResponse> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice){
        List<ProductResponse> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts (@RequestParam String keyword){
        List<ProductResponse> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest request){
        String response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> updateProduct(@RequestParam Long id, @Valid @RequestBody ProductRequest request){
        String response = productService.updateProduct(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        String response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }
}
