package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityCancelBinding;

import java.util.List;
import java.util.Random;

public class CancelActivity extends AppCompatActivity {
    private ActivityCancelBinding mBinding;

    private TextView mMainDisplay;
    private TextView mInstruction;
    private EditText mOrderNumText;
    private Button mButton;
    private Button mSnapButton;

    private int orderNum;

    private StoreLogDAO mStoreLogDAO;
    List<StoreLog> mStoreLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        mBinding = ActivityCancelBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mMainDisplay = mBinding.mainDisplay;
        mInstruction = mBinding.textView2;
        mOrderNumText = mBinding.editTextOrder;
        mButton = mBinding.button3;
        mSnapButton = mBinding.buttonSnap;

        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().StoreLogDAO();

        refreshDisplay();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNumFromDisplay();
                checkForOrderInDatabase();
                refreshDisplay();
            }
        });

        mSnapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snap();
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

    private void getNumFromDisplay() {
        orderNum = Integer.parseInt(mOrderNumText.getText().toString());
    }

    private void checkForOrderInDatabase() {
        List<StoreLog> log = mStoreLogDAO.getLogByID(orderNum);
        if(log == null) {
            Toast.makeText(this, "order number not found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "order cancelled", Toast.LENGTH_SHORT).show();
            mStoreLogDAO.delete(log.get(0));
        }
    }

    private void snap() {
        int min = 1;
        int max = mStoreLogList.size();
        Random rand = new Random();
        for(int i = 0; i < mStoreLogList.size()/2; i++) {
            orderNum = rand.nextInt(max - min) + min;
            checkForOrderInDatabase();
        }
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, CancelActivity.class);
        return intent;
    }
}