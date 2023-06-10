package com.umabikerental.exception;

public class NotEnoughBikesAvailableException extends RuntimeException {
    public NotEnoughBikesAvailableException(String message) {
        super(message);
    }
}
