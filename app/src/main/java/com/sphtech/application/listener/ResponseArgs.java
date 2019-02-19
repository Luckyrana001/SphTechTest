package com.sphtech.application.listener;

import com.sphtech.application.common.RequestType;
import com.sphtech.application.common.ResponseStatus;

public class ResponseArgs {

    public Object args;
    public ResponseStatus responseStatus;
    public RequestType requestType;

    public ResponseArgs(Object args, ResponseStatus responseStatus, RequestType requestType) {
        this.args = args;
        this.responseStatus = responseStatus;
        this.requestType = requestType;
    }
}
