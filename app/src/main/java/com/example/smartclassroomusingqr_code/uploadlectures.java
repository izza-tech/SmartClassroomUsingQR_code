package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadlectures extends AppCompatActivity {
    Button selectfile,uploadfile;
    TextView notification;
    Uri pdfUri;//URI ARE ACTUALLY URLS THAT ARE MEANT FOR LOCAL STORAGE
    ProgressDialog progressDialog;
    FirebaseStorage storage;//used for uploading files
    FirebaseDatabase database;//used to store the url of uploaded files.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadlectures);
        storage=FirebaseStorage.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE STORAGE

        database=FirebaseDatabase.getInstance();//RETURNS AN OBJECT OF CURRENT FIREBASE database


        selectfile=findViewById(R.id.btnselectfile);
        uploadfile=findViewById(R.id.btnuploadfile);
        notification=findViewById(R.id.textViewnotification);



        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission( uploadlectures.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectpdf();

                }else {
                    ActivityCompat.requestPermissions(uploadlectures.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pdfUri!=null)//user has selected the file
                    uploadFile(pdfUri);
                else
                    Toast.makeText(uploadlectures.this, "select a file", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void uploadFile(Uri pdfUri) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("uploading file....");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String fileName=System.currentTimeMillis()+".pdf";
        final String fileName1=System.currentTimeMillis()+"";

        StorageReference storageReference=storage.getReference();//returns root path
        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url=taskSnapshot.getUploadSessionUri().toString();//return the url of uploaded file
                        //store the url in realtime database
                        //DatabaseReference reference=database.getReference();//returns the path to root

                        FirebaseDatabase database=FirebaseDatabase.getInstance();

                        DatabaseReference reference=database.getReference("files");

                        reference.child(fileName1).setValue(url)

                        //reference.child(fileName1).setValue(url)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(uploadlectures.this, "file successfully uploaded...", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }else {
                                            Toast.makeText(uploadlectures.this, "file not successfully uploaded...", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadlectures.this, "file not successfully uploaded...", Toast.LENGTH_LONG).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //track the progress of our upload
                int currentprogress=(int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentprogress);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectpdf();
        }else {
            Toast.makeText(uploadlectures.this, "please provide permission", Toast.LENGTH_LONG).show();
        }
    }
    private void selectpdf() {
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 86);

        //to offer user to select a file using file manager
        //we will be using an intent

       /* Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);//to fetch  files
        startActivityForResult(intent,86);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //check weather user has selected a file of not
        if (requestCode==86&&resultCode==RESULT_OK&&data!=null) {
            pdfUri=data.getData();//return the uri of selected file
            notification.setText("File is selected: "+data.getData().getLastPathSegment());
        }else {
            Toast.makeText(uploadlectures.this, "please select the file", Toast.LENGTH_LONG).show();
        }

    }
}
