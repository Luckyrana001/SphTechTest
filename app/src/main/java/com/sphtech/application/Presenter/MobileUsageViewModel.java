package com.sphtech.application.Presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sphtech.application.common.DialogModel;
import com.sphtech.application.common.LCEStatus;
import com.sphtech.application.common.Logger;
import com.sphtech.application.exception.ServiceRuntimeException;
import com.sphtech.application.model.YearDataModel;
import com.sphtech.application.services.IRemoteServices;

import java.util.ArrayList;

public class MobileUsageViewModel extends ViewModel {
    private IRemoteServices mRemoteServices;


    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> prompt = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DialogModel> mlDialog = new MutableLiveData<>();
    private MutableLiveData<ArrayList<YearDataModel>> mlYearlyDataConsumption = new MutableLiveData<>();


    public MobileUsageViewModel(IRemoteServices rservice) {

        Logger.d("vm constructor");
        mRemoteServices = rservice;


        mRemoteServices
                .getMobileDataUsage("adf")
                .doOnSubscribe(disposable -> LCEStatus.loading("Loading Mobile Usage Data ..."))
                .doOnTerminate(() -> LCEStatus.success())
                .subscribe(result -> {
                    Logger.d("Load Data Result =>" + result);
                    mlWarningStatus.postValue("Data Loaded Successfuly.");

                    mlYearlyDataConsumption.setValue(result.getYearlyData());


                }, throwable -> {

                    if (throwable instanceof ServiceRuntimeException) {
                        ServiceRuntimeException ex = (ServiceRuntimeException) throwable;
                        Logger.d("data loading error =>" + ex.getErrMessages());
                        mlLceStatus.postValue(LCEStatus.error("Data Load Failed", ex.getErrMessages()));
                    } else {
                        mlLceStatus.postValue(LCEStatus.error("Data Error", "Data Load Failed"));
                    }


                    Logger.d("verify device id error =>" + throwable.getMessage());
                });

    }


    public LiveData<LCEStatus> getLceStatus() {
        return mlLceStatus;
    }

    public LiveData<String> getPrompt() {
        return prompt;
    }

    public LiveData<String> getWarning() {
        return mlWarningStatus;
    }

    public LiveData<DialogModel> getDialog() {
        return mlDialog;
    }


    public LiveData<ArrayList<YearDataModel>> getMobileDataUsageData() {
        return mlYearlyDataConsumption;
    }
}
