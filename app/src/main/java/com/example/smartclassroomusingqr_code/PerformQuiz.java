package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.xml.transform.Result;

public class PerformQuiz extends AppCompatActivity {
    String opt1,opt2,opt3,opt4,que,ans,ans_a="a",ans_b ="b",ans_c="c",ans_d="d",totalque ,tt ;
    int totalquestion , t;
    Button btnnext;
    TextView subject,totalQuestion,questionNo,counter,question;
    RadioButton btn_a,btn_b,btn_c,btn_d,ab;
    RadioGroup groupAB,groupCD;
    String semester,quizSubject;
    String totalQuestionNo;
    int total= 0;
    int correct=0;
    int wrong=0;

    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perform_quiz );

        subject = (TextView)findViewById( R.id.txtsubject );
        totalQuestion = (TextView) findViewById( R.id.txttotalquestion );
        questionNo = findViewById( R.id.txtQuestionNo );
        btn_a = (RadioButton)findViewById( R.id.opt_a );
        btn_b = (RadioButton)findViewById( R.id.opt_b );
        btn_c = (RadioButton)findViewById( R.id.opt_c );
        btn_d = (RadioButton)findViewById( R.id.opt_d );
        groupAB =  (RadioGroup) findViewById(R.id.radio_ab);
        btnnext = (Button)findViewById( R.id.btnnext );
//        groupCD =  (RadioGroup) findViewById(R.id.radio_cd);

        question = (TextView)findViewById( R.id.txtquiztext );
        counter = (TextView)findViewById( R.id.txttimer );

        dref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent in =getIntent();
                quizSubject = in.getStringExtra( "subject" );
                subject.setText( quizSubject );
                totalque =dataSnapshot.child("QuizInfo").child( quizSubject.toUpperCase() ).child("total").getValue().toString();
                totalQuestion.setText( String.valueOf( totalque ) );
                tt = String.valueOf( totalque );
                t = Integer.parseInt( tt );
                updateQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        btnnext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();

            }
        } );



        reverseTimer( 60, counter );

    }

    private void updateQuestion() {

        total++;
        if(total<=t)
        questionNo.setText(String.valueOf(total));
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if(total>t){

            Intent i = new Intent( PerformQuiz.this, QuizResult.class );
            i.putExtra( "total",String.valueOf( t ) );
            i.putExtra( "correct",String.valueOf( correct ) );
            i.putExtra( "wrong",String.valueOf( wrong ) );
            i.putExtra( "quizSubject",String.valueOf( quizSubject ) );

            startActivity( i );

        }
        else {

//                    questionNo.setText( total );
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        que = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "question" ).getValue().toString();
                        opt1 = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "option1" ).getValue().toString();
                        opt2 = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "option2" ).getValue().toString();
                        opt3 = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "option3" ).getValue().toString();
                        opt4 = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "option4" ).getValue().toString();
                        ans = dataSnapshot.child( "QuizQuestions" ).child( quizSubject.toUpperCase() ).child( String.valueOf( total ) ).child( "answer" ).getValue().toString();

                        question.setText( que );
                        btn_a.setText( opt1 );
                        btn_b.setText( opt2 );
                        btn_c.setText( opt3 );
                        btn_d.setText( opt4 );
