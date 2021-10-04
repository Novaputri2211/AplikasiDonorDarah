package com.example.aplikasidonordarah.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.aplikasidonordarah.Intro.LoginActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.transition);
        binding.splashIn.setAnimation(animation);

        Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }

                super.run();
            }
        };

        timer.start();
    }
}