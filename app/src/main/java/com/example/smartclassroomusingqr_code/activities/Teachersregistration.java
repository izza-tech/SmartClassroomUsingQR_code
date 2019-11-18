package com.example.smartclassroomusingqr_code.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.R;
import com.example.smartclassroomusingqr_code.studentlogin_validation;
import com.example.smartclassroomusingqr_code.students;
import com.example.smartclassroomusingqr_code.teachers;
import com.example.smartclassroomusingqr_code.teacherslogin;
import com.example.smartclassroomusingqr_code.teacherslogin_validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Teachersregistration extends AppCompatActivity {
    EditText txt_confirmpasswordt, txt_emailt, txt_passwordt;
    Button regbuttont;
    ProgressDialog progressDialog;
    TextView tHaveAccountTvt;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachersregistration);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Teachers Registeration");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        tHaveAccountTvt=findViewById(R.id.teacherhave_accounttv);

        txt_emailt = (EditText) findViewById(R.id.etteachersregemail);
        txt_confirmpasswordt = (EditText) findViewById(R.id.etteachersregconfirmpassword);
        txt_passwordt = (EditText) findViewById(R.id.etteachersregpassword);
        regbuttont = (Button) findViewById(R.id.btnregteachers);


        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering Teacher...");

        regbuttont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txt_emailt.getText().toString().trim();
                String password=txt_passwordt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    txt_emailt.setError("Invalid Email");
                    txt_emailt.setFocusable(true);

                }else if (password.length()<6){
                    txt_passwordt.setError("Pasword Length Must Be grester than 6 characters");
                    txt_passwordt.setFocusable(true);
                }else {
                    registeruser(email,password);
                }

            }
        });

        //handle login textview click listener

        tHaveAccountTvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Teachersregistration.this, teacherslogin.class));
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

                           /* String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object, String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("image","");

                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");

                            reference.child(uid).setValue(hashMap);*/


                            Toast.makeText(Teachersregistration.this, "Registered....\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Teachersregistration.this, teachers.class));
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Teachersregistration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Teachersregistration.this, ""+e.getMessage(),
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
