package com.learn.springboot.ms.eureka.common;

import com.learn.springboot.ms.eureka.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

	private Payment payment;
	private Order order;
}
