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
        Toast.makeText(getApplicationContext() ,barcode , Toast.LENGTH_LONG ).show();

        mref = FirebaseStorage.getInstance().getReference("Attendance").child("students_attendence/");

        SUMBIT=findViewById(R.id.btnsubmitattendence);
        SUMBIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // StorageReference childs=mref.child("attend/");
                String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                UploadTask uploadTask = mref.putBytes(uemail.getBytes());
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(scanqrcode.this,"attendence marked ",Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(scanqrcode.this,attendence_marked.class));
            }
        });



        markattendence=(Button)findViewById(R.id.btnmarkattendence);
        markattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(scanqrcode.this,attendence.class));
            }
        });


    }
}
