package com.example.smartclassroomusingqr_code;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class notifications extends AppCompatActivity {
    private final String CHANNEL_ID="PERSONAL_NOTIFICATIONS";
    private final int NOTIFICATION_ID=001;
    public String inputvalues;

    EditText ettitle,etmessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setTitle("Assignments Notifications");

        ettitle=(EditText)findViewById(R.id.editTexttitle);
        etmessage=(EditText)findViewById(R.id.editTextmessag);
    }

    public void displaynotifications(View view) {
        Intent viewnotifications=new Intent(this,ViewNotifications.class);
        viewnotifications.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent viewnotificationsPendingIntent=PendingIntent.getActivity(this,0,viewnotifications,PendingIntent.FLAG_ONE_SHOT + PendingIntent.FLAG_UPDATE_CURRENT);

        inputvalues="Subject: "+ettitle.getText().toString().trim()+"\n Assignment: "+etmessage.getText().toString().trim();

        if (inputvalues.length()>0){
            createnotificationchannel();
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Assignments Notifications");
            builder.setContentText(inputvalues);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(inputvalues));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.setContentIntent(viewnotificationsPendingIntent);




      /* Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.cover);
        builder.setLargeIcon(bitmap);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));*/


            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());}

    }

    private void  createnotificationchannel(){

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="personal notification";
            String Description="include all the personal notifications";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;


            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(Description);
            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
