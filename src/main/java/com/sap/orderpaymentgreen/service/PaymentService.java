package com.sap.orderpaymentgreen.service;

import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.OrderStatus;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.model.Payment;
import com.sap.orderpaymentgreen.model.PaymentType;
import com.sap.orderpaymentgreen.model.CreditCardDetails;
import com.sap.orderpaymentgreen.repository.IPaymentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

//    private WebClient webClient;
//
//    public PaymentService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("https://b77cb14d-0f20-480f-be00-20fb4d64536f.mock.pstmn.io").build();
//    }

    @Autowired
    private IPaymentRepository paymentRepository;
    @SneakyThrows
    public void getPayment(){

        WebClient webClient = WebClient.builder().build();

        String Url = "https://b77cb14d-0f20-480f-be00-20fb4d64536f.mock.pstmn.io";

        //get from rabbit
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(OrderStatus.APPROVE);
        //

        PaymentDTO payment = new PaymentDTO(100,new CreditCardDetails("4580 7528 0173 1234", "02/36", "576"), PaymentType.CREDIT);

//      PaymentDTO payment = new Payment(orderDTO.getPaymentAmount(), orderDTO.getActionType(), orderDTO.getCreditCardDetails());

        Mono<String> response = null;

        if(orderDTO.getStatus().equals(OrderStatus.APPROVE)) {
            response = webClient
                    .post()
                    .uri(Url + "/debit")
                    .body(Mono.just(payment),PaymentDTO.class)
                    .retrieve()
                    .bodyToMono(String.class);

        }
        if(orderDTO.getStatus().equals(OrderStatus.CANCEL))
        {
            response = webClient
                    .post()
                    .uri(Url + "/credit")
                    .bodyValue(payment)
                    .retrieve()
                    .bodyToMono(String.class);

        }

        if(response != null){
            //enter to CompletedPayment
            System.out.println("to CompletedPayment queue");
            Payment paymentToMongo = new Payment("???", payment.getPaymentAmount(), payment.getCreditCardDetails(), payment.getPaymentType(), 11111);
            paymentRepository.save(paymentToMongo);
        }
        response.subscribe(
                data -> {
                    System.out.println("The content is: " + data);
                },
                error -> {
                    System.out.println("An error occurred: " + error);
                },
                () -> {
                    System.out.println("The Mono has completed successfully.");
                }
        );
      }
}

