package com.learn.springboot.ms.eureka.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="order_tbl")
public class Order {
	@Id
	private int id;
	private String name;
	private double amount;
	private int qty;

}
