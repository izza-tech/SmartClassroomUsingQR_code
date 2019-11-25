package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizResult extends AppCompatActivity {

    TextView questions,correctAns,wrongAns;
    Button btnopen;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quiz_result );

        questions = (TextView)findViewById( R.id.totalQuestion );
        correctAns = (TextView)findViewById( R.id.correctAnswers );
        wrongAns = (TextView)findViewById( R.id.wrongAnswers );
        btnopen = (Button)findViewById( R.id.btnopen );

        Intent i =getIntent();

        final String totals = i.getStringExtra( "total" );
        final String correct = i.getStringExtra( "correct" );
        String wrong = i.getStringExtra( "wrong" );
        final String quizSubject = i.getStringExtra( "quizSubject" );
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(currentuser!=null){
            dref.child( "Student_Profiles" ).child( currentuser ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiledata data = dataSnapshot.getValue(profiledata.class);
                String name = data.getNAME();
                if(name !=null){
                    dref.child("QuizResult").child( quizSubject.toUpperCase( ) ).child( currentuser ).child( "Name" ).setValue( name );
                    dref.child("QuizResult").child( quizSubject.toUpperCase( ) ).child( currentuser ).child( "TotalQuestion" ).setValue( totals );
                    dref.child("QuizResult").child( quizSubject.toUpperCase( ) ).child( currentuser ).child( "Marks" ).setValue( correct );
                }
                else{
                    Toast.makeText( getApplicationContext(), "Please complete profile before performing Quiz!" ,Toast.LENGTH_LONG).show();

                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
        }
        else{
            Toast.makeText( getApplicationContext() ,"Please complete profile before performing Quiz!" ,Toast.LENGTH_LONG).show();

        }
        questions.setText( totals );
        correctAns.setText( correct );
        wrongAns.setText( wrong );
        btnopen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i = new Intent( QuizResult.this,students.class );
            startActivity( i );
            }
        } );
    }
}
