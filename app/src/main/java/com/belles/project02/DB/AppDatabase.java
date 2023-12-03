package com.belles.project02.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.belles.project02.StoreLog;
import com.belles.project02.User;

@Database(entities = {StoreLog.class, User.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "StoreLog.db";
    public static final String STORELOG_TABLE = "storelog_table";
    public static final String USER_TABLE = "user_table";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract StoreLogDAO StoreLogDAO();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
