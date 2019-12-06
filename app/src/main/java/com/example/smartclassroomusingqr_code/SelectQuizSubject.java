package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.L;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectQuizSubject extends AppCompatActivity {
    Spinner txtSub;
    Button btnTakeQuiz;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> QuizSubject;

    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    //asd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_quiz_subject );


        btnTakeQuiz=(Button)findViewById( R.id.btntakequiz );
        txtSub=(Spinner) findViewById(R.id.txtSubject);

        QuizSubject = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,QuizSubject);
        txtSub.setAdapter(adapter );

        retrivedata();



        btnTakeQuiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final String subject = txtSub.getSelectedItem().toString();
                dref.child("QuizResult").child( subject ).child(currentuser).addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Toast.makeText( getApplicationContext() , "You have already solve this quiz" , Toast.LENGTH_LONG ).show();
                    }
                    else{
                        savedata();
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
                //savedata();
            }

            private void savedata() {

              final String subject = txtSub.getSelectedItem().toString();

                    dref.child( "QuizQuestions" ).child( subject.toUpperCase() ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()) {

                                Intent in = new Intent( SelectQuizSubject.this, PerformQuiz.class );
                                in.putExtra( "subject", String.valueOf( subject ) );
                                startActivity( in );
                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                }


//

        } );
    }

    public void retrivedata() {
        listener =   dref.child( "QuizInfo" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot subject : dataSnapshot.getChildren()) {

                    QuizSubject.add(subject.getKey().toUpperCase());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
