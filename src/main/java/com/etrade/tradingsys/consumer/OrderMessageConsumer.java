package com.etrade.tradingsys.consumer;

import com.etrade.tradingsys.messaging.dto.FixOrderPayload;
import com.etrade.tradingsys.service.OrderInfoService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Component
public class OrderMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageConsumer.class);

    @Autowired
    private OrderInfoService orderInfoService;

    @Bean
    public Consumer<Message<FixOrderPayload>> processMessage() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);

            logger.info("Received message with deliveryTag: {}", deliveryTag);
            logger.debug("Message payload: {}", message.getPayload());

            try {
                logger.info("Saving order info for clientOrderId: {}", message.getPayload().getClientOrderId());
                orderInfoService.saveOrderInfo(message.getPayload());
                logger.info("Order info saved successfully for clientOrderId: {}", message.getPayload().getClientOrderId());

                if (channel != null && deliveryTag != null) {
                    channel.basicAck(deliveryTag, false); // Manual ack
                    logger.info("Manually acknowledged message with deliveryTag: {}", deliveryTag);
                } else {
                    logger.warn("Channel or deliveryTag is null, cannot acknowledge message.");
                }
            } catch (IOException e) {
                logger.error("IOException while acknowledging message with deliveryTag: {}", deliveryTag, e);
                // Optionally: handle requeue or dead-letter logic here
            } catch (Exception e) {
                logger.error("Exception while processing message with deliveryTag: {}", deliveryTag, e);
            }
        };
    }
}
