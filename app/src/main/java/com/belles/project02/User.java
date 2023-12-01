package com.belles.project02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.belles.project02.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserID;

    private String mUsername;
    private String mPassword;
    //may eventually need strings for first and last name

    private boolean isAdmin;

    public User(String username, String password, boolean admin) {
        mUsername = username;
        mPassword = password;
        isAdmin = admin;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}