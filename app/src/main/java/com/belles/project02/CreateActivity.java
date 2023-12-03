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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();

                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // long click to create admin account
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createNewAccount();

                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);

                return false;
            }
        });
    }

    private void createNewAccount() {
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();

        User newAdmin = new User(usernameString, passwordString, false);
        mStoreLogDAO.insert(newAdmin);
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, CreateActivity.class);
        return intent;
    }
}