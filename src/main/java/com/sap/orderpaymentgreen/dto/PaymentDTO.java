package com.sap.orderpaymentgreen.dto;

import com.sap.orderpaymentgreen.model.CreditCardDetails;
import com.sap.orderpaymentgreen.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private double paymentAmount;
    private CreditCardDetails CreditCardDetails;
    private PaymentType paymentType;
}
