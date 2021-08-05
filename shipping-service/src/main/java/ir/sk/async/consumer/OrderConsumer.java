package ir.sk.async.consumer;

import java.io.IOException;

import ir.sk.async.producer.OrderProducer;
import ir.sk.constants.OrderStatus;
import ir.sk.domain.Order;
import ir.sk.reactive.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderConsumer {

    @Autowired
    ShippingService shippingService;

    @Autowired
    OrderProducer orderProducer;

    @KafkaListener(topics = "orders", groupId = "shipping")
    public void consume(Order order) throws IOException {
        log.info("Order received to process: {}", order);
        if (OrderStatus.PREPARE_SHIPPING.equals(order.getOrderStatus())) {
            shippingService.handleOrder(order)
                .doOnSuccess(o -> {
                    log.info("Order processed succesfully.");
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.SHIPPING_SUCCESS)
                        .setShippingDate(o.getShippingDate()));
                })
                .doOnError(e -> {
                    if (log.isErrorEnabled())
                        log.error("Order failed to process: " + e);
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.SHIPPING_FAILURE)
                        .setResponseMessage(e.getMessage()));
                })
                .subscribe();
        }
    }
}