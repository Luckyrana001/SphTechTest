package com.sphtech.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Records {
    public Records() {
    }

    public Double getVolumeOfMobileData() {
        return volumeOfMobileData;
    }

    public void setVolumeOfMobileData(Double volumeOfMobileData) {
        this.volumeOfMobileData = volumeOfMobileData;
    }

    @JsonProperty("volume_of_mobile_data")
    Double volumeOfMobileData;
    @JsonProperty("quarter")
    String quarter;
    @JsonProperty("_id")
    Integer recordId;


    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }


}