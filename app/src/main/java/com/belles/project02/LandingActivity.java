package com.belles.project02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityLandingBinding;

import java.util.List;
import java.util.Random;

public class LandingActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.belles.project02.userIDKey";
    private static final String PREFERENCES_KEY = "com.belles.project02.preferencesKey";

    private ActivityLandingBinding binding;

    private TextView display;
    private Button buyButton;
    private Button ordersButton;
    private Button cancelButton;
    private Button logoutButton;
    private Button walletButton;

    private StoreLogDAO mStoreLogDAO;

    private List<StoreLog> mStoreLogList;

    private int mUserID = -1;

    private User mUser;
    private TextView mAdmin;
    private SharedPreferences mPreferences = null;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();
        addUserToPreference(mUserID);
        loginUser(mUserID);

        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        display = binding.mainDisplay;
        buyButton = binding.buyButton;
        ordersButton = binding.ordersButton;
        cancelButton = binding.cancelOrderButton;
        mAdmin = binding.textViewAdmin;
        logoutButton = binding.button;
        walletButton = binding.buttonWallet;

        display.setMovementMethod(new ScrollingMovementMethod());

        //put button click listeners here

        //admin functionality
        if(mUser != null && mUser.isAdmin()) {
            mAdmin.setVisibility(View.VISIBLE);
        } else {
            mAdmin.setVisibility(View.GONE);
        }

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BuyingActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewingActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CancelActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFunds();
            }
        });

    } //end of onCreate

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

    private void loginUser(int userID) {
        //check if userID is valid
        mUser = mStoreLogDAO.getUserByUserID(userID);
        //check if mUser is not null
        addUserToPreference(userID);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null) {
            MenuItem item = menu.findItem(R.id.userMenuOptions);
            item.setTitle(mUser.getUsername());
            mOptionsMenu = menu;
        }
        return super.onPrepareOptionsMenu(menu);
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

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserID = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //don't need to put anything here
                    }
                });
        alertBuilder.create().show();
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("Home");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void viewFunds() {
        Random random = new Random();
        double min = 0.0;
        double max = 9999999.9;
        double funds = min + (max - min) * random.nextDouble();
        funds = Math.round(funds * 100.0) / 100.0;

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("You have $" + funds);

        alertBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //don't need to put anything here
                    }
                });
        alertBuilder.create().show();
    }

    public static Intent intentFactory(Context context, int userID) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(USER_ID_KEY, userID);
        return intent;
    }
}