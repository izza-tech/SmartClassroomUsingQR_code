package com.example.smartclassroomusingqr_code.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.R;
import com.example.smartclassroomusingqr_code.studentlogin_validation;
import com.example.smartclassroomusingqr_code.students;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registeration extends AppCompatActivity {
    EditText txt_confirmpassword, txt_email, txt_password;
    Button regbutton;
    ProgressDialog progressDialog;
    TextView mHaveAccountTv;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Student Registeration");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        mHaveAccountTv=findViewById(R.id.have_accounttv);

        txt_email = (EditText) findViewById(R.id.etregemail);
        txt_confirmpassword = (EditText) findViewById(R.id.etregconfirmpassword);
        txt_password = (EditText) findViewById(R.id.etregpassword);
        regbutton = (Button) findViewById(R.id.btnreg);


        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering Student...");

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email=txt_email.getText().toString().trim();
                String password=txt_password.getText().toString().trim();
if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

    txt_email.setError("Invalid Email");
    txt_email.setFocusable(true);

}else if (password.length()<6){
    txt_password.setError("Pasword Length Must Be grester than 6 characters");
    txt_password.setFocusable(true);
}else {
    registeruser(email,password);
}

            }
        });

        //handle login textview click listener

        mHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registeration.this, studentlogin_validation.class));
                finish();
            }
        });
    }

    private void registeruser(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object, String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("image","");
                            hashMap.put("cover","");

                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");

                            reference.child(uid).setValue(hashMap);


                            Toast.makeText(registeration.this, "Registered....\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(registeration.this, students.class));
                                    finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(registeration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(registeration.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}