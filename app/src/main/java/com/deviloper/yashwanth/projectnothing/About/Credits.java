package com.deviloper.yashwanth.projectnothing.About;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deviloper.yashwanth.projectnothing.R;

public class Credits extends AppCompatActivity {
    TextView creditsHead, name, reachme, gmail, phone, h1;
    Animation animation;
    RelativeLayout creditsLayout;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_from_corner, R.anim.scale_to_corner);
        setContentView(R.layout.yash_layout);
        creditsHead = (TextView) findViewById(R.id.creditsHead);
        name = (TextView) findViewById(R.id.myName);
        reachme = (TextView) findViewById(R.id.reachme);
        gmail = (TextView) findViewById(R.id.gmail);
        phone = (TextView) findViewById(R.id.phone);
        creditsLayout = (RelativeLayout) findViewById(R.id.creditsLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frame_pop);
        h1 = (TextView) findViewById(R.id.h1);
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        Typeface robotM = Typeface.createFromAsset(getAssets(), "fonts/robotom.ttf");
        creditsHead.setTypeface(textFont);
        name.setTypeface(robotM);
        reachme.setTypeface(headFont);
        gmail.setTypeface(textFont);
        phone.setTypeface(textFont);
        h1.setTypeface(headFont);
        animation = AnimationUtils.loadAnimation(this, R.anim.fromdowncredits);
        //   frameLayout.setAnimation(animation);

        creditsLayout.setAnimation(animation);
    }
}
