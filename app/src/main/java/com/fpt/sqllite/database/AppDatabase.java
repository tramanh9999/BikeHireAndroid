package com.fpt.sqllite.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fpt.sqllite.dao.AccountDAO;

@Database(entities = {com.fpt.model.Account.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract AccountDAO accountDAO();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE= Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).
                    allowMainThreadQueries().build();

        }
        return INSTANCE;
    }
    public static void destroyIntance(){
        INSTANCE=null;

    }


}