package com.sap.orderpaymentgreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Payments")
public class Payment {
    @Id
    private String id;
    private String orderId;
    private String customerId;
    private double paymentAmount;
    private String creditCardNumber;
    private String expiryOn;
    private String cvc;
    private String invoiceNumber;
}
