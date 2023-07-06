package com.ek.management.v1.exception;

public class InvalidIdentifierException extends RuntimeException {

    public InvalidIdentifierException() {
        super("The identifier sent is invalid.");
    }
}
