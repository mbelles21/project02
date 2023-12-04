package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String USER_ID_KEY = "com.belles.project02.userIDKey";
    private static final String PREFERENCES_KEY = "com.belles.project02.preferencesKey";

    private ActivityCancelBinding mBinding;

    private TextView mMainDisplay;
    private TextView mInstruction;
    private EditText mOrderNumText;
    private Button mButton;
    private Button mSnapButton;
    private Button backButton;

    private int orderNum;
    private int mUserID;
    private User mUser;

    private SharedPreferences mPreferences = null;

    private StoreLogDAO mStoreLogDAO;
    List<StoreLog> mStoreLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        getDatabase();
        checkForUser();
        addUserToPreference(mUserID);
        loginUser(mUserID);

        mBinding = ActivityCancelBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mMainDisplay = mBinding.mainDisplay;
        mInstruction = mBinding.textView2;
        mOrderNumText = mBinding.editTextOrder;
        mButton = mBinding.button3;
        mSnapButton = mBinding.buttonSnap;

        backButton = mBinding.button10;

        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().StoreLogDAO();

        //admin functionality
        if(mUser.isAdmin()) {
            mSnapButton.setVisibility(View.VISIBLE);
        } else {
            mSnapButton.setVisibility(View.GONE);
        }

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.intentFactory(getApplicationContext(), mUserID);
                startActivity(intent);
            }
        });
    }

    private void getDatabase() {
        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().StoreLogDAO();
    }

    private void checkForUser() {
        //do we have a user in the intent?
        mUserID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserID != -1) {
            return;
        }

        //do we have a user in the preferences?
        if(mPreferences == null) {
            getPrefs();
        }
        mUserID = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserID != -1) {
            return;
        }

        //do we have a user at all?
        List<User> users = mStoreLogDAO.getAllUsers();
        if(users.size() <= 0) {
            User defaultUser = new User("daclink", "dac123", false);
            User altUser = new User("drew", "dac123", true);
            mStoreLogDAO.insert(defaultUser, altUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void addUserToPreference(int userID) {
        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userID);
        editor.apply();  //must call this for editors
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void loginUser(int userID) {
        //check if userID is valid
        mUser = mStoreLogDAO.getUserByUserID(userID);
        //check if mUser is not null
        addUserToPreference(userID);
        invalidateOptionsMenu();
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
        mStoreLogList = mStoreLogDAO.getStoreLogs();
        for(StoreLog log : mStoreLogList) {
            orderNum = log.getLogID();
            if(orderNum % 2 == 0) {
                mStoreLogDAO.delete(log);
            }
        }
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, CancelActivity.class);
        return intent;
    }
}