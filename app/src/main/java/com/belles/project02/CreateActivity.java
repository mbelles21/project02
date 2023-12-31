package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityCreateBinding;

import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private ActivityCreateBinding binding;
    private EditText username;
    private EditText password;
    private Button button;
    private Button cancelButton;

    private String usernameString;
    private String passwordString;
    private User user;
    private StoreLogDAO mStoreLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = findViewById(R.id.editTextTextUsername);
        password = findViewById(R.id.editTextTextPassword);
        button = binding.button;
        cancelButton = binding.buttonCancel;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(!checkForUserInDatabase()) {
                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateActivity.this, "Invalid account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // long click to create admin account
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getValuesFromDisplay();
                if(!checkForAdminInDatabase()) {
                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
                /*
                User newAdmin = new User(usernameString, passwordString, true);
                mStoreLogDAO.insert(newAdmin);

                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);

                 */

                return false;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public void getValuesFromDisplay() {
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
    }

    private boolean checkForUserInDatabase() {
        user = mStoreLogDAO.getUserByUsername(usernameString);
        if(user != null) {
            Toast.makeText(this, "user " + usernameString + " already exists", Toast.LENGTH_SHORT).show();
            List<User> users = mStoreLogDAO.getAllUsers();
            return true;
        } else {
            User newUser = new User(usernameString, passwordString, false);
            mStoreLogDAO.insert(newUser);
            return false;
        }
    }

    private boolean checkForAdminInDatabase() {
        user = mStoreLogDAO.getUserByUsername(usernameString);
        if(user != null) {
            Toast.makeText(this, "user " + usernameString + " already exists", Toast.LENGTH_SHORT).show();
            List<User> users = mStoreLogDAO.getAllUsers();
            return true;
        } else {
            User newUser = new User(usernameString, passwordString, true);
            mStoreLogDAO.insert(newUser);
            return false;
        }
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, CreateActivity.class);
        return intent;
    }
}