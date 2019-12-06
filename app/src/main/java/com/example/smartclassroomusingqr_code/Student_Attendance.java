package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;

public class Student_Attendance extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference ref;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase database;
    ArrayList<String> date=new ArrayList<String>();
    String userName,currentuser,Semester,Subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent i =getIntent();
        Semester= i.getStringExtra( "Semester" );
        Subject = i.getStringExtra( "Subject" );
        if(currentuser!=null){
            dref.child( "Student_Profiles" ).child( currentuser ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    profiledata data = dataSnapshot.getValue(profiledata.class);
                    userName = data.getNAME();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
        }
        else{
            Toast.makeText( getApplicationContext() ,"Please complete profile before performing Quiz!" ,Toast.LENGTH_LONG).show();

        }
       // userEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database= FirebaseDatabase.getInstance();
        database.getReference("Attendance").child(Semester).child(Subject.toUpperCase()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds_date : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds_name : dataSnapshot.child(ds_date.getKey()).getChildren()) {
                            if(dataSnapshot.exists()) {
                                if (ds_name.getKey().equals(userName)) {
                                    date.add(ds_date.getKey() + " present");
                                    // Toast.makeText(getApplicationContext(), "User Found", Toast.LENGTH_LONG).show();
                                    recyclerView.setAdapter(new AttendanceAdapter(date));
                                }

                            }


                        }
                        }
                        if(date.size()==0){
                            date.add("Sorry, no attendance found");
                            recyclerView.setAdapter(new AttendanceAdapter(date));
                        }


                        }
                else {
                    date.add("Sorry, no attendance found");
                    recyclerView.setAdapter(new AttendanceAdapter(date));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       // date.size();

        }
}
