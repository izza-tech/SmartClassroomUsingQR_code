package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenerateMcqs extends AppCompatActivity {

    EditText question,option1,option2,option3,option4;
    TextView totalquestions,sub, questionNo;
    Button btnSave;
    Spinner rightans;
    int i=1;
    String subject,totalQuestions;
    int totals=10;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_generate_mcqs );

        totalquestions=(TextView)findViewById( R.id.txttotalquestion );
        sub =(TextView)findViewById( R.id.txtsubject );
        questionNo = (TextView)findViewById( R.id.txtQuestionNo );
        question = (EditText)findViewById( R.id.txtquestions );
        option1 = (EditText)findViewById( R.id.opt1 );
        option2 = (EditText)findViewById( R.id.opt2 );
        option3 = (EditText)findViewById( R.id.opt3 );
        option4 = (EditText)findViewById( R.id.opt4 );
        btnSave = (Button)findViewById( R.id.btnsave );
        rightans = (Spinner) findViewById(R.id.rightanswer);
        //q.setText( i );
//        totals =totalquestions.getText().toString();
//create a list of items for the spinner.
        final String[] items = new String[]{"a", "b", "c", "d" };
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        rightans.setAdapter(adapter);



        questionNo.setText( String.valueOf( i ) );







        dref.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent in =getIntent();
//                totals = in.getStringExtra( "total" );totalmcq
                subject = in.getStringExtra( "subject" );
                totalQuestions = in.getStringExtra( "totalmcq" );
                 sub.setText( subject.toUpperCase() );
                totalquestions.setText( String.valueOf( totalQuestions ) );
//                totalquestions.setText( totals );
                dref.child("QuizInfo").child( subject.toUpperCase(  )).child("total").setValue( totalQuestions );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );




        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//
//                if(i>=Integer.valueOf( totalQuestions)) {
//
//
//                    AlertDialog.Builder dialog = new AlertDialog.Builder( GenerateMcqs.this );
//                    dialog.setCancelable( false );
//                    dialog.setTitle( "You Have Successfully Created Quiz" );
//                    dialog.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            Intent i = new Intent( GenerateMcqs.this, teachers.class );
//                            startActivity( i );
//                        }
//                    } );
//
//                    final AlertDialog alert = dialog.create();
//                    alert.show();
//                }
//
//                else {

                    setData();




            }

            private boolean setData() {
                String ques=question.getText().toString();
                String opt1=option1.getText().toString();
                String opt2=option2.getText().toString();
                String opt3=option3.getText().toString();
                String opt4=option4.getText().toString();
                if(ques.isEmpty())
                {
                    question.setError( "Please the question.?" );
                }
               else if(opt1.isEmpty())
                {
                    option1.setError( "Please enter option 1" );
                }
               else if(opt2.isEmpty())
                {
                    option2.setError( "Please enter option 2" );
                }
               else if(opt3.isEmpty())
                {
                    option3.setError( "Please enter option 3" );
                }
               else if(opt4.isEmpty())
                {
                    option4.setError( "Please enter option 4" );
                }
               else{
                    question.setText( "" );
                    option1.setText( "" );
                    option2.setText( "" );
                    option3.setText( "" );
                    option4.setText( "" );
                    int val = i+1;

                    questionNo.setText( String.valueOf( val ) );

                    String correct=rightans.getSelectedItem().toString();

                    //Save Data to Firebase
//                    dref.child("QuizQuestions").child( subject ).child("total").setValue( totalquestion );
//                    Toast.makeText( GenerateMcqs.this, "value"+i, Toast.LENGTH_SHORT ).show();
                    dref.child("QuizQuestions").child( subject.toUpperCase(  )).child( String.valueOf( i ) ).child("answer").setValue( correct );
                    dref.child("QuizQuestions").child( subject ).child( String.valueOf( i ) ).child("option1").setValue( opt1 );
                    dref.child("QuizQuestions").child( subject ).child( String.valueOf( i ) ).child("option2").setValue( opt2 );
                    dref.child("QuizQuestions").child( subject ).child( String.valueOf( i ) ).child("option3").setValue( opt3 );
                    dref.child("QuizQuestions").child( subject ).child( String.valueOf( i ) ).child("option4").setValue( opt4 );
                    dref.child("QuizQuestions").child( subject ).child( String.valueOf( i ) ).child("question").setValue( ques );
//                    Toast.makeText( GenerateMcqs.this, "value"+i, Toast.LENGTH_SHORT ).show();


                    if(i>=Integer.valueOf( totalQuestions)) {


                        AlertDialog.Builder dialog = new AlertDialog.Builder( GenerateMcqs.this );
                        dialog.setCancelable( false );
                        dialog.setTitle( "You Have Successfully Created Quiz" );
                        dialog.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent( GenerateMcqs.this, teachers.class );
                                startActivity( i );
                            }
                        } );

                        final AlertDialog alert = dialog.create();
                        alert.show();
                    }

                    i++;

                }


//                    finish();
//                    startActivity( getIntent() );



                return false;
            }

        } );


    }
}
