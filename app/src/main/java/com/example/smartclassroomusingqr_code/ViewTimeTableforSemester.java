package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ViewTimeTableforSemester extends AppCompatActivity {
    Button btn_Attendance;
    String Semester;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_tablefor_semester);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
        spinner=(Spinner) findViewById(R.id.dropdown);
        final String[] items=new String[]{"1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
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
}
