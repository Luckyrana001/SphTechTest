package com.sphtech.application.model;

/**
 * Created by lucky on 07/06/2017.
 */

public class ErrorResponseModel {

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getDisplayErrorMessage() {
        return displayErrorMessage;
    }

    public void setDisplayErrorMessage(String displayErrorMessage) {
        this.displayErrorMessage = displayErrorMessage;
    }

    public String getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(String fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    String errorCode , errorDescription,displayErrorMessage,fieldErrors;
}
