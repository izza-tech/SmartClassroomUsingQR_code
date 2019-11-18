package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class splashh extends AwesomeSplash {
        @SuppressWarnings("deprecation")
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/


    @Override
    public void initSplash(ConfigSplash configSplash) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //BackgroundAnimation
        configSplash.setBackgroundColor(R.color.bg_splash);
        configSplash.setAnimCircularRevealDuration(3000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);
        //logo
        configSplash.setLogoSplash(R.drawable.logo);
        configSplash.setAnimLogoSplashDuration(3000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

        //title
        configSplash.setTitleSplash(getString(R.string.title));
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(40f);
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);


    }

    @SuppressWarnings("deprecation")
        @Override
        public void animationsFinished() {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        }
    }

