package com.example.yashwanth.projectnothing.Home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yashwanth.projectnothing.About.JAbtActivity;
import com.example.yashwanth.projectnothing.About.QAbtActivity;
import com.example.yashwanth.projectnothing.R;
import com.github.clans.fab.FloatingActionButton;


public class HomeActivity extends AppCompatActivity {
    ScrollView scrollView;
    RelativeLayout abtJ, abtQ, abtUs;
    TextView nameJ, nameQ, wsite, wsiteHeader;
    Toolbar toolbar;
    FloatingActionButton fab1, fab2, fab3;
    String FACEBOOK_URL = "https://www.facebook.com/questJNTUH";
    String FACEBOOK_PAGE_ID = "questJNTUH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        final NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setSmoothScrollingEnabled(true);
        scrollView.setFillViewport(true);
        scrollView.setClipToPadding(true);
        abtUs = (RelativeLayout) findViewById(R.id.aboutUs);
        wsite = (TextView) findViewById(R.id.wsite);
        wsiteHeader = (TextView) findViewById(R.id.wsiteheader);
        setFonts();
        fabCode();
        setAbout();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }*/
    public void fabCode() {
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCTlcnhgKr-FieSJCa7OxvCA")));
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(Intent.ACTION_VIEW);
              String fburl=getFacebookPageURL(HomeActivity.this);
                intent.setData(Uri.parse(fburl));
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent();
                view.setAction(Intent.ACTION_SENDTO);
                view.putExtra(Intent.EXTRA_EMAIL,new String[]{"csequest@gmail.com"});
                view.setType("message/rfc822");
                HomeActivity.this.startActivity(Intent.createChooser(view, "Query us..."));
            }
        });

    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            else {
                //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
    public void setFonts() {
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        ((TextView) findViewById(R.id.mainText)).setTypeface(textFont);
        ((TextView) abtUs.findViewById(R.id.aHead)).setTypeface(headFont);
        wsiteHeader.setTypeface(headFont);

    }

    public void setAbout() {
        abtJ = (RelativeLayout) findViewById(R.id.abtJ);
        abtQ = (RelativeLayout) findViewById(R.id.abtQ);
        nameJ = (TextView) abtJ.findViewById(R.id.name);
        nameQ = (TextView) abtQ.findViewById(R.id.name);
        nameJ.setText("");
        nameQ.setText("");
        nameJ.setBackgroundResource(R.drawable.jntu);
        nameQ.setBackgroundResource(R.drawable.quest);
        abtJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, JAbtActivity.class));
            }
        });
        abtQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, QAbtActivity.class));
            }
        });
    }
}
