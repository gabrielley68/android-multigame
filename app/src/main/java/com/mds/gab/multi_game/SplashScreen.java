package com.mds.gab.multi_game;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mds.gab.multi_game.utils.ActivityUtils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.launchActivity(SplashScreen.this, CreatePlayerActivity.class);
            }
        }, 1000);
    }
}