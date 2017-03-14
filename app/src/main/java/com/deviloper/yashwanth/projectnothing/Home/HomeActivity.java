package com.deviloper.yashwanth.projectnothing.Home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.deviloper.yashwanth.projectnothing.About.JAbtActivity;
import com.deviloper.yashwanth.projectnothing.About.QAbtActivity;
import com.deviloper.yashwanth.projectnothing.EventCode.EventActivity;
import com.deviloper.yashwanth.projectnothing.EventCode.VolleySingleton;
import com.deviloper.yashwanth.projectnothing.Otherjavafiles.WorkshopActivity;
import com.deviloper.yashwanth.projectnothing.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.analytics.FirebaseAnalytics;


public class HomeActivity extends AppCompatActivity {
    ScrollView scrollView;
    RelativeLayout abtJ, abtQ;
    TextView nameJ;
    TextView nameQ;
    TextView wsite;
    TextView wsiteHeader, mainText;
    TextView eH;
    TextView wH;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    RelativeLayout homeEvent;
    RelativeLayout homeWork;
    TextView title, tag, line, date, venue;

    Toolbar toolbar;
    FloatingActionButton fab1, fab2, fab3;
    FloatingActionMenu fab;
    String FACEBOOK_URL = "https://www.facebook.com/questJNTUH";
    String FACEBOOK_PAGE_ID = "questJNTUH";
    Button butt;
    ExpandableRelativeLayout exp;
    TextView hwrk, hevt;
    Notification notification;
    FirebaseAnalytics analytics;
    int taps = 0;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "App Installs");
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        int time = sp.getInt("time", 1);
        if (time == 1) {
            Intent home = new Intent(this, HomeActivity.class);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, home, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);
           /* notBuilder.setContentTitle("Welcome Message")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                    .setContentText("")
                    .setSmallIcon(R.drawable.logo);
            notBuilder.setContentIntent(pendingIntent);*/
            String message = "Hello, with this app in your hands you will be updated with our event time to time and notified with interesting facts everyday";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notification = notBuilder.setSmallIcon(R.drawable.logo).setTicker("Q").setWhen(0)
                        .setAutoCancel(true)
                        .setContentTitle("Quest 2017")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo))
                        .setContentText(message).build();
            } else {

                notification = notBuilder.setSmallIcon(R.drawable.logos)
                        .setAutoCancel(true)
                        .setContentTitle("Welcome Message")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentText(message).build();
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
            editor.putInt("time", 2);
            editor.apply();
        }

        mainText = (TextView) findViewById(R.id.mainText);
        eH = (TextView) findViewById(R.id.eh);
        wH = (TextView) findViewById(R.id.wh);
        homeEvent = (RelativeLayout) findViewById(R.id.homeEvent);
        homeWork = (RelativeLayout) findViewById(R.id.homeWork);
        hevt = (TextView) homeEvent.findViewById(R.id.name);
        hwrk = (TextView) homeWork.findViewById(R.id.name);
        hevt.setBackgroundResource(R.drawable.hev);
        hwrk.setBackgroundResource(R.drawable.hw);
        homeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(HomeActivity.this, EventActivity.class);
                        startActivity(i);
                    }
                }, 150);
            }
        });
        homeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(HomeActivity.this, "Loading...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(HomeActivity.this, WorkshopActivity.class);

                        startActivity(intent);
                    }
                }, 150);

            }
        });
        final NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        butt = (Button) findViewById(R.id.butt);
        Animation blinkanim = AnimationUtils.loadAnimation(this, R.anim.blink);
        butt.setAnimation(blinkanim);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exp = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
                exp.toggle();
            }
        });
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setSmoothScrollingEnabled(true);
        scrollView.setFillViewport(true);
        scrollView.setClipToPadding(true);
        wsite = (TextView) findViewById(R.id.wsite);
        wsiteHeader = (TextView) findViewById(R.id.wsiteheader);
        setFonts();
        fabCode();
        setAbout();
        //setVersion();
    }


    public void resetsetTaps() {
        taps = 0;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }*/
    public void fabCode() {
        Animation blinkanim = AnimationUtils.loadAnimation(this, R.anim.blink);
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setAnimation(blinkanim);
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String fburl = getFacebookPageURL(HomeActivity.this);
                intent.setData(Uri.parse(fburl));
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.e_mail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, "MY QUERY");
                Intent.createChooser(intent, "Query us");
                startActivity(intent);
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
            } else {
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
        Typeface robotM = Typeface.createFromAsset(getAssets(), "fonts/robotom.ttf");
        Typeface montBold = Typeface.createFromAsset(getAssets(), "fonts/montb.ttf");
        mainText.setTypeface(textFont);
        wsiteHeader.setTypeface(headFont);
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(montBold);
        tag = (TextView) findViewById(R.id.tag);
        tag.setTypeface(textFont);
        line = (TextView) findViewById(R.id.line);
        line.setTypeface(robotM);
        date = (TextView) findViewById(R.id.date);
        date.setTypeface(headFont);
        venue = (TextView) findViewById(R.id.by);
        venue.setTypeface(textFont);
        eH.setTypeface(headFont);
        wH.setTypeface(headFont);
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
