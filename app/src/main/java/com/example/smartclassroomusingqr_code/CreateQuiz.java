package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateQuiz extends AppCompatActivity {

    String total;
    EditText txtSub,totalmcq;
    Button btnCreateQuiz;
    String selectSem,totalMcq,semester,totalquestion,subject;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_quiz );

//        selectsem = (Spinner) findViewById(R.id.semester);

        txtSub = (EditText)findViewById( R.id.txtSubject );
        totalmcq = (EditText) findViewById(R.id.totalquestion);

        btnCreateQuiz=(Button)findViewById( R.id.btnopen );

////create a list of items for the spinner.
//        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8" };
////create an adapter to describe how the items are displayed, adapters are used in several places in android.
////There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
////set the spinners adapter to the previously created one.
//        selectsem.setAdapter(adapter);
//
//
////create a list of items for the spinner.
//        String[] total = new String[]{ "5", "6", "7", "8","9","10" };
////create an adapter to describe how the items are displayed, adapters are used in several places in android.
////There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
////set the spinners adapter to the previously created one.
//        totalmcq.setAdapter(adapter);



        btnCreateQuiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savedata();
            }

            private boolean savedata() {
//                selectSem = selectsem.getSelectedItem().toString();
//                totalMcq = totalmcq.getSelectedItem().toString();
                subject = txtSub.getText().toString();
                totalMcq = totalmcq.getText().toString();
//                total = totalmcq.getText().toString();

                if(subject.isEmpty()){

                    txtSub.setError( "Please Enter The Subject Name" );
                    return false;
                }
                else if(totalMcq.isEmpty()){

                    totalmcq.setError( "Please Enter The Totol Mcq's" );
                    return false;

                }
                else {

                    Intent in = new Intent( CreateQuiz.this,GenerateMcqs.class );
                    in.putExtra( "subject", String.valueOf( subject.toUpperCase() ) );
                    in.putExtra( "totalmcq", String.valueOf( totalMcq ) );
//                    in.putExtra( "total", String.valueOf( total ) );
                    startActivity( in );
                }

                return false;

            }
        } );
    }
}
