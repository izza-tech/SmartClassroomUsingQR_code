package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectSubjectForResult extends AppCompatActivity {
    EditText txtSub;
    Button btnTakeQuiz;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_subject_for_result );
        txtSub = (EditText)findViewById( R.id.txtSubject );
        btnTakeQuiz=(Button)findViewById( R.id.btntakequiz );



        btnTakeQuiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savedata();
            }

            private boolean savedata() {

                final String subject = txtSub.getText().toString();

                if(subject.isEmpty()){

                    txtSub.setError( "Please Enter The Subject Name" );

                }
                else {
                    dref.child( "QuizResult" ).child( subject.toUpperCase() ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()) {

                                Intent in = new Intent( SelectSubjectForResult.this,ShowQuizResult.class );
                                in.putExtra( "subject", String.valueOf( subject ) );
                                startActivity( in );
                            }

                            else {
                                Toast.makeText( getApplicationContext() , "Sorry you dont have quiz" , Toast.LENGTH_LONG ).show();
                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                }

                return false;
//
            }
        } );
    }
}
