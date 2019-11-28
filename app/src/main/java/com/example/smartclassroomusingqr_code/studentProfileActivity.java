package com.example.smartclassroomusingqr_code;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.poi.ss.util.ImageUtils;

import java.io.IOException;
import java.util.UUID;

public class studentProfileActivity extends AppCompatActivity {
EditText pname,pmobile,pemail,paddress,pdob,psemester;
Button pconfirm , pback;
ImageView pimage;
private Uri filepath;
FirebaseDatabase database;
DatabaseReference ref;
FirebaseStorage storage;
StorageReference storageReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        pimage=(ImageView)findViewById(R.id.profimage);
        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image "),230);
            }
        });
        pname=(EditText)findViewById(R.id.profnames);
        pmobile=(EditText)findViewById(R.id.profmobile);
        pemail=(EditText)findViewById(R.id.profemail);
        paddress=(EditText)findViewById(R.id.profaddress);
        pdob=(EditText)findViewById(R.id.profdob);
        psemester=(EditText)findViewById(R.id.profsemester);
        pconfirm=(Button) findViewById(R.id.profCONFIRM);
        pback=(Button) findViewById(R.id.back);

        pback.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( studentProfileActivity.this , students.class );
                startActivity( i );
            }
        } );

        database=FirebaseDatabase.getInstance();
        ref = database.getReference("Student_Profiles");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String id=currentuser;
        pconfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pname.getText().toString().isEmpty() && !pmobile.getText().toString().isEmpty()&& !pemail.getText().toString().isEmpty()&& !paddress.getText().toString().isEmpty()&& !pmobile.getText().toString().isEmpty()&& !pdob.getText().toString().isEmpty() && !filepath.getPath().isEmpty() )
                {
                    final String push = FirebaseDatabase.getInstance().getReference().child("Packages").push().getKey();
                    StorageReference fileReference  = storageReference.child("images/"+ push);
                    fileReference.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                    if(filepath!=null) {
                                        profiledata profiledata = new profiledata();
                                        profiledata.setID(id);
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isSuccessful());
                                        Uri downloadUri = uriTask.getResult();
                                        profiledata.setIMAGEURL(downloadUri.toString());
                                        profiledata.setNAME(pname.getText().toString());
                                        profiledata.setMOBILE(pmobile.getText().toString());
                                        profiledata.setEMAIL(pemail.getText().toString());
                                        profiledata.setADDRESS(paddress.getText().toString());
                                        profiledata.setDOB(pdob.getText().toString());
                                        profiledata.setSEMESTER(psemester.getText().toString());

                                        ref.child(id)
                                                .setValue(profiledata);
                                        Toast.makeText(studentProfileActivity.this,"profile successfully saved..",Toast.LENGTH_LONG).show();

                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter all Information", Toast.LENGTH_SHORT).show();
                }

            }
        } );

        ref.child( currentuser ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    profiledata profiledata = dataSnapshot.getValue(profiledata.class);
                    if(profiledata != null) {
                        pname.setText(profiledata.getNAME());
                        pmobile.setText(profiledata.getMOBILE());
                        pemail.setText(profiledata.getEMAIL());
                        paddress.setText(profiledata.getADDRESS());
                        pdob.setText(profiledata.getDOB());
                        psemester.setText(profiledata.getSEMESTER());
                        Picasso.get().load(profiledata.getIMAGEURL()).into(pimage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );




        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==230&&resultCode==-1
                &&data!=null && data.getData()!=null){

            filepath=data.getData();
            try {
                Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                pimage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}

