package com.example.managementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {

    private String paymentMethod;

    private Address address;
}
