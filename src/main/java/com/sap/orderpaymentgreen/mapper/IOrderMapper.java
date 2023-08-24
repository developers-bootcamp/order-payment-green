package com.sap.orderpaymentgreen.mapper;

import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IOrderMapper {
    PaymentDTO orderDTOToPaymentDTO(OrderDTO orderDTO);
}
