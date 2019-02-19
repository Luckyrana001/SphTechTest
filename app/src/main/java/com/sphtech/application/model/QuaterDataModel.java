package com.sphtech.application.model;

public class QuaterDataModel {

    public String getQuaterName() {
        return quaterName;
    }

    public void setQuaterName(String quaterName) {
        this.quaterName = quaterName;
    }

    public Double getDataConsumption() {
        return dataConsumption;
    }

    public void setDataConsumption(Double dataConsumption) {
        this.dataConsumption = dataConsumption;
    }

    String quaterName;
    Double dataConsumption;


    /*{
                    "quaterName": "Q1",
                    "dataConsumption": "20000.00"
                }*/
}
