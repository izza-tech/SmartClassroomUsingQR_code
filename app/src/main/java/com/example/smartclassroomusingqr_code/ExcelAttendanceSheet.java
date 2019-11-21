package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class ExcelAttendanceSheet extends AppCompatActivity {
    Button btn_Attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_attendance_sheet);
        btn_Attendance=(Button) findViewById(R.id.btn_Attendance);
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
