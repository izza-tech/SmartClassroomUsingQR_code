package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.security.auth.Subject;

public class ExcelAttendanceSheet extends AppCompatActivity {
    Button btn_Attendance;
    FirebaseDatabase database;
    String Semester;
    TextView txtView;
    EditText editText_Subject,editText_Semester;
    Spinner spinner;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_attendance_sheet);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
        editText_Subject = (EditText) findViewById(R.id.subject);
        //editText_Semester = () findViewById(R.id.semester);
        spinner=(Spinner) findViewById(R.id.dropdown);
        String[] items=new String[]{"1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
        Semester=spinner.getSelectedItem().toString();
        database= FirebaseDatabase.getInstance();
        ref = database.getReference("Attendance");
        btn_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workbook wb=   new HSSFWorkbook();
                Cell cell=null;
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                //Now we are creating sheet
                Sheet sheet=null;
                sheet = wb.createSheet("Attendance sheet");
                //String date = new SimpleDateFormat("dd-MM-yyyy" , Locale.getDefault()).format(new Date());
                //database fetch
                database.getReference("Attendance").child("6").child("ENG").addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            String value= (String) dataSnapshot.getValue();
                            Toast.makeText(getApplicationContext() ,value  , Toast.LENGTH_LONG).show();
//                            dataSnapshot.getValue();
                            }

//
//                        String StudentName = data.getNAME();
//                        String Semester = data.getSEMESTER();
//                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//                        database.getReference("Attendance").child(Semester).child(subject).child(date).child(currentuser).child("Name").setValue(StudentName);
//                        database.getReference("Attendance").child(Semester).child(subject).child(date).child(currentuser).child("Id").setValue(currentuser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Now column and row
                for (int i=0;i<4;i++){
                    Row row =sheet.createRow(i);
                    cell=row.createCell(i);
                    cell.setCellValue(i);
//                    cell.setCellStyle(cellStyle);

//                    cell=row.createCell(i);
//                    cell.setCellValue(i);
//                    cell.setCellStyle(cellStyle);

                }
//                Row row =sheet.createRow(8);
//

//                cell=row.createCell(0,0);
//                cell.setCellValue("Name");
//                cell.setCellStyle(cellStyle);
//
//                cell=row.createCell(1,4);
//                cell.setCellValue("Number");
//                cell.setCellStyle(cellStyle);
//
//                sheet.setColumnWidth(0,(10*200));
//                sheet.setColumnWidth(1,(10*200));

                File file = new File(getExternalFilesDir(null),"plik.xls");
                FileOutputStream outputStream =null;
                File dir = Environment.getExternalStorageDirectory();
                String path = dir.getAbsolutePath();


                try {
                    outputStream=new FileOutputStream(file);
                    wb.write(outputStream);
                    Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
