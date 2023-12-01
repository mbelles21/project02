package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityBuyingBinding;

import java.util.List;

public class BuyingActivity extends AppCompatActivity {
    private ActivityBuyingBinding binding;

    private TextView mMainDisplay;

    private StoreLogDAO mStoreLogDAO;

    List<StoreLog> mStoreLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);

        binding = ActivityBuyingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMainDisplay = binding.mainDisplay;
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod()); // so it can scroll

        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().StoreLogDAO();

        refreshDisplay();
    }

    private void refreshDisplay() {
        mStoreLogList = mStoreLogDAO.getStoreLogs();
        if(!mStoreLogList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(StoreLog log : mStoreLogList) {
                sb.append(log.toString());
            }
            mMainDisplay.setText(sb.toString());
        } else {
            mMainDisplay.setText("No logs yet.");
        }
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, BuyingActivity.class);
        return intent;
    }
}