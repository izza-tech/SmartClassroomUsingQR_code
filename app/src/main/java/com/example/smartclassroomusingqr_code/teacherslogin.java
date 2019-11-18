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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.activities.Teachersregistration;
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

public class teacherslogin extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    GoogleSignInClient mGoogleSignInClientt;
    private Button logint;
    private EditText emailt;
    private EditText passwordt;
    SignInButton mGoogleloginbtnt;
    private FirebaseAuth mAuth;
    TextView mRecoverPasswordtvt,nothaveaccounttvt;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherslogin);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Teacher's Login");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

// Configure Google Sign In
        GoogleSignInOptions gsoo = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClientt= GoogleSignIn.getClient(this,gsoo);

        mAuth = FirebaseAuth.getInstance();


        logint = (Button) findViewById(R.id.btnloginteachers);
        logint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = emailt.getText().toString().trim();
                String PASSWORD = passwordt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    emailt.setError("Invalid email");
                    emailt.setFocusable(true);
                }else {
                    startActivity(new Intent(teacherslogin.this,teachers.class));
                    //loginuser(EMAIL,PASSWORD);
                }
            }
        });
        mGoogleloginbtnt=findViewById(R.id.teachersgoogleloginbtn);
        mGoogleloginbtnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntentt = mGoogleSignInClientt.getSignInIntent();
                startActivityForResult(signInIntentt, RC_SIGN_IN);

            }
        });

        nothaveaccounttvt=findViewById(R.id.teachersnothave_accounttv);
        nothaveaccounttvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacherslogin.this, Teachersregistration.class));
                finish();
            }
        });

        pd=new ProgressDialog(this);
        pd.setMessage("Logging In..... ");
        emailt=findViewById(R.id.teachemails);
        passwordt = (EditText) findViewById(R.id.teachpasswords);
        mRecoverPasswordtvt = (TextView) findViewById(R.id.teachersrecoverpasswordtv);
        //recover password TextviewCLICK
        mRecoverPasswordtvt.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(teacherslogin.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(teacherslogin.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(teacherslogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(teacherslogin.this,teachers.class));
                            finish();

                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(teacherslogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(teacherslogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

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
    private void firebaseAuthWithGoogle(GoogleSignInAccount acctt) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acctt.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
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

                            Toast.makeText(teacherslogin.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(teacherslogin.this,teachers.class));
                            finish();

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(teacherslogin.this, "Login Failed...", Toast.LENGTH_SHORT).show();

                            //  updateUI(null);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //GETAND SHOW ERROR
                Toast.makeText(teacherslogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
