package com.learn.springboot.ms.eureka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.springboot.ms.eureka.model.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {

}
