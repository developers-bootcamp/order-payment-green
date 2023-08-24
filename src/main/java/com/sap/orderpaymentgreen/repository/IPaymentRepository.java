package com.sap.orderpaymentgreen.repository;

import com.sap.orderpaymentgreen.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends MongoRepository<Payment, String> {
}
