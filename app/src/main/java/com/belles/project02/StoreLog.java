package com.belles.project02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.belles.project02.DB.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.STORELOG_TABLE)
public class StoreLog {
    @PrimaryKey(autoGenerate = true)
    private int mLogID;     // will use for order numbers

    private String mItemName;
    private String mItemDesc;
    private double mPrice;

    private Date mDate;
    private int mUserID;

    public StoreLog(String itemName, String itemDesc, double price, int userID) {
        mItemName = itemName;
        mItemDesc = itemDesc;
        mPrice = price;

        mDate = new Date();

        mUserID = userID;
    }

    @Override
    public String toString() {
        return "Order #" + mLogID + "\n" +
                "Item: " + mItemName + "\n" +
                "Desc: " + mItemDesc + "\n" +
                "Price: $" + mPrice + "\n" +
                "Date: " + mDate + "\n" +
                "=-=-=-=-=-=-=-=-=-=\n";    // separate orders on display
    }

    public int getLogID() {
        return mLogID;
    }

    public void setLogID(int logID) {
        mLogID = logID;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getItemDesc() {
        return mItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        mItemDesc = itemDesc;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }
}
