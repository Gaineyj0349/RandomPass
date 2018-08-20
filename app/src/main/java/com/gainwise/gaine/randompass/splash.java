package com.gainwise.gaine.randompass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import spencerstudios.com.bungeelib.Bungee;

public class splash extends AppCompatActivity implements Animation.AnimationListener {

    AlphaAnimation alphaAnimation2;
    ImageView imageView;
    AlphaAnimation alphaAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags((WindowManager.LayoutParams.FLAG_FULLSCREEN), WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        imageView = (ImageView)findViewById(R.id.image);
        alphaAnimation= new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setAnimationListener(this);
        alphaAnimation2= new AlphaAnimation(1f, 0f);
        alphaAnimation2.setDuration(400);
        alphaAnimation2.setAnimationListener(this);

        imageView.startAnimation(alphaAnimation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }



    @Override
    public void onAnimationEnd(Animation animation) {

        if(animation.equals(alphaAnimation)){
            imageView.startAnimation(alphaAnimation2);
        }
        if(animation.equals(alphaAnimation2)){
            imageView.setVisibility(View.GONE);
            Intent intent = new Intent(splash.this,MainActivity.class);

            startActivity(intent);
            Bungee.slideUp(splash.this);
        }



    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }



    @Override
    protected void onPause() {
        super.onPause();
        splash.this.finish();
    }
}
