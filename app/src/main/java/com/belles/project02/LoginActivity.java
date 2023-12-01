package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.belles.project02.DB.AppDatabase;
import com.belles.project02.DB.StoreLogDAO;
import com.belles.project02.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText username;
    private EditText password;
    private TextView header;
    private Button button;

    private String usernameString;
    private String passwordString;

    private User user;
    private StoreLogDAO mStoreLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireUpDisplay();

        getDatabase();
    }

    private void wireUpDisplay() {
        username = findViewById(R.id.editTextTextUsername);
        password = findViewById(R.id.editTextTextPassword);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()) {
                    if(!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = LandingActivity.intentFactory(getApplicationContext(), user.getUserID());
                        startActivity(intent);
                    }
                }
                /*
                Intent intent = LandingActivity.intentFactory(getApplicationContext(), user.getUserID());
                startActivity(intent);

                 */
            }
        });
    }

    private void getValuesFromDisplay() {
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
    }

    private boolean checkForUserInDatabase() {
        user = mStoreLogDAO.getUserByUsername(usernameString);
        if(user == null) {
            Toast.makeText(this, "no user " + usernameString + " found", Toast.LENGTH_SHORT).show();
            List<User> users = mStoreLogDAO.getAllUsers();
            // declare predefined users here
            if(users.size() <= 0) {
                User defaultUser = new User("username", "password", false);
                User adminUser = new User("admin", "123", true);
                mStoreLogDAO.insert(defaultUser, adminUser);
            }
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        return user.getPassword().equals(passwordString);
    }

    private void getDatabase() {
        mStoreLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().StoreLogDAO();
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}