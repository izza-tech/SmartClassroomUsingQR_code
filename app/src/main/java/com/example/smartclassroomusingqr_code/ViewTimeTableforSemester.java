package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;

public class ViewTimeTableforSemester extends AppCompatActivity {
    Button btn_Attendance;
    String Semester;
    Spinner spinner;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> Timetable;

    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_tablefor_semester);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
        spinner=(Spinner) findViewById(R.id.dropdown);
        Timetable = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, Timetable );
        spinner.setAdapter(adapter );

        retrivedata();

        btn_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Semester = spinner.getSelectedItem().toString();

                Intent intent = new Intent(ViewTimeTableforSemester.this,ViewTimeTable.class);
                intent.putExtra("Semester",Semester);
                startActivity(intent);
            }
        });
    }


    public void retrivedata() {

        listener =   dref.child( "Time_Table" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot subject : dataSnapshot.getChildren()) {

                    Timetable.add(subject.getKey().toUpperCase());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
