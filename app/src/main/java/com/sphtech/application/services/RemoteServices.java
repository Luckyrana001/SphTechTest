package com.sphtech.application.services;

import android.content.Context;


import com.sphtech.application.common.RequestType;
import com.sphtech.application.exception.ServiceRuntimeException;
import com.sphtech.application.model.ErrorResponseModel;
import com.sphtech.application.model.TotalDataConsumption;

import io.reactivex.Observable;


/**
 * Created by Thet Paing Tun on 2/2/2018.
 */

public class RemoteServices implements IRemoteServices {

    private Context mContext;

    public RemoteServices(Context mContext) {
        this.mContext = mContext;


 }

    @Override
    public Observable<TotalDataConsumption> getMobileDataUsage(String text) {

        return Observable.create(emitter -> {
            RequestHandler.getRequestHandler()
                    .getMobileUsageDataRequest(responseArgs -> {
                        if (responseArgs.requestType == RequestType.getMobileDataUsage) {
                            TotalDataConsumption response = (TotalDataConsumption) responseArgs.args;

                            emitter.onNext(response);
                            emitter.onComplete();

                        } else if (responseArgs.requestType == RequestType.errorResponse) {
                            ErrorResponseModel errModel = (ErrorResponseModel) responseArgs.args;
                            if (errModel == null) {
                                ServiceRuntimeException ex = new ServiceRuntimeException("0", "Unknown Error");
                                emitter.onError(ex);
                            } else {
                                if(errModel.getDisplayErrorMessage()!=null && !errModel.getDisplayErrorMessage().equals("")){
                                    ServiceRuntimeException ex = new ServiceRuntimeException(errModel.getErrorCode(), errModel.getDisplayErrorMessage());
                                    emitter.onError(ex);
                                }else{
                                    ServiceRuntimeException ex = new ServiceRuntimeException(errModel.getErrorCode(), errModel.getErrorDescription());
                                    emitter.onError(ex);
                                }
                            }

                        }
                    }, text);
        });
    }
}

