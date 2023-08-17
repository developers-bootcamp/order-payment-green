package com.sap.orderpaymentgreen.dto;

import com.sap.orderpaymentgreen.model.PaymentType;
import com.sap.orderpaymentgreen.model.CreditCardDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @Id
    private String id;
    private String customerId;
    private double paymentAmount;
    private OrderStatus status;
    private PaymentType actionType;
    private CreditCardDetails CreditCardDetails;
}
