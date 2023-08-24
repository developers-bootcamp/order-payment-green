package com.sap.orderpaymentgreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@SpringBootApplication
public class OrderPaymentGreenApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrderPaymentGreenApplication.class, args);
	}

}
