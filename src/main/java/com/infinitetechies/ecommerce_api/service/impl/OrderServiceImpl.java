package com.infinitetechies.ecommerce_api.service.impl;

import com.infinitetechies.ecommerce_api.exception.BadRequestException;
import com.infinitetechies.ecommerce_api.exception.ResourceNotFoundException;
import com.infinitetechies.ecommerce_api.model.*;
import com.infinitetechies.ecommerce_api.model.dto.request.OrderRequest;
import com.infinitetechies.ecommerce_api.model.dto.request.OrderStatusUpdateRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.OrderItemResponse;
import com.infinitetechies.ecommerce_api.model.dto.response.OrderResponse;
import com.infinitetechies.ecommerce_api.model.enums.OrderStatus;
import com.infinitetechies.ecommerce_api.repository.CartRepository;
import com.infinitetechies.ecommerce_api.repository.OrderRepository;
import com.infinitetechies.ecommerce_api.repository.ProductRepository;
import com.infinitetechies.ecommerce_api.service.inf.IOrderService;
import com.infinitetechies.ecommerce_api.service.inf.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IUserService userService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));

        if(cart.getItems().isEmpty()){
            throw new BadRequestException("Cart is Empty!");
        }

        Order order = Order.builder()
                .user(currentUser)
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PENDING)
                .totalAmount(0.0)
                .build();

        double total = 0.0;

        for(CartItem cartItem: cart.getItems()){
            Product product = cartItem.getProduct();

            if(product.getStockQuantity() < cartItem.getQuantity())
                throw new BadRequestException("There is no enough stock for product : "+product.getTitle());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .priceAtPurchase(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .product(product)
                    .build();

            order.getItems().add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            total = total + (product.getPrice() * cartItem.getQuantity());
        }
         order.setTotalAmount(total);
         Order savedOrder = orderRepository.save(order);

         cart.getItems().clear();
         cartRepository.save(cart);

         return mapToOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        User currentUser = userService.getCurrentUser();
        return orderRepository.findByUserId(currentUser.getId()).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return mapToOrderResponse(orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found with Id: "+id))) ;
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Order not found with Id: "+request.getId()));
        User currentUser = userService.getCurrentUser();

        if(order.getStatus().equals(OrderStatus.DELIVERED) || order.getStatus().equals(OrderStatus.CANCELLED))
            throw new BadRequestException("Cannot update status when it is delivered or cancelled!");

        order.setStatus(request.getStatus());

        if(request.getStatus().equals(OrderStatus.CANCELLED)){
            for (OrderItem orderItem: order.getItems()){
                Product product = orderItem.getProduct();
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    private OrderResponse mapToOrderResponse(Order order){
        List<OrderItemResponse> orderItems = order.getItems().stream()
                .map(this::mapToOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUser().getId())
                .items(orderItems)
                .build();

    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem){
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .priceAtPurchase(orderItem.getPriceAtPurchase())
                .productTitle(orderItem.getProduct().getTitle())
                .productBrand(orderItem.getProduct().getBrand())
                .productModel(orderItem.getProduct().getModel())
                .subTotal(orderItem.getQuantity() * orderItem.getPriceAtPurchase())
                .build();
    }
}
