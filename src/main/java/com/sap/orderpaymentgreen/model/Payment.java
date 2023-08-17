package com.sap.orderpaymentgreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Payment")
public class Payment {
    @Id
    private String id;
    private double paymentAmount;
    private CreditCardDetails CreditCardDetails;
    private PaymentType paymentType;
    private int invoiceNumber;
}
