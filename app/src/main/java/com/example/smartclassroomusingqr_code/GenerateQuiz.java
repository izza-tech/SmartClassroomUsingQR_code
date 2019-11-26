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
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQuiz extends AppCompatActivity {
    private EditText etques1,etques2,etques3,etques4,etques5,etdatequiz;
    EditText etmarks1,etmarks2,etmarks3,etmarks4,etmarks5;
    EditText et2;
    DatePickerDialog datePickerDialogquiz;
    String TAG ="generate QRcode for quiz";
    Bitmap bitmapquiz;
    ImageView QRimgquiz;
    String inputvaluequiz;
    Button startquiz,savequiz , generatemcq;
    QRGEncoder qrgEncoderquiz;
    BitmapDrawable drawablequiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_quiz);
        getSupportActionBar().setTitle("Generate Quiz");



        QRimgquiz=(ImageView)findViewById(R.id.qrcodequiz);
        etques1=(EditText)findViewById(R.id.edittextques1);
        etques2=(EditText)findViewById(R.id.edittextques2);
        etques3=(EditText)findViewById(R.id.edittextques3);
        etques4=(EditText)findViewById(R.id.edittextques4);
        etques5=(EditText)findViewById(R.id.edittextques5);

        etmarks1=(EditText)findViewById(R.id.edittextmarks1);
        etmarks2=(EditText)findViewById(R.id.edittextmarks2);
        etmarks3=(EditText)findViewById(R.id.edittextmarks3);
        etmarks4=(EditText)findViewById(R.id.edittextmarks4);
        etmarks5=(EditText)findViewById(R.id.edittextmarks5);



        savequiz=(Button)findViewById(R.id.btnquizsavetogallery);
        savequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawablequiz=(BitmapDrawable)QRimgquiz.getDrawable();
                bitmapquiz=drawablequiz.getBitmap();
                FileOutputStream outputStream=null;
                File sdcard= Environment.getExternalStorageDirectory();
                File directory = new File(sdcard.getAbsolutePath() +"/Quiz_Codes");
                directory.mkdir();
                String flename=String.format("%d.jpg",System.currentTimeMillis());
                File outfile=new File(directory,flename);

                Toast.makeText(GenerateQuiz.this,"image saved successfully",Toast.LENGTH_LONG).show();
                try {
                    outputStream=new FileOutputStream(outfile);
                    bitmapquiz.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
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
        startquiz=(Button)findViewById(R.id.createquizbutton);
        startquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputvaluequiz="Date : "+etdatequiz.getText().toString().trim()+
                        "\n\n Question 1 : "+etques1.getText().toString().trim()+"    (Marks:"+etmarks1.getText().toString().trim()+")"+
                        "\n\n Question 2 : "+etques2.getText().toString().trim()+"    (Marks:"+etmarks2.getText().toString().trim()+")"+
                        "\n\n Question 3 : "+etques3.getText().toString().trim()+"    (Marks:"+etmarks3.getText().toString().trim()+")"+
                        "\n\n Question 4 : "+etques4.getText().toString().trim()+"    (Marks:"+etmarks4.getText().toString().trim()+")"+
                        "\n\n Question 5 : "+etques5.getText().toString().trim()+"    (Marks:"+etmarks5.getText().toString().trim()+")";

                if (inputvaluequiz.length()>0)

                {
                    WindowManager manager=(WindowManager)getSystemService(WINDOW_SERVICE);
                    Display display= manager.getDefaultDisplay();
                    Point point=new Point();
                    display.getSize(point);
                    int width=point.x;
                    int height=point.y;
                    int smallerdimension=width>height ? width:height;
                    smallerdimension=smallerdimension*3/4;
                    qrgEncoderquiz =new QRGEncoder(inputvaluequiz,null, QRGContents.Type.TEXT,smallerdimension);
                    try {
                        bitmapquiz=qrgEncoderquiz.encodeAsBitmap();
                        QRimgquiz.setImageBitmap(bitmapquiz);
                    }
                    catch (WriterException e)
                    {
                        Log.v(TAG,e.toString());
                    }
                }
                else
                {
                    et2.setError("required");
                }
            }
        });

        generatemcq=(Button)findViewById(R.id.btngeneratemcq);
        generatemcq.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (GenerateQuiz.this,CreateQuiz.class);
                startActivity(intent);

            }
        } );
        etdatequiz=findViewById(R.id.et_dateQuiz);



        etdatequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialogquiz=new DatePickerDialog(GenerateQuiz.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        etdatequiz.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                datePickerDialogquiz.show();
            }
        });
    }
}