//                    Toast.makeText( PerformQuiz.this, "total"+total, Toast.LENGTH_SHORT ).show();
//                    questionNo.setText( total );
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );


        }
    }



    public void reverseTimer(int seconds, final TextView tv){
        new CountDownTimer( seconds* 1000+1000, 1000){

            @Override
            public void onTick(long l) {
                int seconds = (int) (l/1000);
                int minutes = seconds/60;
                seconds = seconds%60;
                tv.setText( String.format( "%02d", minutes )
                        +":"+ String.format( "%02d", seconds  ));

            }

            @Override
            public void onFinish() {
                Intent i = new Intent( PerformQuiz.this, QuizResult.class );
                i.putExtra( "total",String.valueOf( t ) );
                i.putExtra( "correct",String.valueOf( correct ) );
                i.putExtra( "wrong",String.valueOf( wrong ) );
                i.putExtra( "quizSubject",String.valueOf( quizSubject ) );

                startActivity( i );


            }
        }.start();
    }

    public void onRadioButtonClicked(View view) {

//        String selectedId = String.valueOf( groupAB.getCheckedRadioButtonId() );
        if (btn_a.isChecked()) {
//            if(ans_a.equals( ans ))
//                Toast.makeText( this, "True", Toast.LENGTH_SHORT ).show();
            if (ans_a.equals( ans )) {
//                btn_a.setTextColor( Color.GREEN );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        correct++;
//                        btn_a.setTextColor( Color.BLACK );
//                        updateQuestion();

                    }
                }, 1000 );
            }
            else {
                wrong++;
//                btn_a.setTextColor( Color.RED );

//                if(ans_b.equals( ans )) {
//                    btn_b.setTextColor( Color.GREEN );
//                }
//                else if(ans_c.equals( ans )) {
//                    btn_c.setTextColor( Color.GREEN );
//                }
//                else if(ans_d.equals( ans )) {
//                    btn_d.setTextColor( Color.GREEN );
//
//                }
                Handler handler = new Handler(  );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        btn_a.setTextColor( Color.BLACK );
                        btn_b.setTextColor( Color.BLACK );
                        btn_c.setTextColor( Color.BLACK );
                        btn_d.setTextColor( Color.BLACK );
//                        updateQuestion();
                    }
                },1000 );
            }


        }

        else if (btn_b.isChecked()) {
            if (ans_b.equals( ans )) {
//                btn_b.setTextColor( Color.GREEN );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        correct++;
                        btn_b.setTextColor( Color.BLACK );
//                        updateQuestion();

                    }
                }, 1000 );
            }
            else {
                wrong++;
//                btn_b.setTextColor( Color.RED );
//
//                if(ans_a.equals( ans )) {
//                    btn_a.setTextColor( Color.GREEN );
//                }
//                else if(ans_c.equals( ans )) {
//                    btn_c.setTextColor( Color.GREEN );
//                }
//                else if(ans_d.equals( ans )) {
//                    btn_d.setTextColor( Color.GREEN );
//
//                }
                Handler handler = new Handler(  );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        btn_a.setTextColor( Color.BLACK );
                        btn_b.setTextColor( Color.BLACK );
                        btn_c.setTextColor( Color.BLACK );
                        btn_d.setTextColor( Color.BLACK );
//                        updateQuestion();
                    }
                },1000 );
            }
        }

        else if (btn_c == view) {
            if (ans_c.equals( ans )) {
//                btn_c.setTextColor( Color.GREEN );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        correct++;
                        btn_c.setTextColor( Color.BLACK );
//                        updateQuestion();

                    }
                }, 1000 );
            }
            else {
                wrong++;
//                btn_c.setTextColor( Color.RED );
//
//                if(ans_b.equals( ans )) {
//                    btn_b.setTextColor( Color.GREEN );
//                }
//                else if(ans_a.equals( ans )) {
//                    btn_a.setTextColor( Color.GREEN );
//                }
//                else if(ans_d.equals( ans )) {
//                    btn_d.setTextColor( Color.GREEN );
//
//                }
                Handler handler = new Handler(  );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        btn_a.setTextColor( Color.BLACK );
                        btn_b.setTextColor( Color.BLACK );
                        btn_c.setTextColor( Color.BLACK );
                        btn_d.setTextColor( Color.BLACK );
//                        updateQuestion();
                    }
                },1000 );
            }
        }

        if (btn_d.isChecked()) {
            if (ans_d.equals( ans )) {
//                btn_d.setTextColor( Color.GREEN );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        correct++;
                        btn_d.setTextColor( Color.BLACK );
//                        updateQuestion();

                    }
                }, 1000 );
            }
            else {
                wrong++;
//                btn_d.setTextColor( Color.RED );
//
//                if(ans_b.equals( ans )) {
//                    btn_b.setTextColor( Color.GREEN );
//                }
//                else if(ans_c.equals( ans )) {
//                    btn_c.setTextColor( Color.GREEN );
//                }
//                else if(ans_a.equals( ans )) {
//                    btn_a.setTextColor( Color.GREEN );
//
//                }
                Handler handler = new Handler(  );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        btn_a.setTextColor( Color.BLACK );
                        btn_b.setTextColor( Color.BLACK );
                        btn_c.setTextColor( Color.BLACK );
                        btn_d.setTextColor( Color.BLACK );
//                        updateQuestion();
                    }
                },1000 );
            }
        }
    }
}
