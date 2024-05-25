package com.dugimto.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message) {
        super("Email already exists in the system: " + message);
    }
}
