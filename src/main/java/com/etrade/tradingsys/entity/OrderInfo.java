package com.etrade.tradingsys.entity;

import com.etrade.tradingsys.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_info")
public class OrderInfo {

    @Id
    private Long orderId;

    private Long parentOrderId;

    @Enumerated(EnumType.STRING)
    private Source source;

    private Long accountId;

    private String clientOrderId;
    private String exchangeId;
    private String exchangeOrderId;

    @Enumerated(EnumType.STRING)
    private InstrumentType instrumentType;

    private String batchId;
    private String symbol;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private TimeInForce timeInForce;

    @Enumerated(EnumType.STRING)
    private Side side;

    private BigDecimal quantity;
    private BigDecimal leavesQty;
    private BigDecimal cashOrderQuantity;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PriceCurrency priceCurrency;

    private BigDecimal stopPrice;

    @Enumerated(EnumType.STRING)
    private ExecType execType;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String transmission;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime filledAt;
    private BigDecimal cumQty;

//    public OrderInfo() {
//    }
    // Getters and setters omitted for brevity

    // Enum definitions (see below)
}
