package com.deviloper.yashwanth.projectnothing.Home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.deviloper.yashwanth.projectnothing.R;

public class SplashScreenActivity extends AppCompatActivity {
    public static long timeOut = 2800;
    TextView qtext, htext;
    ImageView img;
    Animation fromDown, fromUp, fromSide, bounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        checkIntent(getIntent());

        // Toast.makeText(this, "value is"+value, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_splash_screen);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        img = (ImageView) findViewById(R.id.logo);
        qtext = (TextView) findViewById(R.id.qtext);
        htext = (TextView) findViewById(R.id.deptHead);
        Typeface montBold = Typeface.createFromAsset(getAssets(), "fonts/montb.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        qtext.setTypeface(montBold);
        htext.setTypeface(headFont);
        fromDown = AnimationUtils.loadAnimation(this, R.anim.fromdown);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        fromUp = AnimationUtils.loadAnimation(this, R.anim.fromup);
        fromSide = AnimationUtils.loadAnimation(this, R.anim.fromside);
        // img.setAnimation(fromSide);
        img.setAnimation(bounce);
        qtext.setAnimation(fromDown);
        htext.setAnimation(fromUp);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                finish();
            }
        }, timeOut);
    }

    /*@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }
    public void checkIntent(Intent intent)
    {
      if(intent.hasExtra("click_action")){
          ClickActionHelper.startActivity(intent.getStringExtra("click_action"),intent.getExtras(),this);
      }
    }*/
}
