package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.activities.registeration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class studentlogin_validation extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    private Button login;
    private EditText email;
    private EditText password;
    SignInButton mGoogleloginbtn;
    private FirebaseAuth mAuth;
    TextView mRecoverPasswordtv,nothaveaccounttv;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin_validation);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Student Login");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);

        mAuth = FirebaseAuth.getInstance();


      login = (Button) findViewById(R.id.btnloginstudents);
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String EMAIL = email.getText().toString().trim();
              String PASSWORD = password.getText().toString().trim();
              if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                 email.setError("Invalid email");
                 email.setFocusable(true);
              }else {
                  //startActivity(new Intent(studentlogin_validation.this,students.class));
                  loginuser(EMAIL,PASSWORD);
              }
          }
      });
      mGoogleloginbtn=findViewById(R.id.googleloginbtn);
      mGoogleloginbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent signInIntent = mGoogleSignInClient.getSignInIntent();
              startActivityForResult(signInIntent, RC_SIGN_IN);

          }
      });

        nothaveaccounttv=findViewById(R.id.nothave_accounttv);
        nothaveaccounttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(studentlogin_validation.this, registeration.class));
            finish();
            }
        });

        pd=new ProgressDialog(this);
        pd.setMessage("Logging In..... ");
        email=findViewById(R.id.etloginemail);
        password = (EditText) findViewById(R.id.etloginpassword);
        mRecoverPasswordtv = (TextView) findViewById(R.id.recoverpasswordtv);
        //recover password TextviewCLICK
        mRecoverPasswordtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });



}

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailET=new EditText(this);
        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(16);



        linearLayout.addView(emailET);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        //button recover
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailET.getText().toString().trim();
                beginRecovery(email);
            }
        });
//button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {

        pd.setMessage("Sending Email....");
        pd.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(studentlogin_validation.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(studentlogin_validation.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(studentlogin_validation.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginuser(String EMAIL, String PASSWORD) {
        pd.setMessage("Logging In....");
        pd.show();
        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(studentlogin_validation.this,students.class));
                            finish();

                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(studentlogin_validation.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(studentlogin_validation.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
              Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (task.getResult().getAdditionalUserInfo().isNewUser()){
                                String email=user.getEmail();
                                String uid=user.getUid();
                                HashMap<Object, String> hashMap=new HashMap<>();
                                hashMap.put("email",email);
                                hashMap.put("uid",uid);
                                hashMap.put("name","");
                                hashMap.put("phone","");
                                hashMap.put("image","");
                                hashMap.put("cover","");

                                //firebase data instance
                                FirebaseDatabase database=FirebaseDatabase.getInstance();

                                //path to store user data named 'Users'
                                DatabaseReference reference=database.getReference("Users");

                                //PUT Data within hashmap in database
                                reference.child(uid).setValue(hashMap);
                            }



                            Toast.makeText(studentlogin_validation.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(studentlogin_validation.this,students.class));
                            finish();

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(studentlogin_validation.this, "Login Failed...", Toast.LENGTH_SHORT).show();

                            //  updateUI(null);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //GETAND SHOW ERROR
                Toast.makeText(studentlogin_validation.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
