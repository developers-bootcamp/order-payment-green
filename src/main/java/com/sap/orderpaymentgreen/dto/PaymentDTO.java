package com.sap.orderpaymentgreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private double paymentAmount;
    private String creditCardNumber;
    private String expiryOn;
    private String cvc;
}




