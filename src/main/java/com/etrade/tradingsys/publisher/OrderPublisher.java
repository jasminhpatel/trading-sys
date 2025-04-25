package com.etrade.tradingsys.publisher;

import com.etrade.tradingsys.messaging.dto.FixOrderPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class OrderPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderPublisher.class);
    private static final AtomicLong ORDER_ID = new AtomicLong(1000);

    private final StreamBridge streamBridge;

    @Value("${my.order.publisher.binding:orderPublisher-out-0}")
    private String orderPublisherBinding;

    public OrderPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
        logger.info("OrderPublisher initialized with binding '{}'", orderPublisherBinding);
    }

    @Scheduled(fixedRate = 5000)
    public void publishDummyOrder() {
        FixOrderPayload order = createDummyOrderPayload();
        logger.debug("Attempting to publish order: {}", order);

        boolean sent = false;
        try {
            sent = streamBridge.send(orderPublisherBinding, order);
            if (sent) {
                logger.info("Published order: {}", order.getClientOrderId());
            } else {
                logger.error("Failed to publish order: {}", order.getClientOrderId());
            }
        } catch (Exception ex) {
            logger.error("Exception while publishing order: {}", order.getClientOrderId(), ex);
        }
    }

    private FixOrderPayload createDummyOrderPayload() {
        FixOrderPayload payload = new FixOrderPayload();
        payload.setParentOrderId(null);
        payload.setAccountId(12345L);
        payload.setClientOrderId("CLIENT-" + ORDER_ID.get());
        payload.setInstrumentType("EQUITY");
        payload.setSymbol("AAPL");
        payload.setOrderType("LIMIT");
        payload.setTimeInForce("GTC");
        payload.setSide("BUY");
        payload.setQuantity(new BigDecimal("100.0000"));
        payload.setCashOrderQuantity(new BigDecimal("0.00"));
        payload.setPrice(new BigDecimal("150.5000"));
        payload.setPriceCurrency("USD");
        payload.setStopPrice(null);
        payload.setCreatedAt(LocalDateTime.now());
        logger.debug("Created dummy FixOrderPayload: {}", payload);
        return payload;
    }
}
