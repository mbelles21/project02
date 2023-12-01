package com.belles.project02.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.belles.project02.StoreLog;
import com.belles.project02.User;

import java.util.List;

@Dao
public interface StoreLogDAO {

    @Insert
    void insert(StoreLog... storeLogs);

    @Update
    void update(StoreLog... storeLogs);

    @Delete
    void delete(StoreLog storeLog);

    @Query("SELECT * FROM " + AppDatabase.STORELOG_TABLE + " ORDER BY mDate desc")
    List<StoreLog> getStoreLogs();

    @Query("SELECT * FROM " + AppDatabase.STORELOG_TABLE + " WHERE mLogID = :logID")
    List<StoreLog> getLogByID(int logID);

    //user stuff
    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserID = :userID")
    User getUserByUserID(int userID);

}
