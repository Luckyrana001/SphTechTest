package com.sphtech.application.exception;


public class SessionTimeOutException extends RuntimeException {
    public SessionTimeOutException(String message) {
        super(message);
    }
}
