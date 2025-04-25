package com.etrade.tradingsys.repository;

import com.etrade.tradingsys.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    Optional<OrderInfo> findByOrderId(Long orderId);
    // You can add custom query methods here if needed
}
