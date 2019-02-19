package com.sphtech.application.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sphtech.application.db.dao.RecordsDao;
import com.sphtech.application.db.entity.YearlyRecordsData;


@Database(entities = {YearlyRecordsData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    /**
     * from developers android, made my own singleton
     *
     * @param context
     * @return
     */
    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name").build();
        }
        return appDatabase;
    }

    public abstract RecordsDao userDao();
}