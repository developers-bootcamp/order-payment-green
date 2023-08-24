package com.sap.orderpaymentgreen.mapper;

import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.model.Payment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment paymentDTOtoPayment(PaymentDTO paymentDTO);
}
