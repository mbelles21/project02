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
    private Button buttonButton;
    private Button wireButton;
    private Button buttonButton2;
    private Button threadButton;
    private Button pizzaButton;
    private Button materiaButton;
    private Button bottleButton;
    private Button mysteryButton;
    private Button backButton;

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

        buttonButton = binding.button1;
        wireButton = binding.button2;
        buttonButton2 = binding.button3;
        threadButton = binding.button4;
        pizzaButton = binding.button6;
        materiaButton = binding.button7;
        bottleButton = binding.button8;
        mysteryButton = binding.button9;

        backButton = binding.button5;

        buttonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Button", "a button", 2.00, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        wireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Wire", "for the buttons", 1.0, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        buttonButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("The other button", "for clothes", 0.5, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        threadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Thread", "for the clothes buttons", 1.25, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        pizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Extra large 3 topping pizza", "with cheese", 9.99, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Pizza time!", Toast.LENGTH_SHORT).show();
            }
        });

        materiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Enemy Skill Materia", "lets you learn cool spells", 99.99, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        bottleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Water Bottle", "water not included", 14.75, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        mysteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreLog log = new StoreLog("Mystery Item", "not even we know what it is", 3.49, userID);
                mStoreLogDAO.insert(log);
                Toast.makeText(BuyingActivity.this, "Purchased", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.intentFactory(getApplicationContext(), userID);
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, BuyingActivity.class);
        return intent;
    }
}