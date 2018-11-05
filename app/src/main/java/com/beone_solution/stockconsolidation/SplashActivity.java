package com.beone_solution.stockconsolidation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.logominimal);
        ImageView imageView1 = findViewById(R.id.logo);
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        imageView.startAnimation(animSlide);
        imageView1.startAnimation(animSlide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
