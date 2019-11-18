package com.example.smartclassroomusingqr_code;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class myrecyclerviewActivity extends AppCompatActivity implements CallbackValue {
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recycler_view);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("files");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //called for individual items at the database ref....
                String filename = dataSnapshot.getKey();//returns file name..
              //  String ss = dataSnapshot.getValue().toString();
                String url = dataSnapshot.getValue(String.class);//returns the yrl for file name..
                ((Myadapter) recyclerView.getAdapter()).update(filename, url);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView = findViewById(R.id.recyclerview);

        //custom adapters always
        //populate recycler view with items

        recyclerView.setLayoutManager(new LinearLayoutManager(myrecyclerviewActivity.this));

        Myadapter myadapter = new Myadapter(recyclerView, myrecyclerviewActivity.this, new ArrayList<String>(), new ArrayList<String>(), this);
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public void onFetchValue(String key, String value) {
       StorageReference firebaseStorage =  FirebaseStorage.getInstance().getReference().child("Uploads").child(key+".pdf");
        Task<Uri> result = firebaseStorage.getDownloadUrl();
        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                openPdfFile(imageUrl);
                Log.d("url",imageUrl);
                //createNewPost(imageUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("url","failed");
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Log.d("url","complete");
            }
        });
        /* query = db.orderByKey().equalTo(key); //current user is "abhishek88666kumar" in this case.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Passcode doesn't match.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No passcode found for this user", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    private void openPdfFile(String url) {


        Intent intent = new Intent();
        intent.setType(Intent.ACTION_VIEW);
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(Uri.parse(url), "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(Uri.parse(url), "application/pdf");}//DENOTES THAT WE ARE GOING TO VIEW SOMETHING ...
        else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(Uri.parse(url), "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(Uri.parse(url), "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip")) {
            // ZIP file
            intent.setDataAndType(Uri.parse(url), "application/zip");
        } else if (url.toString().contains(".rar")){
            // RAR file
            intent.setDataAndType(Uri.parse(url), "application/x-rar-compressed");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(Uri.parse(url), "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(Uri.parse(url), "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(Uri.parse(url), "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(Uri.parse(url), "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(Uri.parse(url), "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(Uri.parse(url), "video/*");
        } else {
            intent.setDataAndType(Uri.parse(url), "*/*");
        }
            // intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
