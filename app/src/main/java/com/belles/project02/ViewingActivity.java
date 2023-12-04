package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityBuyingBinding;
import com.belles.project02.databinding.ActivityViewingBinding;

import java.util.List;

public class ViewingActivity extends AppCompatActivity {
    private ActivityViewingBinding binding;
    private TextView mMainDisplay;
    private Button mButton;

    private int mUserID;
    private StoreLogDAO mStoreLogDAO;
    List<StoreLog> mStoreLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);

        binding = ActivityViewingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMainDisplay = binding.mainDisplay;
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod()); // so it can scroll
        mButton = binding.button4;

        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().StoreLogDAO();

        refreshDisplay();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.intentFactory(getApplicationContext(), mUserID);
                startActivity(intent);
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
        Intent intent = new Intent(packageContext, ViewingActivity.class);
        return intent;
    }
}