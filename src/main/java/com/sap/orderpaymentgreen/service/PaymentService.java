package com.sap.orderpaymentgreen.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.mapper.IOrderMapper;
import com.sap.orderpaymentgreen.mapper.IPaymentMapper;
import com.sap.orderpaymentgreen.model.OrderStatus;
import com.sap.orderpaymentgreen.model.Payment;
import com.sap.orderpaymentgreen.repository.IPaymentRepository;
import com.sap.orderpaymentgreen.util.rabbitMQ.Producer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
    @SneakyThrows
    public void getPayment(OrderDTO orderDTO){

        String paymentServerUrl = "http://b77cb14d-0f20-480f-be00-20fb4d64536f.mock.pstmn.io";

        PaymentDTO payment = orderMapper.orderDTOToPaymentDTO(orderDTO);

        ObjectMapper mapper = new ObjectMapper();
        String paymentJson = mapper.writeValueAsString(payment);

        WebClient webClient = WebClient.builder().build();

        Mono<String> response = webClient
                .post()
                .uri(paymentServerUrl + "/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paymentJson)
                .retrieve()
                .bodyToMono(String.class);

        if(response != null){
            String res = response.block();
            System.out.println(res);

            String ststus = res.substring(res.indexOf(":")+2, res.indexOf(",")-1);
            System.out.println(ststus);
            String invoiceNumber = res.substring(res.lastIndexOf(":")+1, res.indexOf("}"));

            if(ststus.equals("approved")){
                Payment paymentToMongo = paymentMapper.paymentDTOToPayment(payment);
                paymentToMongo.setInvoiceNumber(invoiceNumber);
                System.out.println(paymentToMongo);
                paymentRepository.save(paymentToMongo);
            }
            else {
                orderDTO.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
            }
            producer.sendMessageAfterCharge(orderDTO);
        }
    }
}
