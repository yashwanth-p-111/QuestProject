package com.deviloper.yashwanth.projectnothing.About;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deviloper.yashwanth.projectnothing.R;

public class ContactsActivity extends AppCompatActivity {
    RelativeLayout[] c = new RelativeLayout[7];
    TextView eo, hod, cs, st;
    int[] rid = {R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.cs1, R.id.cs2, R.id.cs3};
    String[] name = {"Sai Prasad", "Sri Ram", "Sahithi M", "Sudheer", "Dr K P Supreethi ", "Dr V.Kamakshi Prasad", "Dr J Ujwala Rekha"};
    String[] nameStr = {"Sp", "Sr", "Sa", "Su", "", "", ""};
    String[] no = {"9030338465", "7660909180", "8978941220", "7036492383", "040-32408718", "", "040-32408718"};
    String[] mail = {"annaldas.sai111@gmail.com", "------------------------------", "mgvsahithi7@gmail.com", "sudheersehwagad@gmail.com", "supreethi.pujari@gmail.com", "", "ujwala_rekha@yahoo.com"};
    TextView[] names = new TextView[7];
    TextView[] nos = new TextView[7];
    TextView[] mails = new TextView[7];
    TextView[] letters = new TextView[7];
    int[] img = {R.drawable.teacher, R.drawable.teacher, R.drawable.teacher};
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_from_corner, R.anim.scale_to_corner);
        setContentView(R.layout.activity_contacts);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        eo = (TextView) findViewById(R.id.eo);
        hod = (TextView) findViewById(R.id.hod);
        cs = (TextView) findViewById(R.id.cs);
        st = (TextView) findViewById(R.id.st);
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        Typeface extraFont = Typeface.createFromAsset(getAssets(), "fonts/exo.otf");

        eo.setTypeface(headFont);
        hod.setTypeface(headFont);
        cs.setTypeface(headFont);
        st.setTypeface(headFont);

        for (int i = 0; i < 7; i++) {
            c[i] = (RelativeLayout) findViewById(rid[i]);
            nos[i] = (TextView) c[i].findViewById(R.id.pNo);
            nos[i].setTypeface(textFont);
            mails[i] = (TextView) c[i].findViewById(R.id.mail);
            mails[i].setTypeface(textFont);
            letters[i] = (TextView) c[i].findViewById(R.id.textLetter);
            letters[i].setTypeface(extraFont);
            names[i] = (TextView) c[i].findViewById(R.id.cName);
            names[i].setTypeface(headFont);
        }
        for (int i = 0; i < 7; i++) {
            names[i].setText(name[i]);
            nos[i].setText(no[i]);
            mails[i].setText(mail[i]);
            mails[i].setTextColor(Color.parseColor("#2d2d2d"));
            letters[i].setText(nameStr[i]);
        }
        letters[4].setBackgroundResource(img[0]);
        letters[5].setBackgroundResource(img[1]);
        letters[6].setBackgroundResource(img[2]);
        // c[4].setBackgroundColor(Color.parseColor("#aaaaaa"));
        //c[5].setBackgroundColor(Color.parseColor("#aaaaaa"));
        names[5].setTextSize(21f);
        names[4].setTextSize(21f);
        names[6].setTextSize(21f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
