package com.taskone.demo.utils.exception;

public class BookingServiceException extends Exception {

    public BookingServiceException() {

    }

    public BookingServiceException(String errorMessage) {
        super(errorMessage);
    }

    public BookingServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
