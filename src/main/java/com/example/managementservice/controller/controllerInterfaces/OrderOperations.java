package com.example.managementservice.controller.controllerInterfaces;

import com.example.managementservice.model.OrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public interface OrderOperations {

    @PostMapping
    void createOrder(@RequestBody OrderDTO orderDetails);
}
