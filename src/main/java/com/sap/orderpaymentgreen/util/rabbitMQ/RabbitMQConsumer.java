package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.sap.orderpaymentgreen.util.rabbitMQ.RabbitMQConfig.*;
import static com.sap.orderpaymentgreen.util.rabbitMQ.MessagingLoggingUtil.logReceivedMessage;

@Service
public class RabbitMQConsumer {
    ObjectMapper objectMapper = new ObjectMapper();
    @RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
    public void listenOnWaitingChargQueue(String order) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(order, OrderDTO.class);
        logReceivedMessage(QUEUE_WAITING_FOR_CHARG, orderDTO);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,orderDTO);
    }
//@RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
//public void listenOnWaitingChargQueue(String order) {
//    System.out.println("💕💕💕💕💕");
//    System.out.println(order);
//    // logReceivedMessage(QUEUE_WAITING_FOR_CHARG, order);
//   // MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,order);
//}
}
