package com.sap.orderpaymentgreen.dto;

import com.sap.orderpaymentgreen.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

        @Id
        private String id;
        private String customerId;
        private double paymentAmount;
        private OrderStatus orderStatus;
        private Boolean notificationFlag;
        private String creditCardNumber;
        private String expiryOn;
        private String cvc;
}
