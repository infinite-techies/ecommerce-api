package com.infinitetechies.ecommerce_api.service.inf;

import com.infinitetechies.ecommerce_api.model.dto.request.OrderRequest;
import com.infinitetechies.ecommerce_api.model.dto.request.OrderStatusUpdateRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse> getMyOrders();
    OrderResponse getOrderById(Long id);
    OrderResponse updateOrderStatus(OrderStatusUpdateRequest request);
}
