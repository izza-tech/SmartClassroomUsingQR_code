package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectAttendance extends AppCompatActivity {
    EditText editSubject;
    Button btn_Attendance;
    String Semester;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attendance);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
        editSubject = (EditText) findViewById(R.id.Subject);
        spinner=(Spinner) findViewById(R.id.dropdown);
        final String[] items=new String[]{"1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
        btn_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Subject = editSubject.getText().toString();
                Semester = spinner.getSelectedItem().toString();
                if (Subject.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter  Subject", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(SelectAttendance.this,Student_Attendance.class);
                    intent.putExtra("Subject",Subject);
                    intent.putExtra("Semester",Semester);
                    startActivity(intent);
                }
            }
        });

    }
}
