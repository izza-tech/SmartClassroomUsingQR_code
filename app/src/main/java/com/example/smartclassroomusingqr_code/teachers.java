package com.example.smartclassroomusingqr_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class teachers extends AppCompatActivity {
    private Button generateqrcode;
    private Button teacherslogout;
    private Button replyqueries;
    private Button createquiz, quizResult;
    private Button uploadlectures;
    private Button uploadassignments;
    private Button viewdetails , fattendence;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        generateqrcode=(Button) findViewById(R.id.btngenerateqrcode);
        generateqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opengenerateqrcode();
            }
        });

        replyqueries=(Button) findViewById(R.id.btnreplyqueries);
        replyqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openreplyqueries();
            }
        });

        createquiz=(Button) findViewById(R.id.btncreatequiz);
        createquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengeneratequiz();
            }
        });

        quizResult=(Button) findViewById(R.id.btnquizresult);
        quizResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizResult();
            }
        });

        uploadassignments=(Button) findViewById(R.id.btnUploadAssignments);
        uploadassignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opennotifications();
            }
        });

        uploadlectures=(Button) findViewById(R.id.btnuploadlectures);
        uploadlectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadlectures();
            }
        });

        fattendence=(Button) findViewById(R.id.btngenerateattendence);
        fattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,ExcelAttendanceSheet.class);
                startActivity(intent);
                    }
        });

        teacherslogout=(Button) findViewById(R.id.btnteacherslogout);
        teacherslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,teacherslogin.class);
                startActivity(intent);
                finish();
                Toast.makeText(teachers.this,"successfully logout",Toast.LENGTH_LONG).show();
            }
        });

        viewdetails=(Button) findViewById(R.id.btnviewdetails);
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,studet_details.class);
                startActivity(intent);

            }
        });
    }

    private void opengeneratequiz() {
        Intent intent=new Intent (this,GenerateQuiz.class);
        startActivity(intent);
    }
    private void quizResult(){
        Intent intent = new Intent( teachers.this, SelectResultSubject.class );
        startActivity( intent );
    }

    private void openreplyqueries() {
        Intent intent=new Intent (this,Issue_Disscusion.class);
        startActivity(intent);
    }

    private void Uploadlectures() {
        Intent intent=new Intent (this,uploadlectures.class);
        startActivity(intent);

    }

    private void opennotifications() {
        Intent intent=new Intent (this,notifications.class);
        startActivity(intent);

    }

    @SuppressWarnings("deprecation")
    public void opengenerateqrcode(){
        Intent intent=new Intent (this,generate_QRcode.class);
        startActivity(intent);
    }
}
