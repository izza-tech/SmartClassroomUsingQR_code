package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.security.auth.Subject;

public class ExcelAttendanceSheet extends AppCompatActivity {
    Button btn_Attendance;
    FirebaseDatabase database;
    ArrayList<String> list = new ArrayList<String>();
    String Semester;
    TextView txtView;
    EditText editSubject;
    Spinner spinner;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_attendance_sheet);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
        editSubject = (EditText) findViewById(R.id.Subject);
        spinner=(Spinner) findViewById(R.id.dropdown);
        final String[] items=new String[]{"1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
        database= FirebaseDatabase.getInstance();
        ref = database.getReference("Attendance");
        btn_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Subject = editSubject.getText().toString();
                Semester = spinner.getSelectedItem().toString();
                if (Subject.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter  Subject", Toast.LENGTH_LONG).show();
                }
                else {
                //database fetch
                database.getReference("Attendance").child(Semester).child(Subject.toUpperCase()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            /////////////////////////excel sheet code/////////////////////////////////////
                            Workbook wb = new HSSFWorkbook();
                            Cell cell = null;
                            CellStyle cellStyle = wb.createCellStyle();
                            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                            //Now we are creating sheet
                            Sheet sheet = null;
                            sheet = wb.createSheet("Attendance sheet of " + Subject);

                            ///////////////////////////////////excel sheet code//////////////////////////
                            int rowNO = 0;
                            for (DataSnapshot ds_date : dataSnapshot.getChildren()) {
                                Row row = sheet.createRow(rowNO);
                                int columnNo = 0;
                                for (DataSnapshot ds_name : dataSnapshot.child(ds_date.getKey()).getChildren()) {

                                    ////////////////////////////excel sheet code ///////////////////////////////////
                                    cell = row.createCell(columnNo);
                                    if (columnNo == 0) {
                                        cell.setCellValue(ds_date.getKey());
                                        columnNo++;
                                        cell = row.createCell(columnNo);
                                        cell.setCellValue(ds_name.getKey());
                                    } else {
                                        cell.setCellValue(ds_name.getKey());
                                    }
                                    columnNo++;
                                }
                                rowNO++;
                            }
                            ////////////////////////////excel sheet code ///////////////////////////////////


                            File file = new File(getExternalFilesDir(null), Subject.toUpperCase() + " Attendance.xls");
                            FileOutputStream outputStream = null;

                            try {
                                outputStream = new FileOutputStream(file);
                                wb.write(outputStream);
                                Toast.makeText(getApplicationContext(), "Attendance Downloaded, Check App folder", Toast.LENGTH_LONG).show();
                            } catch (java.io.IOException e) {
                                e.printStackTrace();

                                Toast.makeText(getApplicationContext(), "Sorry, Try Again", Toast.LENGTH_LONG).show();
                                try {
                                    outputStream.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }


                            ////////////////////////////excel sheet code ///////////////////////////////////

                        } else {
                            Toast.makeText(getApplicationContext(), "No Attendance Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }



            }
        });
    }
}
