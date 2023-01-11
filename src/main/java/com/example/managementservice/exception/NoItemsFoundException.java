package com.example.managementservice.exception;

public class NoItemsFoundException extends RuntimeException{

    public NoItemsFoundException() {
        super("No items found");
    }
}
