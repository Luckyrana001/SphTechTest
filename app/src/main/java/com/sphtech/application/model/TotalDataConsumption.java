package com.sphtech.application.model;

import java.util.ArrayList;

public class TotalDataConsumption {


    ArrayList<String> quaterDataVolume;

    String totalDataConsumption;
    boolean isThereDecreaseInVolumeOfData;


    public ArrayList<String> getQuaterDataVolume() {
        return quaterDataVolume;
    }

    public void setQuaterDataVolume(ArrayList<String> quaterDataVolume) {
        this.quaterDataVolume = quaterDataVolume;
    }

    public String getTotalDataConsumption() {
        return totalDataConsumption;
    }

    public void setTotalDataConsumption(String totalDataConsumption) {
        this.totalDataConsumption = totalDataConsumption;
    }

    public boolean isThereDecreaseInVolumeOfData() {
        return isThereDecreaseInVolumeOfData;
    }

    public void setThereDecreaseInVolumeOfData(boolean thereDecreaseInVolumeOfData) {
        isThereDecreaseInVolumeOfData = thereDecreaseInVolumeOfData;
    }


}
