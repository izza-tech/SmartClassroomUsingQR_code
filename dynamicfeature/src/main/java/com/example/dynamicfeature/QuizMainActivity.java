package com.example.dynamicfeature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dynamicfeature.Model.quiz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizMainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4;
    TextView t1_question, timertxt;
    int total = 1;
    int correct = 0;
    int wrong = 0;

    DatabaseReference reference;
    Handler handler = new Handler();
    List<quiz> curQuizList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        t1_question = (TextView) findViewById(R.id.questiontext);
        timertxt = (TextView) findViewById(R.id.timertxt);


        updateQuestion();
        reverseTimer(30, timertxt);

    }

    private void updateQuestion() {
        if (this.total > 11) {
            //OPEN THE RESULT ACTIVITY
            Intent i = new Intent(QuizMainActivity.this, QuizResultActivity.class);
            i.putExtra("total", String.valueOf(total));
            startActivity(i);
        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("questions").child(String.valueOf(0));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final quiz quiz = dataSnapshot.getValue(quiz.class);
                    Log.d("hello",quiz.getQuestion());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz workingQuz = curQuizList.get(Integer.parseInt(b1.getTag().toString()));
                    if (workingQuz.getCorrectIndex() == 0) {
                        b1.setBackgroundColor(Color.GREEN);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            }
                        }, 1500);
                    } else {
                        // answer is wrong we will find the correct answer and make it green
                        wrong++;
                        b1.setBackgroundColor(Color.RED);
                        if (workingQuz.getCorrectIndex() == 1) {
                            b2.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 2) {
                            b3.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 3) {
                            b4.setBackgroundColor(Color.GREEN);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();


                            }


                        }, 1500);
                    }
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz workingQuz = curQuizList.get(Integer.parseInt(b1.getTag().toString()));
                    if (workingQuz.getCorrectIndex() == 1) {
                        b2.setBackgroundColor(Color.GREEN);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            }
                        }, 1500);
                    } else {
                        // answer is wrong we will find the correct answer and make it green
                        wrong++;
                        b2.setBackgroundColor(Color.RED);
                        if (workingQuz.getCorrectIndex() == 0) {
                            b1.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 2) {
                            b3.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 3) {
                            b4.setBackgroundColor(Color.GREEN);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();


                            }
                        }, 1500);
                    }
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz workingQuz = curQuizList.get(Integer.parseInt(b1.getTag().toString()));
                    if (workingQuz.getCorrectIndex() == 2) {
                        b3.setBackgroundColor(Color.GREEN);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            }
                        }, 1500);
                    } else {
                        // answer is wrong we will find the correct answer and make it green
                        wrong++;
                        b3.setBackgroundColor(Color.RED);
                        if (workingQuz.getCorrectIndex() == 0) {
                            b1.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 1) {
                            b2.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 3) {
                            b4.setBackgroundColor(Color.GREEN);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            }

                        }, 1500);


                    }
                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz workingQuz = curQuizList.get(Integer.parseInt(b1.getTag().toString()));
                    if (workingQuz.getCorrectIndex() == 3) {
                        b4.setBackgroundColor(Color.GREEN);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            }
                        }, 1500);
                    } else {
                        // answer is wrong we will find the correct answer and make it green
                        wrong++;
                        b4.setBackgroundColor(Color.RED);
                        if (workingQuz.getCorrectIndex() == 0) {
                            b1.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 1) {
                            b2.setBackgroundColor(Color.GREEN);
                        } else if (workingQuz.getCorrectIndex() == 2) {
                            b3.setBackgroundColor(Color.GREEN);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();


                            }

                        }, 1500);

                    }
                }

            });
        }

    }

    public void reverseTimer(int seconds, final TextView tv) {
        new CountDownTimer(seconds * 1000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%2d", minutes) + ":" + String.format("%2d", seconds));
            }

            @Override
            public void onFinish() {
                tv.setText("completed");
                Intent myIntent = new Intent(QuizMainActivity.this, QuizResultActivity.class);
                myIntent.putExtra("total", String.valueOf(total));
                myIntent.putExtra("correct ", String.valueOf(correct));
                myIntent.putExtra("incorrect", String.valueOf(wrong));
                startActivity(myIntent);
            }

        }.start();
    }

    private void updateQuestionWithAnswer(boolean isFirstTime, int nextIndex) {
        if (isFirstTime) {
            quiz quiz = curQuizList.get(0);
            t1_question.setText(quiz.getQuestion());
            b1.setText(quiz.getAnswers().get(0));
            b1.setTag(0);
            b2.setText(quiz.getAnswers().get(1));
            b2.setTag(0);
            b3.setText(quiz.getAnswers().get(2));
            b3.setTag(0);
            b4.setText(quiz.getAnswers().get(3));
            b4.setTag(0);
        } else {
            quiz quiz = curQuizList.get(nextIndex);
            t1_question.setText(quiz.getQuestion());
            b1.setText(quiz.getAnswers().get(0));
            b1.setTag(nextIndex);
            b2.setText(quiz.getAnswers().get(1));
            b2.setTag(nextIndex);
            b3.setText(quiz.getAnswers().get(2));
            b3.setTag(nextIndex);
            b4.setText(quiz.getAnswers().get(3));
            b4.setTag(nextIndex);
        }
    }
}

