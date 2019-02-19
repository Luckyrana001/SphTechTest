package com.sphtech.application.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class YearlyRecordsData {
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "records")
    private String records;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }



    @Override
    public String toString() {
        return "\nUser{" +
                "uid=" + uid +
                ", records='" + records + '\'' +

                '}' + "\n\n";
    }
}