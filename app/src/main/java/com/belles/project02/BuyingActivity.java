package com.belles.project02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityBuyingBinding;

import java.util.List;

public class BuyingActivity extends AppCompatActivity {
    private ActivityBuyingBinding binding;

    private TextView mMainDisplay;
    private Button mButton;

    private StoreLogDAO mStoreLogDAO;
    private int userID;
    private User user;

    List<StoreLog> mStoreLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);

        binding = ActivityBuyingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mButton = binding.button1;

        mMainDisplay = binding.mainDisplay;
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod()); // so it can scroll

        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().StoreLogDAO();

        refreshDisplay();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userID = user.getUserID();
                StoreLog log = new StoreLog("button", "a button", 2.00, userID);
                mStoreLogDAO.insert(log);

                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
                refreshDisplay();
            }
        });
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