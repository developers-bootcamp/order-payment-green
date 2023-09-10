package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sap.orderpaymentgreen.model.OrderStatus.CREATED;
import static com.sap.orderpaymentgreen.util.rabbitMQ.RabbitMQConfig.*;
import static com.sap.orderpaymentgreen.util.rabbitMQ.MessagingLoggingUtil.logReceivedMessage;

@Service
public class Consumer {
    @Autowired
    private Producer defaultExchangeProducer;

    @Autowired
     private PaymentService paymentService;
    ObjectMapper objectMapper = new ObjectMapper();
    @RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
    public void listenOnWaitingChargQueue(String order) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(order, OrderDTO.class);
        System.out.println(orderDTO);
         paymentService.getPayment(orderDTO);
        logReceivedMessage(QUEUE_WAITING_FOR_CHARG, orderDTO);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,orderDTO);
    }

}
