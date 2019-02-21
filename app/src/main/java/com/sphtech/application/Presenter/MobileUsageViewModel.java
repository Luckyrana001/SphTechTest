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
import com.sphtech.application.common.Utils;
import com.sphtech.application.db.AppDatabase;
import com.sphtech.application.db.dao.RecordsDao;
import com.sphtech.application.db.entity.YearlyRecordsData;
import com.sphtech.application.exception.ServiceRuntimeException;
import com.sphtech.application.model.MobileDataConsumptionYearlyModel;
import com.sphtech.application.model.QuaterDataModel;
import com.sphtech.application.model.Records;
import com.sphtech.application.model.YearDataModel;
import com.sphtech.application.services.IRemoteServices;

import java.util.ArrayList;
import java.util.List;

import static com.sphtech.application.MobileDataUsageConstants.START_REQUEST;

public class MobileUsageViewModel extends ViewModel {
    private IRemoteServices mRemoteServices;


    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> prompt = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DialogModel> mlDialog = new MutableLiveData<>();
    private MutableLiveData<ArrayList<YearDataModel>> mlYearlyDataConsumption = new MutableLiveData<>();
    private RecordsDao recordsDao;

    public MobileUsageViewModel(IRemoteServices rservice) {

//        Logger.d("vm constructor");
        mRemoteServices = rservice;
        recordsDao= AppDatabase.getInstance(BaseFlyContext.getInstant().getApplicationContext()).userDao();
        LCEStatus.loading("Loading Mobile Usage Data ...");

        getDataFromAPiOrCacheOrFromDb();



    }

