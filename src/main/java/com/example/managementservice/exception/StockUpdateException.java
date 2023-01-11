package com.example.managementservice.exception;

public class StockUpdateException extends RuntimeException {

    public StockUpdateException(){super("Stock could not be updated");}
}
