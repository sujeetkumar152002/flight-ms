package com.BookingMicroservice.Exceptions;



public class FareMismatchException extends RuntimeException {

    public FareMismatchException(String message) {
        super(message);
    }
}
