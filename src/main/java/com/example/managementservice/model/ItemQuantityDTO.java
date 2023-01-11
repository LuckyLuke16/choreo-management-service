package com.example.managementservice.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class ItemQuantityDTO {

    private HashMap<Integer, Integer> itemsFromShoppingCart;
}
