package com.sphtech.application.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class LCEStatus {
    @NonNull
    private Status status;
    @Nullable
    private String msg;

    private String title;

    private LCEStatus(@NonNull Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public LCEStatus(@NonNull Status status, String msg, String title) {
        this.status = status;
        this.msg = msg;
        this.title = title;
    }

    public static LCEStatus error(String errMsg) {
        return new LCEStatus(Status.ERROR, errMsg);
    }

    public static LCEStatus error(String title, String errMsg) {
        return new LCEStatus(Status.ERROR, errMsg, title);
    }

    public static LCEStatus loading(String msg) {
        return new LCEStatus(Status.LOADING, msg);
    }

    public static LCEStatus success() {
        return new LCEStatus(Status.SUCCESS, null);
    }

    public static LCEStatus timeout(String msg) {
        return new LCEStatus(Status.TIME_OUT, msg);
    }

    ;


    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public String getMsg() {
        return msg;
    }

    public String getTitle() {
        return title;
    }

    public enum Status {
        LOADING, SUCCESS, ERROR, NORM, TIME_OUT
    }

}
