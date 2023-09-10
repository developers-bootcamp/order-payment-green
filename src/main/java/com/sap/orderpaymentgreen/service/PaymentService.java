package com.sap.orderpaymentgreen.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.mapper.IOrderMapper;
import com.sap.orderpaymentgreen.mapper.IPaymentMapper;
import com.sap.orderpaymentgreen.model.OrderStatus;
import com.sap.orderpaymentgreen.model.Payment;
import com.sap.orderpaymentgreen.model.PaymentResponse;
import com.sap.orderpaymentgreen.repository.IPaymentRepository;
import com.sap.orderpaymentgreen.util.rabbitMQ.Producer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    IOrderMapper orderMapper;
    @Autowired
    IPaymentMapper paymentMapper;
    @Autowired
    IPaymentRepository paymentRepository;
    @Autowired
    Producer producer;


    //@Value("${paymentServerDebitUrl}")
    @Value("http://b2397a09-a369-47c1-ba30-5a136ac18f7d.mock.pstmn.io/debit")
    private String paymentServerDebitUrl;

    @SneakyThrows
    public void getPayment(OrderDTO orderDTO){

        PaymentDTO payment = orderMapper.orderDTOToPaymentDTO(orderDTO);

        ObjectMapper mapper = new ObjectMapper();
        String paymentJson = mapper.writeValueAsString(payment);

        WebClient webClient = WebClient.builder().build();

        Mono<String> response = null;

        response = webClient
                .post()
                .uri(paymentServerDebitUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paymentJson)
                .retrieve()
                .bodyToMono(String.class);

        if(response != null){
            String res = response.block();

            PaymentResponse paymentResponse = mapper.readValue(res, PaymentResponse.class);

            if(paymentResponse.getStatus().equals("approved")){
                Payment paymentToMongo = paymentMapper.orderDTOToPayment(orderDTO);
                paymentToMongo.setInvoiceNumber(paymentResponse.getInvoiceNumber());
                paymentRepository.save(paymentToMongo);
            }
            else {
                orderDTO.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
            }
            producer.sendMessageAfterCharge(orderDTO);
        }
    }
}
