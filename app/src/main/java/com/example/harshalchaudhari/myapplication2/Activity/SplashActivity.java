package com.example.harshalchaudhari.myapplication2.Activity;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.harshalchaudhari.myapplication2.R;

import pl.droidsonroids.gif.GifImageView;


public class SplashActivity extends AppCompatActivity {
    private boolean isActivityVisible = false;
    private GifImageView gifImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
    }

    @Override
    protected void onResume () {
        super.onResume();
        isActivityVisible = true;
        delayedHide(4000);
    }

    @Override
    protected void onPause () {
        super.onPause();
        isActivityVisible = false;
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */

    private void delayedHide(int delayMillis) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isActivityVisible) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delayMillis);
    }
}

        /*gifImageView = (GifImageView)findViewById(R.id.gifImage);
        progressBar = (ProgressBar)findViewById(R.id.pro)//
        gifImageView = findViewById(R.id.gifImageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);package com.example.harshalchaudhari.myapplication2.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.harshalchaudhari.myapplication2.R;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME);




    }

    }


        try {
            InputStream inputStream = getAssets().open("drawable/LEGEND.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
                    //gifImageView.startAnimation();
        }
        catch(IOException ex)
        {

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        },20000);*/