package com.example.smartclassroomusingqr_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class scanqrcode extends AppCompatActivity {
    Button markattendence,SUMBIT;
    public static TextView textView;
    private StorageReference mref;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqrcode);
        textView=(TextView)findViewById(R.id.textview);
        String barcode = getIntent().getStringExtra("code");
        textView.setText(barcode);
        database= FirebaseDatabase.getInstance();
        ref = database.getReference("Student_Profiles");
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        final String currentTime = sdf.format(new Date());
        if(barcode != null) {
            String time = barcode.substring(Integer.valueOf(barcode.length()) - 9);
            String ST = barcode.substring(Integer.valueOf(barcode.length())-18);
            final String sub=ST.substring(0,3);
            final String subject = sub.toUpperCase();
            //Toast.makeText(getApplicationContext(), subject, Toast.LENGTH_LONG).show();

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
                        if(Integer.valueOf(t)<9) {
                            // StorageReference childs=mref.child("attend/");
                            final String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            if(currentuser != null) {
                                ref.child(currentuser).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        profiledata data = dataSnapshot.getValue(profiledata.class);

                                        String StudentName = data.getNAME();
                                        String Semester = data.getSEMESTER();
                                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                        if (StudentName != null) {
                                            database.getReference("Attendance").child(Semester).child(subject).child(date).child(StudentName).setValue(StudentName);
//                                        database.getReference("Attendance").child(Semester).child(subject).child(date).child(currentuser).child("Id").setValue(currentuser);
                                        }
                                        else
                                            Toast.makeText(getApplicationContext() , "Please complete your profile!" , Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else
                                {
                                Toast.makeText(getApplicationContext(), "Please complete your profile!", Toast.LENGTH_LONG).show();
                                }

                            UploadTask uploadTask = mref.putBytes(uemail.getBytes());
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(scanqrcode.this, "attendance marked ", Toast.LENGTH_SHORT).show();
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
