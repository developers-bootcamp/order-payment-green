package com.sap.orderpaymentgreen.util.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend("myExchange", "routingKey", message);
        return "Message sent: " + message;
    }

    @RabbitListener(queues = "afterPaymentQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
