package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.sap.orderpaymentgreen.DTO.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.sap.orderpaymentgreen.util.rabbitMQ.RabbitMQConfig.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultExchangeProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendMessageAfterCharge(OrderDTO order) {
        OrderDTO msg = new OrderDTO();
        msg.setCustomer(order.getCustomer()!=null?order.getCustomer() :"empty");
        rabbitTemplate.convertAndSend(QUEUE_AFTER_CHARG, msg);
        MessagingLoggingUtil.logSendMessage("", QUEUE_AFTER_CHARG, msg);
    }
}