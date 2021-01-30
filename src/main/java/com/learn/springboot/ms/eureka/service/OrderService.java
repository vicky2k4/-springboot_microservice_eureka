package com.learn.springboot.ms.eureka.service;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springboot.ms.eureka.common.Payment;
import com.learn.springboot.ms.eureka.common.TransactionRequest;
import com.learn.springboot.ms.eureka.common.TransactionResponse;
import com.learn.springboot.ms.eureka.model.Order;
import com.learn.springboot.ms.eureka.repository.OrderRepository;


@Service
@RefreshScope
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	//@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	//private String ENDPOINT_URL;
	
	private Logger log = LoggerFactory.getLogger(OrderService.class);
	
	public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {
		String response = "";
		Order order = transactionRequest.getOrder();
		Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getAmount());
        
        log.info("Order Service Request saveOrder {}-->"+new ObjectMapper().writeValueAsString(transactionRequest));

		//Payment paymentResponseFromAPI = restTemplate.postForObject("http://localhost:9191/payment/savePayment", payment, Payment.class);
        Payment paymentResponseFromAPI = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/savePayment", payment, Payment.class);
		//Payment paymentResponseFromAPI = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
		
		log.info("Payment API call from Order Service Request saveOrder {}-->",new ObjectMapper().writeValueAsString(paymentResponseFromAPI));
		
		response = paymentResponseFromAPI.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";
		orderRepository.save(order);
		
		return new TransactionResponse(order,paymentResponseFromAPI.getAmount(),paymentResponseFromAPI.getTransactionId(),response);
	}
	

}
