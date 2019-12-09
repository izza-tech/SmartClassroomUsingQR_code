package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherGeneratQuiz extends AppCompatActivity {
    Button createquiz,quizResult , generatemcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_teacher_generat_quiz );


        createquiz=(Button) findViewById(R.id.btncreatequiz);
        createquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (TeacherGeneratQuiz.this,GenerateQuiz.class);
                startActivity(intent);            }
        });


        generatemcq=(Button)findViewById(R.id.btngeneratemcq);
        generatemcq.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (TeacherGeneratQuiz.this,CreateQuiz.class);
                startActivity(intent);

            }
        } );

        quizResult=(Button) findViewById(R.id.btnquizresult);
        quizResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TeacherGeneratQuiz.this, SelectResultSubject.class );
                startActivity( intent );            }
        });
    }
}
