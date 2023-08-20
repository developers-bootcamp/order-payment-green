package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sap.orderpaymentgreen.model.OrderStatus.CREATED;
import static com.sap.orderpaymentgreen.util.rabbitMQ.RabbitMQConfig.*;
import static com.sap.orderpaymentgreen.util.rabbitMQ.MessagingLoggingUtil.logReceivedMessage;

@Service
public class RabbitMQConsumer {
    @Autowired
    private DefaultExchangeProducer defaultExchangeProducer;
    ObjectMapper objectMapper = new ObjectMapper();
    @RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
    public void listenOnWaitingChargQueue(String order) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(order, OrderDTO.class);
         orderDTO.setOrderStatus(CREATED);
        System.out.println(orderDTO);
        defaultExchangeProducer.sendMessageAfterCharge(orderDTO);
        logReceivedMessage(QUEUE_WAITING_FOR_CHARG, orderDTO);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,orderDTO);
    }

}
