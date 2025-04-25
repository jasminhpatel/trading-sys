package com.etrade.tradingsys.messaging.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FixOrderPayload {
    private Long parentOrderId;

    private Long accountId;

    private String clientOrderId;


    private String instrumentType;

    private String batchId;
    private String symbol;

    private String orderType;

    private String timeInForce;

    private String side;

    private BigDecimal quantity;
    private BigDecimal cashOrderQuantity;
    private BigDecimal price;

    private String priceCurrency;

    private BigDecimal stopPrice;

    private LocalDateTime createdAt;

}
