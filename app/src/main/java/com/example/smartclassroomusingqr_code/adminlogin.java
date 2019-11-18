package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartclassroomusingqr_code.activities.registeration;

public class adminlogin extends AppCompatActivity {
    private Button login;
    private EditText email;
    private Button register;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        getSupportActionBar().setTitle("Admin Login");

        login = (Button) findViewById(R.id.btnlogin);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregisteration();
            }
        });
    }

    private void openregisteration() {
        Intent intent=new Intent (this, registeration.class);
        startActivity(intent);
    }

    public void movepage(View view) {
        String stemail = email.getText().toString();
        String stpassword = password.getText().toString();

        if (stemail.equals("admin@gmail.com") && stpassword.equals("admin123")) {
            Intent intent = new Intent(this, admin.class);
            startActivity(intent);
            finish();

        }
        else if (stemail.equals("") || stpassword.equals(""))
        {
            Toast.makeText(getBaseContext(),"enter email and password",Toast.LENGTH_LONG).show();
        }
        else

        {
            Toast.makeText(getBaseContext(),"wrong email and password", Toast.LENGTH_LONG).show();
        }

    }
}

