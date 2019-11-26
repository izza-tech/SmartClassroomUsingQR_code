package com.example.smartclassroomusingqr_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class students extends AppCompatActivity {
    private Button scanQRcode,stuentslogout,accesslectures,uploadissues,viewprofile;
    private Button attendencehistory , mcqquiz;
    private Button attemptquiz;

    ArrayList<String> urls=new ArrayList<>();
    FirebaseStorage storage;//used for uploading files
    FirebaseDatabase database;//used to store the url of uploaded files.



    @SuppressWarnings("deprecation")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        storage=FirebaseStorage.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE STORAGE

        database=FirebaseDatabase.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE database

        attemptquiz=(Button) findViewById(R.id.btnscanforquiz);
        attemptquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openattemptquiz();


            }
        });
        mcqquiz = (Button) findViewById( R.id.btnformmcq);
        mcqquiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (students.this,SelectQuizSubject.class);
                startActivity(intent);
            }
        } );

        scanQRcode=(Button) findViewById(R.id.btnscanqrcode);
        scanQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openscanqrcode();
            }
        });

        uploadissues=(Button) findViewById(R.id.btnuploadissuesstudents);
        uploadissues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengroupchatactivity();
            }
        });


        viewprofile=(Button) findViewById(R.id.btnviewprofiles);
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openprofile();
            }
        });



        attendencehistory=(Button) findViewById(R.id.btnviewattendencehistory);
        attendencehistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openattendencehistory();
            }
        });

        accesslectures=findViewById(R.id.btnaccesslectures);
        accesslectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(students.this,myrecyclerviewActivity .class));
            }
        });

        stuentslogout=(Button) findViewById(R.id.btnstudentlogout);
        stuentslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (students.this,studentlogin_validation.class);
                startActivity(intent);
                finish();
                Toast.makeText(students.this,"successfully logut",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openattemptquiz() {
        Intent intent=new Intent (students.this, ScanQuiz.class);
        startActivity(intent);
    }

    private void openprofile() {
        Intent intent=new Intent (students.this, studentProfileActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    public void openscanqrcode(){
        Intent intent=new Intent (this,scanqrcode.class);
        startActivity(intent);
    }
    @SuppressWarnings("deprecation")
    public void openattendencehistory(){
        Intent intent=new Intent (this,ExcelAttendanceSheet.class);
        startActivity(intent);
    }

    public void opengroupchatactivity(){
        Intent intent=new Intent (this,Issue_Disscusion.class);
        startActivity(intent);
    }
}

