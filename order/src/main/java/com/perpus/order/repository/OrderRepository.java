package com.perpus.order.repository;

import com.perpus.order.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderBook,Long> {
}
