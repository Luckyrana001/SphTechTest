package com.sphtech.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Links {
    public Links(){

    }
    @JsonProperty("start")
    String start;
    @JsonProperty("prev")
    String  prev;
    @JsonProperty("next")
    String  next;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }


}