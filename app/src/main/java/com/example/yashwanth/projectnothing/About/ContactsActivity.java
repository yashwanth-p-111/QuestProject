package com.example.yashwanth.projectnothing.About;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yashwanth.projectnothing.R;

public class ContactsActivity extends AppCompatActivity {
    RelativeLayout[] c=new RelativeLayout[6];
    TextView eo,hod,cs,st;
    int[] rid={R.id.c1,R.id.c2,R.id.c3,R.id.c4,R.id.cs1,R.id.cs2};
    String[] name ={"Sai Prasad","Sri Ram","Sahithi M","Sudheer","K P Supreethi ","Kamakshi Prasad"};
    String[] no={"7589585858","8585858585","9669696924","7584524585","7548548512","Msc,Mphil,Mca"};
    String[] mail={"saip123@gmail.com","xxxxxxxxx","xxxxxxxxx","xxxxxxxx","nhuyydvuybdhib","jgvgvcghgyccy"};
    String[] letter={"Sp","Sr","Sa","Su","Su","Ka"};
    TextView[] names=new TextView[6];
    TextView[] nos=new TextView[6];
    TextView[] mails=new TextView[6];
    TextView[] letters=new TextView[6];
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        eo=(TextView) findViewById(R.id.eo);
        hod=(TextView) findViewById(R.id.hod);
        cs=(TextView) findViewById(R.id.cs);
        st=(TextView) findViewById(R.id.st);
        Typeface textFont = Typeface.createFromAsset(getAssets(),  "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(),  "fonts/hmed.ttf");
        Typeface extraFont = Typeface.createFromAsset(getAssets(),  "fonts/exo.otf");

        eo.setTypeface(headFont);
        hod.setTypeface(headFont);
        cs.setTypeface(headFont);
        st.setTypeface(headFont);

        for(int i=0;i<6;i++)
        {
            c[i]=(RelativeLayout)findViewById(rid[i]);
            nos[i]=(TextView)c[i]. findViewById(R.id.pNo);
            nos[i].setTypeface(textFont);
            mails[i]=(TextView) c[i].findViewById(R.id.mail);
            mails[i].setTypeface(textFont);
            letters[i]=(TextView) c[i].findViewById(R.id.textLetter);
            letters[i].setTypeface(extraFont);
            names[i]=(TextView) c[i].findViewById(R.id.cName);
            names[i].setTypeface(headFont);
        }
        for(int i=0;i<6;i++)
        {
            names[i].setText(name[i]);
            nos[i].setText(no[i]);
            mails[i].setText(mail[i]);
            mails[i].setTextColor(Color.parseColor("#2d2d2d"));
            letters[i].setText(letter[i]);

        }
       // c[4].setBackgroundColor(Color.parseColor("#aaaaaa"));
        //c[5].setBackgroundColor(Color.parseColor("#aaaaaa"));
        names[5].setTextSize(22f);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
