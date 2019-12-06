package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;

public class ShowQuizResult extends AppCompatActivity {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    String subjectResult, stName,toatalMarks,obtaninedMarks;
    TextView Subject;
    ArrayList<String> stuName= new ArrayList<String>();
    ArrayList<String> totalMarks= new ArrayList<String>();
    ArrayList<String> obtainMarks= new ArrayList<String>();
    RecyclerView recyclerView;
    ArrayList< ResultModel> resultModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show_quiz_result );
        recyclerView = (RecyclerView)findViewById( R.id.showResult );
        Subject = (TextView)findViewById( R.id.txtSubject );

//        recyclerView.setLayoutManager(new LinearLayoutManager( this ) );


//        Intent in =getIntent();
//        final String quizSubject = in.getStringExtra( "subject" );
        Intent in =getIntent();
        subjectResult = in.getStringExtra( "subject" );
        Subject.setText( subjectResult );
        resultModels = new ArrayList<ResultModel>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dref.child( "QuizResult" ).child( subjectResult ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ResultModel p = dataSnapshot1.getValue(ResultModel.class);
                    resultModels.add(p);
                }

                recyclerView.setAdapter(new ResultAdapter(resultModels));


            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        
    }
}
