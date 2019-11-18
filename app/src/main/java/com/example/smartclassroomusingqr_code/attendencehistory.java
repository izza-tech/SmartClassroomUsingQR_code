package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class attendencehistory extends AppCompatActivity {
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendencehistory);
        getSupportActionBar().setTitle("Attendence History");
    }
}
