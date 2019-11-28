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
                chooseimage();
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

    private void chooseimage() {
        Intent intent=new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image "),71);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==1&&resultCode==RESULT_OK
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

    private void getvalues() {
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String id=currentuser;
        final String namep =pname.getText().toString();
        final String mobilep =  pmobile.getText().toString();
        final String emailp = pemail.getText().toString();
        final String addressp =   paddress.getText().toString();
        final String dobp = pdob.getText().toString();
        final String semesterp = psemester.getText().toString();
         if (TextUtils.isEmpty(namep)||
                 TextUtils.isEmpty(mobilep)||
                 TextUtils.isEmpty(addressp)||
                TextUtils.isEmpty(dobp)||
                 TextUtils.isEmpty(semesterp) ||
                 !filepath.getPath().isEmpty()
         ){
            Toast.makeText(studentProfileActivity.this,"please enter all fields..",Toast.LENGTH_LONG).show();

        }
        else
        {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            //String push = FirebaseDatabase.getInstance().getReference().child("Student_Profiles").push().getKey();
            StorageReference fileReference = storageReference.child("images/"+id );
            fileReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               if(filepath!=null)
               {
                  profiledata profiledata = new profiledata();
                  profiledata.setID(id);
                   Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                   while (!uriTask.isSuccessful());
                   Uri downloadUri = uriTask.getResult();
                   profiledata.setIMAGEURL(downloadUri.toString());
                   profiledata.setNAME(namep);
                   profiledata.setMOBILE(mobilep);
                   profiledata.setEMAIL(emailp);
                   profiledata.setADDRESS(addressp);
                   profiledata.setDOB(dobp);
                   profiledata.setSEMESTER(semesterp);

                   ref.child(id).setValue(profiledata);
                   Toast.makeText(studentProfileActivity.this,"profile successfully saved..",Toast.LENGTH_LONG).show();

               }
                }
            });
//          profiledata  profiledata=new profiledata(id,namep,mobilep,emailp,addressp,dobp,semesterp);
//          ref.child(id).setValue(profiledata);
//            Toast.makeText(studentProfileActivity.this,"profile successfully saved..",Toast.LENGTH_LONG).show();


        }
    }

        public void confirmprofile(View view) {
        getvalues();
        uploadimage();
        }

    private void uploadimage() {
        if (filepath!=null){

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("UPLOADING...");
            progressDialog.show();

            StorageReference reference=storageReference.child("images/" +UUID.randomUUID().toString());

            reference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(studentProfileActivity.this,"IMAGE UPLOADED..",Toast.LENGTH_LONG).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });

        }
    }


}

