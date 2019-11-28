package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class ViewStudents extends AppCompatActivity {
FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
DatabaseReference databaseReference = firebaseDatabase.getReference();
RecyclerView recyclerView;
    ArrayList< profiledata> profiledata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        profiledata = new ArrayList<profiledata>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("Student_Profiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    profiledata p = dataSnapshot1.getValue(profiledata.class);
                    profiledata.add(p);
                }

                recyclerView.setAdapter(new ViewStudentAdapter(profiledata));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
