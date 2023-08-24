package com.sap.orderpaymentgreen.controller;

import com.sap.orderpaymentgreen.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @GetMapping
    public void getPayment() {
        paymentService.getPayment();
    }
}