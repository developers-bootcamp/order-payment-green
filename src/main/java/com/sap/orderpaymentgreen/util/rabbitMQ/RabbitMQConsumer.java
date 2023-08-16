package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.sap.orderpaymentgreen.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.sap.orderpaymentgreen.util.rabbitMQ.RabbitMQConfig.*;
import static com.sap.orderpaymentgreen.util.rabbitMQ.MessagingLoggingUtil.logReceivedMessage;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
    public void listenOnWaitingChargQueue(OrderDTO order) {
        logReceivedMessage(QUEUE_WAITING_FOR_CHARG, order);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,order);
    }
//@RabbitListener(queues = {QUEUE_WAITING_FOR_CHARG})
//public void listenOnWaitingChargQueue(String order) {
//    System.out.println("💕💕💕💕💕");
//   // logReceivedMessage(QUEUE_WAITING_FOR_CHARG, order);
//   // MessagingLoggingUtil.logReceivedMessage(QUEUE_WAITING_FOR_CHARG,order);
//}
}
