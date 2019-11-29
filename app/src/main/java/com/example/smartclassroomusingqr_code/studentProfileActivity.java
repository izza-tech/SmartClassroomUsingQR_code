package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class studentProfileActivity extends AppCompatActivity {
    EditText pname,pmobile,pemail,paddress,pdob,psemester;
    Button pconfirm , pback;
    ImageView pimage;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference StorageRef;
    FirebaseDatabase database;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        pimage=(ImageView)findViewById(R.id.profimage);
        pname=(EditText)findViewById(R.id.profnames);
        pmobile=(EditText)findViewById(R.id.profmobile);
        pemail=(EditText)findViewById(R.id.profemail);
        paddress=(EditText)findViewById(R.id.profaddress);
        pdob=(EditText)findViewById(R.id.profdob);
        psemester=(EditText)findViewById(R.id.profsemester);
        pconfirm=(Button) findViewById(R.id.profCONFIRM);
        pback=(Button) findViewById(R.id.back);
        StorageRef = FirebaseStorage.getInstance().getReference();
        pback.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( studentProfileActivity.this , students.class );
                startActivity( i );
            }
        } );
        reference =  FirebaseDatabase.getInstance().getReference();

        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference.child("Student_Profiles").child( currentuser ).addValueEventListener( new ValueEventListener() {
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

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        pconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getApplicationContext()!=null) {
                    if (!pname.getText().toString().isEmpty() && !pmobile.getText().toString().isEmpty() && !pemail.getText().toString().isEmpty() && !paddress.getText().toString().isEmpty() && !pdob.getText().toString().isEmpty() && !psemester.getText().toString().isEmpty() &&!filePath.getPath().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inserting Please wait", Toast.LENGTH_LONG).show();
                        final String push = FirebaseDatabase.getInstance().getReference().child("Student_Profiles").push().getKey();
                        StorageReference fileReference = StorageRef.child("images/" + push);
                        fileReference.putFile(filePath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                        if (filePath != null) {
                                            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            final String id = currentuser;
                                            profiledata profiledata = new profiledata();
                                            profiledata.setID(id);
                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!urlTask.isSuccessful()) ;
                                            Uri downloadUrl = urlTask.getResult();
                                            profiledata.setIMAGEURL(downloadUrl.toString());
                                            profiledata.setNAME(pname.getText().toString());
                                            profiledata.setMOBILE(pmobile.getText().toString());
                                            profiledata.setEMAIL(pemail.getText().toString());
                                            profiledata.setADDRESS(paddress.getText().toString());
                                            profiledata.setDOB(pdob.getText().toString());
                                            profiledata.setSEMESTER(psemester.getText().toString());

                                            reference.child("Student_Profiles").child(id)
                                                    .setValue(profiledata);
                                            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter all Information", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getApplicationContext().getContentResolver(), filePath);
                pimage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
