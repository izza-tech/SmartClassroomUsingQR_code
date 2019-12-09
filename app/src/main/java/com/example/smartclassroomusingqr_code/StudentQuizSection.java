package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentQuizSection extends AppCompatActivity {
    private Button attemptquiz , mcqquiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_quiz_section );



        mcqquiz = (Button) findViewById( R.id.btnformmcq);
        mcqquiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentQuizSection.this,SelectQuizSubject.class);
                startActivity(intent);
            }
        } );

        attemptquiz = (Button)findViewById( R.id.btnscanforQuiz );
        attemptquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (StudentQuizSection.this, ScanQuiz.class);
                startActivity(intent);
            }
        });
    }
}
