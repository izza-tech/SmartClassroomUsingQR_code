package com.example.smartclassroomusingqr_code;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class teachers extends AppCompatActivity {
    private Button generateqrcode;
    private Button teacherslogout;
    private Button replyqueries;
    private Button createquiz, quizResult;
    private Button uploadlectures;
    private Button uploadassignments;
    private Button viewdetails , fattendence,timetable;
    private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

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

        generateqrcode=(Button) findViewById(R.id.btngenerateqrcode);
        generateqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opengenerateqrcode();
            }
        });

        timetable = (Button) findViewById( R.id.btnTimeTable );
        timetable.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( teachers.this , ViewTimeTableforSemester.class );
                startActivity( i );
            }
        } );

        replyqueries=(Button) findViewById(R.id.btnreplyqueries);
        replyqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openreplyqueries();
            }
        });

        createquiz=(Button) findViewById(R.id.btncreatequiz);
        createquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengeneratequiz();
            }
        });

        uploadassignments=(Button) findViewById(R.id.btnUploadAssignments);
        uploadassignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opennotifications();
            }
        });

        uploadlectures=(Button) findViewById(R.id.btnuploadlectures);
        uploadlectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadlectures();
            }
        });

        fattendence=(Button) findViewById(R.id.btngenerateattendence);
        fattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,ExcelAttendanceSheet.class);
                startActivity(intent);
                    }
        });

        teacherslogout=(Button) findViewById(R.id.btnteacherslogout);
        teacherslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,teacherslogin.class);
                startActivity(intent);
                finish();
                Toast.makeText(teachers.this,"successfully logout",Toast.LENGTH_LONG).show();
            }
        });

        viewdetails=(Button) findViewById(R.id.btnviewdetails);
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (teachers.this,studet_details.class);
                startActivity(intent);

            }
        });
    }

    private void opengeneratequiz() {
        Intent intent=new Intent (this,TeacherGeneratQuiz.class);
        startActivity(intent);
    }


    private void openreplyqueries() {
        Intent intent=new Intent (this,Issue_Disscusion.class);
        startActivity(intent);
    }

    private void Uploadlectures() {
        Intent intent=new Intent (this,uploadlectures.class);
        startActivity(intent);

    }

    private void opennotifications() {
        Intent intent=new Intent (this,notifications.class);
        startActivity(intent);

    }

    @SuppressWarnings("deprecation")
    public void opengenerateqrcode(){
        Intent intent=new Intent (this,generate_QRcode.class);
        startActivity(intent);
    }

    private void generateAlert() {
        createNotificationChannel();

        Intent notificationIintent = new Intent(teachers.this, ViewTimeTableforSemester.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(teachers.this);
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
