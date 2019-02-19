package com.sphtech.application.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sphtech.application.db.entity.YearlyRecordsData;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM YearlyRecordsData")
    List<YearlyRecordsData> getAll();

    /*@Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);*/

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/

    @Insert
    void insertAll(YearlyRecordsData... users);

    @Delete
    void delete(YearlyRecordsData user);

    @Query("DELETE FROM YearlyRecordsData")
    void deleteAll();
}