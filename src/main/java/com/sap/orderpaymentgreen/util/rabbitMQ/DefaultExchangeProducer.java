package com.sap.orderpaymentgreen.util.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
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
    ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessageAfterCharge(OrderDTO order) throws JsonProcessingException {
        String jsonOrder=objectMapper.writeValueAsString(order);
        rabbitTemplate.convertAndSend(QUEUE_AFTER_CHARG, jsonOrder);
        MessagingLoggingUtil.logSendMessage("", QUEUE_AFTER_CHARG, order);
    }
}