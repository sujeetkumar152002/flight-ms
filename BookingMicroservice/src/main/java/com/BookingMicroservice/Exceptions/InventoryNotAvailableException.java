package com.BookingMicroservice.Exceptions;


public class InventoryNotAvailableException extends RuntimeException {

    public InventoryNotAvailableException(String message) {
        super(message);
    }
}
