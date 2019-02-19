package com.sphtech.application.exception;

/**
 * Created by Thet Paing Tun on 3/2/2018.
 */

public class SessionTimeOutException extends RuntimeException {
    public SessionTimeOutException(String message) {
        super(message);
    }
}
