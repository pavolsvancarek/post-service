package com.amcef.svancarek.testovaciezadanie.postservice.exception;

public class ApiCommunicationException extends RuntimeException {
    public ApiCommunicationException(String message) {
        super(message);
    }
}