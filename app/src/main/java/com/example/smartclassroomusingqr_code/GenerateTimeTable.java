package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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

public class GenerateTimeTable extends AppCompatActivity {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    String Semester;
    EditText b1 ,b2 ,b3, b4, b5, b6, b7, b8, b9, b10;
    EditText b11 ,b12 ,b13, b14, b15, b16, b17, b18, b19, b20;
    Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_time_table);
        final Intent i =getIntent();
        Semester= i.getStringExtra( "Semester" );
        create = (Button) findViewById(R.id.btnCreateTime);
        b1 = (EditText) findViewById(R.id.edt1);
        b2 = (EditText) findViewById(R.id.edt2);
        b3 = (EditText) findViewById(R.id.edt3);
        b4 = (EditText) findViewById(R.id.edt4);
        b5 = (EditText) findViewById(R.id.edt5);
        b6 = (EditText) findViewById(R.id.edt6);
        b7 = (EditText) findViewById(R.id.edt7);
        b8 = (EditText) findViewById(R.id.edt8);
        b9 = (EditText) findViewById(R.id.edt9);
        b10 = (EditText) findViewById(R.id.edt10);
        b11 = (EditText) findViewById(R.id.edt11);
        b12 = (EditText) findViewById(R.id.edt12);
        b13 = (EditText) findViewById(R.id.edt13);
        b14 = (EditText) findViewById(R.id.edt14);
        b15 = (EditText) findViewById(R.id.edt15);
        b16 = (EditText) findViewById(R.id.edt16);
        b17 = (EditText) findViewById(R.id.edt17);
        b18 = (EditText) findViewById(R.id.edt18);
        b19 = (EditText) findViewById(R.id.edt19);
        b20 = (EditText) findViewById(R.id.edt20);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref.child("Time_Table").child(Semester ).child( "1" ).setValue( b1.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "2" ).setValue( b2.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "3" ).setValue( b3.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "4" ).setValue( b4.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "5" ).setValue( b5.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "6" ).setValue( b6.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "7" ).setValue( b7.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "8" ).setValue( b8.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "9" ).setValue( b9.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "10" ).setValue( b10.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "11" ).setValue( b11.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "12" ).setValue( b12.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "13" ).setValue( b13.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "14" ).setValue( b14.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "15" ).setValue( b15.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "16" ).setValue( b16.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "17" ).setValue( b17.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "18" ).setValue( b18.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "19" ).setValue( b19.getText().toString());
                dref.child("Time_Table").child(Semester ).child( "20" ).setValue( b20.getText().toString());

                Toast.makeText(getApplicationContext() , "Timetable created" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GenerateTimeTable.this , SelectSemesterForTimeTable.class);
                startActivity(intent);
                finish();
            }
        });



    }



}
