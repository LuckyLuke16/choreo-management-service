package com.example.managementservice.exception;

public class ShoppingCartContentNotFoundException extends RuntimeException {

    public ShoppingCartContentNotFoundException() {
        super("No items found in shopping cart");
    }
}
