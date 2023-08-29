package com.sap.orderpaymentgreen.mapper;

import com.sap.orderpaymentgreen.dto.OrderDTO;
import com.sap.orderpaymentgreen.dto.PaymentDTO;
import com.sap.orderpaymentgreen.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IPaymentMapper {

    IPaymentMapper INSTANCE = Mappers.getMapper(IPaymentMapper.class);

    @Mapping(source = "id", target = "orderId")
    Payment orderDTOToPayment(OrderDTO orderDTO);
}
