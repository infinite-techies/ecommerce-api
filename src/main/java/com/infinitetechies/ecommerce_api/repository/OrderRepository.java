package com.infinitetechies.ecommerce_api.repository;

import com.infinitetechies.ecommerce_api.model.Order;
import com.infinitetechies.ecommerce_api.model.dto.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long id);
}
