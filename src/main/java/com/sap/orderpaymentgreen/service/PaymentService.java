package com.sap.orderpaymentgreen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.OrderStatus;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.mapper.OrderMapper;
import com.sap.orderpaymentgreen.mapper.PaymentMapper;
import com.sap.orderpaymentgreen.model.Payment;
import com.sap.orderpaymentgreen.model.PaymentType;
import com.sap.orderpaymentgreen.model.CreditCardDetails;
import com.sap.orderpaymentgreen.repository.IPaymentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    public PaymentMapper paymentMapper;

    @Autowired
    private IPaymentRepository paymentRepository;
    @SneakyThrows
    public void getPayment(){

        String paymentServerUrl = "http://b77cb14d-0f20-480f-be00-20fb4d64536f.mock.pstmn.io";

        //get from rabbit
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(OrderStatus.CANCEL);
        //

        PaymentDTO payment = new PaymentDTO(100,new CreditCardDetails("4580 7528 0173 1234", "02/36", "576"), PaymentType.CREDIT);

        ObjectMapper mapper = new ObjectMapper();

        String paymentJson = mapper.writeValueAsString(payment);

        //PaymentDTO payment = orderMapper.orderDTOtoPaymentDTO(orderDTO);
        WebClient webClient = WebClient.builder().build();

        Mono<String> response = null;

        if(orderDTO.getStatus().equals(OrderStatus.APPROVE)) {
            response = webClient
                    .post()
                    .uri(paymentServerUrl + "/debit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(paymentJson)
                    .retrieve()
                    .bodyToMono(String.class);
        }
        if(orderDTO.getStatus().equals(OrderStatus.CANCEL))
        {
            response = webClient
                    .post()
                    .uri(paymentServerUrl + "/credit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(paymentJson)
                    .retrieve()
                    .bodyToMono(String.class);
        }

        if(response != null){
            //enter to CompletedPayment

            String res = response.block();

            String status = res.substring(res.indexOf(":") + 1, res.indexOf(","));

            String invoiceNumber = res.substring(res.lastIndexOf(":") + 1, res.indexOf("}"));

            if(status.equals("approved")){
                Payment paymentToMongo = paymentMapper.paymentDTOtoPayment(payment);
                //
                paymentToMongo.setId("???");
                paymentToMongo.setInvoiceNumber(invoiceNumber);
                paymentRepository.save(paymentToMongo);
            }
        }
      }
}

