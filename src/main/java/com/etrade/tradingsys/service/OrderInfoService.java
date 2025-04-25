package com.etrade.tradingsys.service;

import com.etrade.tradingsys.entity.OrderInfo;
import com.etrade.tradingsys.messaging.dto.FixOrderPayload;
import com.etrade.tradingsys.model.enums.*;
import com.etrade.tradingsys.repository.OrderInfoRepository;
import exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderInfoService {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoService.class);

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public void saveOrderInfo(FixOrderPayload fixOrderInfo) {
        logger.info("Starting to save order for clientOrderId: {}", fixOrderInfo.getClientOrderId());

        try {
            OrderInfo order = OrderInfo.builder()
                    .parentOrderId(fixOrderInfo.getParentOrderId())
                    .accountId(fixOrderInfo.getAccountId())
                    .clientOrderId(fixOrderInfo.getClientOrderId())
                    .instrumentType(InstrumentType.valueOf(fixOrderInfo.getInstrumentType()))
                    .batchId(fixOrderInfo.getBatchId())
                    .symbol(fixOrderInfo.getSymbol())
                    .orderType(OrderType.valueOf(fixOrderInfo.getOrderType()))
                    .timeInForce(TimeInForce.valueOf(fixOrderInfo.getTimeInForce()))
                    .side(Side.valueOf(fixOrderInfo.getSide()))
                    .quantity(fixOrderInfo.getQuantity())
                    .cashOrderQuantity(fixOrderInfo.getCashOrderQuantity())
                    .price(fixOrderInfo.getPrice())
                    .priceCurrency(PriceCurrency.valueOf(fixOrderInfo.getPriceCurrency()))
                    .stopPrice(fixOrderInfo.getStopPrice())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .orderId(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L))
                    .source(Source.FIX_GW)
                    .build();

            logger.debug("Constructed OrderInfo object: {}", order);

            orderInfoRepository.save(order);
            logger.info("Successfully saved order with ID: {} for clientOrderId: {}",
                    order.getOrderId(), order.getClientOrderId());

        } catch (IllegalArgumentException e) {
            logger.error("Error converting enum values for clientOrderId: {}",
                    fixOrderInfo.getClientOrderId(), e);
            throw e;
        }
    }

    public OrderInfo getOrderInfo(Long orderId) {
        logger.debug("Looking up order with ID: {}", orderId);

        return orderInfoRepository.findByOrderId(orderId)
                .map(order -> {
                    logger.debug("Found order: {}", order);
                    return order;
                })
                .orElseThrow(() -> {
                    logger.error("Order not found with ID: {}", orderId);
                    return new OrderNotFoundException(orderId);
                });
    }
}
