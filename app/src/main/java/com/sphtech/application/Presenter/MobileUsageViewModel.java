package com.sphtech.application.Presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sphtech.application.common.BaseFlyContext;
import com.sphtech.application.common.DialogModel;
import com.sphtech.application.common.LCEStatus;
import com.sphtech.application.common.Logger;
import com.sphtech.application.db.AppDatabase;
import com.sphtech.application.db.dao.RecordsDao;
import com.sphtech.application.db.entity.YearlyRecordsData;
import com.sphtech.application.exception.ServiceRuntimeException;
import com.sphtech.application.model.MobileDataConsumptionYearlyModel;
import com.sphtech.application.model.YearDataModel;
import com.sphtech.application.services.IRemoteServices;

import java.util.ArrayList;
import java.util.List;

public class MobileUsageViewModel extends ViewModel {
    private IRemoteServices mRemoteServices;


    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> prompt = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DialogModel> mlDialog = new MutableLiveData<>();
    private MutableLiveData<ArrayList<YearDataModel>> mlYearlyDataConsumption = new MutableLiveData<>();
    private RecordsDao recordsDao;

    public MobileUsageViewModel(IRemoteServices rservice) {

        Logger.d("vm constructor");
        mRemoteServices = rservice;
        recordsDao= AppDatabase.getInstance(BaseFlyContext.getInstant().getApplicationContext()).userDao();
        LCEStatus.loading("Loading Mobile Usage Data ...");

        getDataFromAPiOrCacheOrFromDb();



    }

    private void insertDataIntoDB(String result) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                recordsDao.deleteAll();

                YearlyRecordsData user = new YearlyRecordsData();
                user.setRecords(result);
                recordsDao.insertAll(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //mlWarningStatus.setValue("table data iserted.");

            }
        }.execute();
    }

    private void getDataFromDb() {
        new AsyncTask<Void, Void, ArrayList<YearDataModel>>() {
            @Override
            protected ArrayList<YearDataModel>  doInBackground(Void... params) {
                try {
                    List<YearlyRecordsData> data = recordsDao.getAll();
                    YearlyRecordsData value = data.get(0);
                    String records = value.getRecords();
                    MobileDataConsumptionYearlyModel recordData = new Gson().fromJson(records, MobileDataConsumptionYearlyModel.class);
                    ArrayList<YearDataModel> getYearlyData = recordData.getYearlyData();

                    return getYearlyData;
                }catch (Exception e){
                    return  null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<YearDataModel> getYearlyData) {


                if(getYearlyData==null || getYearlyData.size()==0){
                    //mlWarningStatus.setValue("table data updated.");
                    mlLceStatus.postValue(LCEStatus.error("Data Load Failed", "Please check your internet connection and retry again."));
                    LCEStatus.success();
                }else{
                    LCEStatus.success();
                    mlYearlyDataConsumption.setValue(getYearlyData);
                }
            }
        }.execute();
    }

    public void getDataFromAPiOrCacheOrFromDb() {
        mRemoteServices
                .getMobileDataUsage("adf")
                .doOnSubscribe(disposable -> LCEStatus.loading("Loading Mobile Usage Data ..."))
                .doOnTerminate(() -> LCEStatus.success())
                .subscribe(result -> {
                    Logger.d("Load Data Result =>" + result);
                    mlWarningStatus.postValue("Data Loaded Successfuly.");
                    mlYearlyDataConsumption.setValue(result.getYearlyData());

                    insertDataIntoDB(new Gson().toJson(result));

                }, throwable -> {

                    if (throwable instanceof ServiceRuntimeException) {
                        ServiceRuntimeException ex = (ServiceRuntimeException) throwable;
                        Logger.d("data loading error =>" + ex.getErrMessages());
                        getDataFromDb();

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
