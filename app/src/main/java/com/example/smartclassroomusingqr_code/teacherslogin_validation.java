package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.activities.registeration;

public class teacherslogin_validation extends AppCompatActivity {
    private Button login;
    private EditText email;
    private EditText password;
    private TextView register;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherslogin_validation);
        getSupportActionBar().setTitle("Teachers Login ");



        login = (Button) findViewById(R.id.btnloginteachers);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);
        register = (TextView) findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregisteration();
            }
        });
    }
    public void movepage(View v) {
        String stemail = email.getText().toString();
        String stpassword = password.getText().toString();

        if (stemail.equals("Sir basit") && stpassword.equals("1234")) {
            Intent intent = new Intent(this,teachers.class);
            startActivity(intent);
            finish();

        }

        else if (stemail.equals("") || stpassword.equals(""))
        {
            Toast.makeText(getBaseContext(),"enter email and password",Toast.LENGTH_LONG).show();
        }
        else

        {
            Toast.makeText(getBaseContext(),"wrong email and password",Toast.LENGTH_LONG).show();
        }

    }
    @SuppressWarnings("deprecation")
    public void openregisteration(){
        Intent intent=new Intent (this, registeration.class);
        startActivity(intent);
    }

}

