package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectResultSubject extends AppCompatActivity {
    Spinner txtSub;
    Button btnResult;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> ResultSubject;

    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_result_subject );

        btnResult=(Button)findViewById( R.id.btnShowResult);
        txtSub=(Spinner) findViewById(R.id.txtSubject);

        ResultSubject = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,ResultSubject);
        txtSub.setAdapter(adapter );

        retrivedata();



        btnResult.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String subject = txtSub.getSelectedItem().toString();

                dref.child( "QuizResult" ).child( subject.toUpperCase() ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {

                            Intent in = new Intent( SelectResultSubject.this, ShowQuizResult.class );
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

        listener =   dref.child( "QuizResult" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot subject : dataSnapshot.getChildren()) {

                    ResultSubject.add(subject.getKey().toUpperCase());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
