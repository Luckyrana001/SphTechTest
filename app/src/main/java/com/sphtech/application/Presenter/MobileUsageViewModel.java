package com.sphtech.application.Presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sphtech.application.common.DialogModel;
import com.sphtech.application.common.LCEStatus;
import com.sphtech.application.common.Logger;
import com.sphtech.application.exception.ServiceRuntimeException;
import com.sphtech.application.services.IRemoteServices;

public class MobileUsageViewModel  extends ViewModel {
    private IRemoteServices mRemoteServices;


    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> prompt = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DialogModel> mlDialog = new MutableLiveData<>();
    private MutableLiveData<String> mlTotalDataConsumption = new MutableLiveData<>();
    private MutableLiveData<Boolean> mlisThereDecreaseInQuaterlyConsumption = new MutableLiveData<>();


    public MobileUsageViewModel( IRemoteServices rservice) {

        Logger.d("vm constructor");
        mRemoteServices = rservice;



        mRemoteServices
                .getMobileDataUsage("adf")
                .doOnSubscribe(disposable -> LCEStatus.loading("Loading Mobile Usage Data ..."))
                .doOnTerminate(() -> LCEStatus.success())
                .subscribe(result -> {
                    Logger.d("Load Data Result =>" + result);
                    mlWarningStatus.postValue("Data Loaded Successfuly.");
                    mlTotalDataConsumption.setValue(result.getTotalDataConsumption());
                    mlisThereDecreaseInQuaterlyConsumption.postValue(result.isThereDecreaseInVolumeOfData());
                    //Toast.makeText(this,new Gson().toJson(result),Toast.LENGTH_LONG).show();

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

    public LiveData<Boolean> isThereDecreaseInQuaterlyConsumption() {
        return mlisThereDecreaseInQuaterlyConsumption;
    }

    public LiveData<String> getTotalDataConsumption() {
        return mlTotalDataConsumption;
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
}
