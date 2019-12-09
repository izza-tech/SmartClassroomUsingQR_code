package com.example.smartclassroomusingqr_code;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class students extends AppCompatActivity {
    private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;
    private Button scanQRcode,stuentslogout,accesslectures,uploadissues,viewprofile;
    private Button attendencehistory , mcqquiz;
    private Button attemptquiz , timetable;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
//    private static final int uniqueID = 45618;

    ArrayList<String> urls=new ArrayList<>();
    FirebaseStorage storage;//used for uploading files
    FirebaseDatabase database;//used to store the url of uploaded files.



    @SuppressWarnings("deprecation")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);



        storage=FirebaseStorage.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE STORAGE

        database=FirebaseDatabase.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE database

        dref.child( "Time_Table" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()==8){
                    generateAlert();
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


        timetable = (Button) findViewById( R.id.btnTimeTable );
        timetable.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( students.this , ViewTimeTableforSemester.class );
                startActivity( i );
            }
        } );
        attemptquiz = (Button)findViewById( R.id.btncheckquiz );
        attemptquiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( students.this , StudentQuizSection.class );
                startActivity( i );
            }
        } );

        scanQRcode=(Button) findViewById(R.id.btnscanqrcode);
        scanQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openscanqrcode();
            }
        });

        uploadissues=(Button) findViewById(R.id.btnuploadissuesstudents);
        uploadissues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengroupchatactivity();
            }
        });


        viewprofile=(Button) findViewById(R.id.btnviewprofiles);
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openprofile();
            }
        });



        attendencehistory=(Button) findViewById(R.id.btnviewattendencehistory);
        attendencehistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openattendencehistory();
            }
        });

        accesslectures=findViewById(R.id.btnaccesslectures);
        accesslectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(students.this,myrecyclerviewActivity .class));
            }
        });

        stuentslogout=(Button) findViewById(R.id.btnstudentlogout);
        stuentslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (students.this,studentlogin_validation.class);
                startActivity(intent);
                finish();
                Toast.makeText(students.this,"successfully logut",Toast.LENGTH_LONG).show();
            }
        });
    }



    private void openprofile() {
        Intent intent=new Intent (students.this, StudentProfileDetail.class);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    public void openscanqrcode(){
        Intent intent=new Intent (this,scanqrcode.class);
        startActivity(intent);
    }
    @SuppressWarnings("deprecation")
    public void openattendencehistory(){
        Intent intent=new Intent (this,SelectAttendance.class);
        startActivity(intent);
    }

    public void opengroupchatactivity(){
        Intent intent=new Intent (this,Issue_Disscusion.class);
        startActivity(intent);
    }



    private void generateAlert() {
        
        createNotificationChannel();

        Intent notificationIintent = new Intent(students.this, ViewTimeTableforSemester.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(students.this);
        taskStackBuilder.addNextIntent(notificationIintent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_add_alert_black_24dp);
        builder.setContentTitle("TimeTable Alert");
        builder.setContentText("Kindly check TimeTable");
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        Intent intent = new Intent(this, students.class);
//        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent( pendingIntent );

        NotificationManager notificationManager = (NotificationManager)getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify(NOTIFICATION_ID,builder.build() );

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "personal";
            String Description = "Please review time table";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel( CHANNEL_ID,name,importance );
            notificationChannel.setDescription( Description );

            NotificationManager notificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
            notificationManager.createNotificationChannel( notificationChannel );
        }
    }

}