    public void insertDataIntoDB(String result) {
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
            protected void onPostExecute(Void result) {
              System.out.print("Table data deleted");
            }
        }.execute();
    }

    public void getDataFromDb() {
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


                if(getYearlyData==null || getYearlyData.isEmpty()){
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
                .getMobileDataUsage(START_REQUEST)
                .doOnSubscribe(disposable -> LCEStatus.loading("Loading Mobile Usage Data ..."))
                .doOnTerminate(() -> LCEStatus.success())
                .subscribe(result -> {
                    Logger.d("Load Data Result =>" + result);

                    ArrayList<Records> records = result.getResult().getRecords();
                    MobileDataConsumptionYearlyModel mobileDataConsumptionYearlyModel = new MobileDataConsumptionYearlyModel();
                    ArrayList<YearDataModel> yearDataModelArrayList = new ArrayList<>();
                    ArrayList<QuaterDataModel> quaterDataModelArrayList = new ArrayList<QuaterDataModel>();
                    String yearName = "";

                    for(int i=0;i<records.size();i++){
                        Records record = records.get(i);
                        String  recYear = Utils.getYear(record.getQuarter());
                        String  recQuater = Utils.getQuater(record.getQuarter());
                        Double dataConsumed = record.getVolumeOfMobileData();
                        YearDataModel   yearDataModel = new YearDataModel();


                        if(Integer.parseInt(recYear)>=2008) {
                            if(yearName.equals("")){
                                yearName  =   recYear;
                            }


                            if(yearName.equals(recYear)){
                                QuaterDataModel quaterDataModel = new QuaterDataModel();
                                quaterDataModel.setQuaterName(recQuater);
                                quaterDataModel.setDataConsumption(dataConsumed);
                                quaterDataModelArrayList.add(quaterDataModel);
                                yearDataModel.setYear(yearName);
                                if(i==(records.size()-1)){

                                    // calculate total data consumption
                                    Double totalDataConumption = 0.0;
                                    for(QuaterDataModel dataModel:quaterDataModelArrayList){
                                        totalDataConumption = totalDataConumption + dataModel.getDataConsumption();
                                    }
                                    // verifying if any quarter in a year demonstrates a decrease in volume data.
                                    boolean isDataVolumeDecreaseFound = getIsDataVolumeDecreaseFound(quaterDataModelArrayList);
                                    yearDataModel.setTotalYearlyataConsumption(totalDataConumption);
                                    yearDataModel.setIsthereDecreaseInVolume(isDataVolumeDecreaseFound);
                                    yearDataModel.setQuaterlyData(quaterDataModelArrayList);
                                    yearDataModelArrayList.add(yearDataModel);
                                    mobileDataConsumptionYearlyModel.setYearlyData(yearDataModelArrayList);

                                }


                            } else {


                                // calculate total data consumption
                                Double totalDataConumption = 0.0;
                                for(QuaterDataModel quaterDataModel:quaterDataModelArrayList){
                                    totalDataConumption = totalDataConumption + quaterDataModel.getDataConsumption();
                                }

                                // verifying if any quarter in a year demonstrates a decrease in volume data.
                                boolean isDataVolumeDecreaseFound = getIsDataVolumeDecreaseFound(quaterDataModelArrayList);
                                // updating Yearly data model
                                yearDataModel.setIsthereDecreaseInVolume(isDataVolumeDecreaseFound);
                                yearDataModel.setYear(yearName);
                                yearDataModel.setTotalYearlyataConsumption(totalDataConumption);
                                yearDataModel.setQuaterlyData(quaterDataModelArrayList);
                                yearDataModelArrayList.add(yearDataModel);
                                quaterDataModelArrayList= new ArrayList<QuaterDataModel>();
                                yearName = recYear;

                                QuaterDataModel  quaterDataModel = new QuaterDataModel();
                                quaterDataModel.setQuaterName(recQuater);
                                quaterDataModel.setDataConsumption(dataConsumed);
                                quaterDataModelArrayList.add(quaterDataModel);

                                if(i==(records.size()-1)){
                                    yearDataModel.setYear(yearName);
                                    yearDataModel.setQuaterlyData(quaterDataModelArrayList);
                                    yearDataModelArrayList.add(yearDataModel);
                                    mobileDataConsumptionYearlyModel.setYearlyData(yearDataModelArrayList);

                                }


                            }

                        }

                    }







                    mlWarningStatus.postValue("Data Loaded Successfuly.");
                    mlYearlyDataConsumption.setValue(mobileDataConsumptionYearlyModel.getYearlyData());

                    insertDataIntoDB(new Gson().toJson(result));

                }, throwable -> {

                    if (throwable instanceof ServiceRuntimeException) {
                        ServiceRuntimeException ex = (ServiceRuntimeException) throwable;
                        Logger.d("data loading error =>" + ex.getErrMessages());
                        getDataFromDb();

                    } else {
                        mlLceStatus.postValue(LCEStatus.error("Data Error", "Data Load Failed"));
                    }
                    Logger.d("verify  error =>" + throwable.getMessage());
                });
    }

    private boolean getIsDataVolumeDecreaseFound(ArrayList<QuaterDataModel> quaterDataModelArrayList) {
        boolean isDataVolumeDecreaseFound = false;
        if(quaterDataModelArrayList.size()==2){
            Double Q1Volume = quaterDataModelArrayList.get(0).getDataConsumption();
            Double Q2Volume = quaterDataModelArrayList.get(0).getDataConsumption();
            if(Q1Volume>Q2Volume){
                isDataVolumeDecreaseFound = true;
            }
        }
        else if(quaterDataModelArrayList.size()==3){
            Double Q1Volume = quaterDataModelArrayList.get(0).getDataConsumption();
            Double Q2Volume = quaterDataModelArrayList.get(1).getDataConsumption();
            Double Q3Volume = quaterDataModelArrayList.get(2).getDataConsumption();
            if(Q1Volume>Q2Volume || Q2Volume>Q3Volume){
                isDataVolumeDecreaseFound = true;
            }
        }
        else if(quaterDataModelArrayList.size()==4){
            Double Q1Volume = quaterDataModelArrayList.get(0).getDataConsumption();
            Double Q2Volume = quaterDataModelArrayList.get(1).getDataConsumption();
            Double Q3Volume = quaterDataModelArrayList.get(2).getDataConsumption();
            Double Q4Volume = quaterDataModelArrayList.get(3).getDataConsumption();

            if(Q1Volume>Q2Volume || Q2Volume>Q3Volume || Q3Volume>Q4Volume){
                isDataVolumeDecreaseFound = true;
            }
        }
        return isDataVolumeDecreaseFound;
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
