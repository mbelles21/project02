package com.belles.project02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, CancelActivity.class);
        return intent;
    }
}