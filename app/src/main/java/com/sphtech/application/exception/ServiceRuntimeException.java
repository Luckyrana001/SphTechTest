package com.sphtech.application.exception;

/**
 * Created by Thet Paing Tun on 6/2/2018.
 */

public class ServiceRuntimeException extends RuntimeException {
    private String errCode;
    private String errMessages;

    public ServiceRuntimeException(String errCode, String errMessages) {
        this.errCode = errCode;
        this.errMessages = errMessages;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessages() {
        return errMessages;
    }

    public void setErrMessages(String errMessages) {
        this.errMessages = errMessages;
    }


    @Override
    public String toString() {
        return "ServiceRuntimeException{" +
                "errCode='" + errCode + '\'' +
                ", errMessages='" + errMessages + '\'' +
                '}';
    }
}
