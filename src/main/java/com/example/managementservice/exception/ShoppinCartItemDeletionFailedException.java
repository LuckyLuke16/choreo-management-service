package com.example.managementservice.exception;

public class ShoppinCartItemDeletionFailedException extends RuntimeException {
    public ShoppinCartItemDeletionFailedException(int itemId) {
        super("Deletion of item with id: " + itemId + " failed");
    }
}
