package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    FirebaseDatabase database;
    ArrayList<String> date=new ArrayList<String>();
    String userName,userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        userName= FirebaseAuth.getInstance().getCurrentUser().getUid();
       // userEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] text={"23-23-1993","32-32-3003","32-32-3088"};
        database= FirebaseDatabase.getInstance();
        ref = database.getReference("Attendance");
        database.getReference("Attendance").child("1").child("ENG").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds_date : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds_name : dataSnapshot.child(ds_date.getKey()).getChildren()) {
                    //        Toast.makeText(getApplicationContext(), ""+ds_date.getKey(), Toast.LENGTH_LONG).show();
                    //        Toast.makeText(getApplicationContext(), ""+ds_name.getKey(), Toast.LENGTH_LONG).show();
                            date.add(ds_date.getKey());
                            if(ds_name.getKey()==userName){
                                date.add(ds_date.getKey());
                            }

                        }
                        }



                        }
                else {
                    Toast.makeText(getApplicationContext(), "No Attendance Found", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        date.size();
       // recyclerView.setAdapter(new AttendanceAdapter(date));
        }
}
