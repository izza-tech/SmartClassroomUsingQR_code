package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button admin;
    private Button teachers;
    private Button students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Smart Classroom");

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        teachers = (Button) findViewById(R.id.btnteachers);
        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openteachersloginvalidation();
            }
        });
        admin = (Button) findViewById(R.id.btnadmin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openadminloginvalidation();
            }
        });

        students = (Button) findViewById(R.id.btnstudents);
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openstudentloginvalidation();
            }
        });

    }

    public void openadminloginvalidation(){
        Intent intent=new Intent (this,adminlogin.class);
        startActivity(intent);
    }

    public void openteachersloginvalidation(){
        Intent intent=new Intent (this,teacherslogin.class);
        startActivity(intent);
    }

    public void openstudentloginvalidation(){
     Intent intent=new Intent (this,studentlogin_validation.class);
        startActivity(intent);
    }

}


