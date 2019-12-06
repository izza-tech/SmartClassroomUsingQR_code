package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class admin extends AppCompatActivity {
    private Button managestudents , manageClasses;
    private Button managefaculty,adminlogout , viewStudents;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Welcome Admin");

        managestudents=(Button) findViewById(R.id.btnmanagestudents);
        managestudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfragmentacivity();
            }
        });
        managefaculty=(Button) findViewById(R.id.btnmanagefaculty);
        managefaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmanagefaculty();
            }
        });

        manageClasses = (Button) findViewById( R.id.btntimetable );
        manageClasses.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent( admin.this , SelectSemesterForTimeTable.class );
                startActivity( i1 );
            }
        } );

        adminlogout=(Button) findViewById(R.id.btnadminlogout);
        adminlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (admin.this,ViewStudents.class);
                startActivity(intent);
                finish();
                Toast.makeText(admin.this,"successfully logout",Toast.LENGTH_LONG).show();
            }
        });
        viewStudents = (Button) findViewById(R.id.btnregisteredrstudents);
        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (admin.this,ViewStudents.class);
                startActivity(intent);
            }
        });
    }

    private void openfragmentacivity() {
        Intent intent=new Intent (this,fragmentActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    public void openmanagefaculty(){
        Intent intent=new Intent (this,teacherActivity.class);
        startActivity(intent);
    }
}

