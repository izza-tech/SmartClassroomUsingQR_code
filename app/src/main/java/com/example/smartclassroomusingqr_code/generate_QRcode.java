package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class generate_QRcode extends AppCompatActivity {
  private EditText etdate,etname;
    private Spinner spinnersemester,spinnershift;
    DatePickerDialog datePickerDialog;
    String TAG ="generateQRcode";
    EditText et1;
    ImageView QRimg;
    Bitmap bitmap;
    String inputvalue;
    Button start,save;
    QRGEncoder qrgEncoder;
    BitmapDrawable drawable;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__qrcode);

        QRimg=(ImageView)findViewById(R.id.qrcode);
        et1=(EditText)findViewById(R.id.edittext);
        etname=(EditText)findViewById(R.id.edittextteachername);

        spinnersemester = (Spinner) findViewById(R.id.spinnersemester);
        spinnershift = (Spinner) findViewById(R.id.spinnershift);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        final String currentTime = sdf.format(new Date());
        //textView.setText(currentDateandTime);

        save=(Button)findViewById(R.id.btnsavetogallery);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawable=(BitmapDrawable)QRimg.getDrawable();
                bitmap=drawable.getBitmap();
                FileOutputStream outputStream=null;
                File sdcard= Environment.getExternalStorageDirectory();
                File directory = new File(sdcard.getAbsolutePath() +"/QR_Codes");
                directory.mkdir();
                String flename=String.format("%d.jpg",System.currentTimeMillis());
                File outfile=new File(directory,flename);

                Toast.makeText(generate_QRcode.this,"image saved successfully",Toast.LENGTH_LONG).show();
                try {
                    outputStream=new FileOutputStream(outfile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();

                    Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outfile));
                    sendBroadcast(intent);





                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        start=(Button)findViewById(R.id.createbutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputvalue="Teacher Name : "+etname.getText().toString().trim()+"\n Subject : "+et1.getText().toString().trim()+"\n Date : "+etdate.getText().toString().trim()+"\n Semester : "+spinnersemester.getSelectedItem().toString().trim()+"\n Shift : "+spinnershift.getSelectedItem().toString().trim()+"\n Time"+ currentTime +"\n";

                if (inputvalue.length()>0)

                {
                    WindowManager manager=(WindowManager)getSystemService(WINDOW_SERVICE);
                    Display display= manager.getDefaultDisplay();
                    Point point=new Point();
                    display.getSize(point);
                    int width=point.x;
                    int height=point.y;
                    int smallerdimension=width>height ? width:height;
                    smallerdimension=smallerdimension*3/4;
                    qrgEncoder =new QRGEncoder(inputvalue,null, QRGContents.Type.TEXT,smallerdimension);
                    try {
                        bitmap=qrgEncoder.encodeAsBitmap();
                        QRimg.setImageBitmap(bitmap);
                    }
                    catch (WriterException e)
                    {
                        Log.v(TAG,e.toString());
                    }
                }
                else
                {
                    et1.setError("required");
                }
            }
        });


        etdate=findViewById(R.id.et_date);



        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog=new DatePickerDialog(generate_QRcode.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        etdate.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
}
