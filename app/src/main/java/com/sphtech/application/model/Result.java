package com.sphtech.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties
public class Result {

    public  Result(){

    }


    @JsonProperty("resource_id")
    String resourceId;

    @JsonProperty("fields")
    ArrayList<Fields> fields;


    @JsonProperty("records")
    ArrayList<Records> records;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("_links")
    Links links;

    @JsonProperty("offset")
    Integer offset;
    @JsonProperty("limit")
    Integer limit;
    @JsonProperty("total")
    Integer total;



    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public ArrayList<Fields> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Fields> fields) {
        this.fields = fields;
    }

    public ArrayList<Records> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Records> records) {
        this.records = records;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
