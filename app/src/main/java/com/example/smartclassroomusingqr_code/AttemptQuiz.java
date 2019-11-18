package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AttemptQuiz extends AppCompatActivity {
    public static TextView textViewquiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_quiz);

        getSupportActionBar().setTitle("Quiz");

        textViewquiz=(TextView)findViewById(R.id.textviewquiz);
        String barcode = getIntent().getStringExtra("code");
        textViewquiz.setText(barcode);
    }
}
