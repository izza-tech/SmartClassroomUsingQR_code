package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class StudentProfileDetail extends AppCompatActivity {
    TextView pname,pmobile,pemail,paddress,pdob,psemester;
    Button pupdate , pback;
    ImageView pimage;
    private Uri filepath;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseStorage storage;
    StorageReference storageReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_detail);
        pimage=(ImageView)findViewById(R.id.profimage);
        pname=(TextView) findViewById(R.id.profnames);
        pmobile=(TextView)findViewById(R.id.profmobile);
        pemail=(TextView)findViewById(R.id.profemail);
        paddress=(TextView)findViewById(R.id.profaddress);
        pdob=(TextView)findViewById(R.id.profdob);
        psemester=(TextView)findViewById(R.id.profsemester);
        pupdate=(Button) findViewById(R.id.profCONFIRM);
        pback=(Button) findViewById(R.id.back);

        pback.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( StudentProfileDetail.this , students.class );
                startActivity( i );
            }
        } );

        database=FirebaseDatabase.getInstance();
        ref = database.getReference("Student_Profiles");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        ref.child( currentuser ).addValueEventListener( new ValueEventListener() {
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
        pupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( StudentProfileDetail.this , studentProfileActivity.class );
                startActivity( i );
            }
        });
    }
}
