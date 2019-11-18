package com.example.smartclassroomusingqr_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class scanqrcode extends AppCompatActivity {
    Button markattendence,SUMBIT;
    public static TextView textView;
    private StorageReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqrcode);
        textView=(TextView)findViewById(R.id.textview);
        String barcode = getIntent().getStringExtra("code");
        textView.setText(barcode);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        final String currentTime = sdf.format(new Date());
        if(barcode != null) {
            String time = barcode.substring(Integer.valueOf(barcode.length()) - 9);

//            Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();

            String format = "hh:mm:ss";
            SimpleDateFormat df = new SimpleDateFormat(format);

            try {
                Date dateObj1 = df.parse(time);
                Date dateObj2 = df.parse(currentTime);
                DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
                long diff = dateObj2.getTime() - dateObj1.getTime();
                int diffsec = (int) (diff / (1000));
                final String t = crunchifyFormatter.format(diffsec);

                Toast.makeText(getApplicationContext() , t , Toast.LENGTH_LONG).show();
                mref = FirebaseStorage.getInstance().getReference("Attendance").child("students_attendence/");

                SUMBIT=findViewById(R.id.btnsubmitattendence);
                SUMBIT.setOnClickListener(new View.OnClickListener() {
                    @Override


                    public void onClick(View v) {
                        if(Integer.valueOf(t)<30) {
                            // StorageReference childs=mref.child("attend/");
                            String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                            UploadTask uploadTask = mref.putBytes(uemail.getBytes());
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(scanqrcode.this, "attendence marked ", Toast.LENGTH_SHORT).show();
                                }
                            });

                            
                            startActivity(new Intent(scanqrcode.this, attendence_marked.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext() , "Sorry please scan again" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }






        markattendence=(Button)findViewById(R.id.btnmarkattendence);
        markattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(scanqrcode.this,attendence.class));
            }
        });


    }
}
