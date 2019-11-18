package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class studet_details extends AppCompatActivity {
Button studentissues;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studet_details);


        studentissues=(Button)findViewById(R.id.btnstudentissues);
        studentissues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(studet_details.this,Issue_Disscusion.class));
            }
        });



    }
}
