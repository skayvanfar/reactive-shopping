package ir.sk.async.producer;

import ir.sk.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendMessage(Order order) {
        log.info("Order processed to dispatch: {}", order);
        this.kafkaTemplate.send("orders", order);
    }

}
