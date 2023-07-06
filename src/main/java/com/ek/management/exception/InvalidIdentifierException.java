package com.ek.management.exception;

public class InvalidIdentifierException extends RuntimeException {

    public InvalidIdentifierException() {
        super("The identifier sent is invalid.");
    }
}
