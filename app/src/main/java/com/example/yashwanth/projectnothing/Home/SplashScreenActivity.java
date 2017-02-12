package com.example.yashwanth.projectnothing.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yashwanth.projectnothing.R;

public class SplashScreenActivity extends AppCompatActivity {
    public static  long timeOut=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                finish();
            }
        },timeOut);
    }
}
