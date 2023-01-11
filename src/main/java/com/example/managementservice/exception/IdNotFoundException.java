package com.example.managementservice.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException() {
        super("Could not retrieve ID of user");
    }
}
