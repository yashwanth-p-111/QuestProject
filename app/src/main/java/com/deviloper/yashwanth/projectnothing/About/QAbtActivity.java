package com.deviloper.yashwanth.projectnothing.About;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deviloper.yashwanth.projectnothing.R;

public class QAbtActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout common;
    TextView title, overv, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qabt);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setBackgroundColor(Color.parseColor("#0D47A1"));
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        common = (RelativeLayout) findViewById(R.id.commonHeader);
        title = (TextView) common.findViewById(R.id.abtHeader);
        overv = (TextView) common.findViewById(R.id.desc);
        overv.setTypeface(headFont);
        title.setTypeface(headFont);
        desc = (TextView) findViewById(R.id.abtDesc);
        desc.setTypeface(textFont);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0D47A1"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
