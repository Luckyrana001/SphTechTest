package com.sphtech.application.model;

import java.util.ArrayList;

public class YearDataModel {


    String year;
    Double totalYearlyataConsumption;

    Boolean isthereDecreaseInVolume;

    ArrayList<QuaterDataModel> quaterlyData;


    public Boolean getIsthereDecreaseInVolume() {
        return isthereDecreaseInVolume;
    }

    public void setIsthereDecreaseInVolume(Boolean isthereDecreaseInVolume) {
        this.isthereDecreaseInVolume = isthereDecreaseInVolume;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<QuaterDataModel> getQuaterlyData() {
        return quaterlyData;
    }

    public void setQuaterlyData(ArrayList<QuaterDataModel> quaterlyData) {
        this.quaterlyData = quaterlyData;
    }

    public Double getTotalYearlyataConsumption() {
        return totalYearlyataConsumption;
    }

    public void setTotalYearlyataConsumption(Double totalYearlyataConsumption) {
        this.totalYearlyataConsumption = totalYearlyataConsumption;
    }


    /* {
            "year": "2016",
            "quaterlyData": [
                {
                    "quaterName": "Q1",
                    "dataConsumption": "20000.00"
                },
                {
                    "quaterName": "Q2",
                    "dataConsumption": "20000.00"
                },
                {
                    "quaterName": "Q3",
                    "dataConsumption": "20000.00"
                },
                {
                    "quaterName": "Q4",
                    "dataConsumption": "20000.00"
                }
            ]
        }*/

}
