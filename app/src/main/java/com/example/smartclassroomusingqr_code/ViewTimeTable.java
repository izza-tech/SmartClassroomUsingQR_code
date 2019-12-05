package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewTimeTable extends AppCompatActivity {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    String Semester;
    TextView b1 ,b2 ,b3, b4, b5, b6, b7, b8, b9, b10;
    TextView b11 ,b12 ,b13, b14, b15, b16, b17, b18, b19, b20;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);
        final Intent i =getIntent();
        Semester= i.getStringExtra( "Semester" );

        create = (Button) findViewById(R.id.btnCreateTime);
        b1 = (TextView) findViewById(R.id.edt1);
        b2 = (TextView) findViewById(R.id.edt2);
        b3 = (TextView) findViewById(R.id.edt3);
        b4 = (TextView) findViewById(R.id.edt4);
        b5 = (TextView) findViewById(R.id.edt5);
        b6 = (TextView) findViewById(R.id.edt6);
        b7 = (TextView) findViewById(R.id.edt7);
        b8 = (TextView) findViewById(R.id.edt8);
        b9 = (TextView) findViewById(R.id.edt9);
        b10 = (TextView) findViewById(R.id.edt10);
        b11 = (TextView) findViewById(R.id.edt11);
        b12 = (TextView) findViewById(R.id.edt12);
        b13 = (TextView) findViewById(R.id.edt13);
        b14 = (TextView) findViewById(R.id.edt14);
        b15 = (TextView) findViewById(R.id.edt15);
        b16 = (TextView) findViewById(R.id.edt16);
        b17 = (TextView) findViewById(R.id.edt17);
        b18 = (TextView) findViewById(R.id.edt18);
        b19 = (TextView) findViewById(R.id.edt19);
        b20 = (TextView) findViewById(R.id.edt20);

        dref.child("Time_Table").child(Semester).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    b1.setText(dataSnapshot.child("1").getValue().toString());
                    b2.setText(dataSnapshot.child("2").getValue().toString());
                    b3.setText(dataSnapshot.child("3").getValue().toString());
                    b4.setText(dataSnapshot.child("4").getValue().toString());
                    b5.setText(dataSnapshot.child("5").getValue().toString());
                    b6.setText(dataSnapshot.child("6").getValue().toString());
                    b7.setText(dataSnapshot.child("7").getValue().toString());
                    b8.setText(dataSnapshot.child("8").getValue().toString());
                    b9.setText(dataSnapshot.child("9").getValue().toString());
                    b10.setText(dataSnapshot.child("10").getValue().toString());
                    b11.setText(dataSnapshot.child("11").getValue().toString());
                    b12.setText(dataSnapshot.child("12").getValue().toString());
                    b13.setText(dataSnapshot.child("13").getValue().toString());
                    b14.setText(dataSnapshot.child("14").getValue().toString());
                    b15.setText(dataSnapshot.child("15").getValue().toString());
                    b16.setText(dataSnapshot.child("16").getValue().toString());
                    b17.setText(dataSnapshot.child("17").getValue().toString());
                    b18.setText(dataSnapshot.child("18").getValue().toString());
                    b19.setText(dataSnapshot.child("19").getValue().toString());
                    b20.setText(dataSnapshot.child("20").getValue().toString());


                }
                else{
                    Toast.makeText(getApplicationContext() , "Something went wrong.." , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ViewTimeTable.this , ViewTimeTableforSemester.class);
            startActivity(intent);
            finish();
            }
        });

    }
}
