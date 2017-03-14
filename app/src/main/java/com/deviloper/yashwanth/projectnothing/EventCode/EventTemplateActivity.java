package com.deviloper.yashwanth.projectnothing.EventCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.deviloper.yashwanth.projectnothing.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;

public class EventTemplateActivity extends AppCompatActivity {
    EventData eventItem;
    TextView eventName, overviewDesc, eveLink, outcomes, overview, rHeader, oHeader;
    Button regLink;
    TextView contMail, regFee;
    TextView[] rh = new TextView[3];
    TextView[] rd = new TextView[3];
    TextView[] na = new TextView[4];
    TextView[] no = new TextView[4];
    TextView addText;
    RelativeLayout[] r = new RelativeLayout[3];
    int[] idRefs = {R.id.r1, R.id.r2, R.id.r3};
    Gson gson;
    int drawId;
    Toolbar toolbar;
    ImageView image;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;
    int def = R.drawable.unavailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.scale_to_corner,R.anim.);
        setContentView(R.layout.activity_event_template_);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawId = def;
        Intent i = getIntent();
        drawId = i.getIntExtra("drawable", def);
        gson = new Gson();
        eventItem = gson.fromJson(i.getStringExtra("eventItem"), EventData.class);
        getReferences();
        setValues();
        toolbar.setBackgroundColor(Color.parseColor(eventItem.getColor()));
        toolbar.setTitle("Event #" + eventItem.getNumber());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(eventItem.getColor()));
        }
    }

    private void setValues() {
        String[] roundHead = {eventItem.getRound1().heading, eventItem.getRound2().heading, eventItem.getRound3().heading};
        String[] roundDesc = {eventItem.getRound1().description, eventItem.getRound2().description, eventItem.getRound3().description};
        eventName.setText(eventItem.getName());
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        eventName.setTypeface(headFont);
        overviewDesc.setText(eventItem.getOverview());
        overviewDesc.setTypeface(textFont);
        overview.setTypeface(headFont);
        for (int i = 0; i < 3; i++) {
            rh[i].setText(roundHead[i]);
            rh[i].setTypeface(headFont);
            rd[i].setText(roundDesc[i]);
            rd[i].setTypeface(textFont);
            // Linkify.addLinks(rd[i],Linkify.ALL);
        }
        setTitle(eventItem.getName());
        eveLink.setText(eventItem.getEventlink());
        //regLink.setText(eventItem.getRegisterlink());
        outcomes.setText(eventItem.getOutcome());
        outcomes.setTypeface(textFont);
        rHeader.setTypeface(headFont);
        regFee.setText(eventItem.getRegFee());
        contMail.setText(eventItem.getContMail());
        rHeader.setText(eventItem.getRoundHeader());
        oHeader.setTypeface(headFont);
        String addData = eventItem.getExtraData();
        if (!addData.trim().equals("•")) {
            addText.setTypeface(textFont);
            addText.setText(addData);
            addText.setVisibility(View.VISIBLE);
        } else {
            addText.setVisibility(View.GONE);
        }

        Linkify.addLinks(addText, Linkify.ALL);
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rl = eventItem.getRegisterlink();
                if (!rl.equals("")) {
                    Uri uri = Uri.parse(rl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(EventTemplateActivity.this, "No registration required", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*path=eventItem.getImgurl();
        if(path!=null)
        {
            imageLoader.get(path, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    image.setBackground(new BitmapDrawable(getResources(),response.getBitmap()));
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }*/
        try {
            String url = eventItem.getImgurl();
            if (url.equals("")) {
                image.setImageResource(drawId);
            } else {
                File f = new File(url, eventItem.getImgName());
                // Toast.makeText(this, ""+eventItem.getImgurl(), Toast.LENGTH_SHORT).show();
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                image.setImageBitmap(b);
            }

        } catch (Exception e) {
            image.setImageResource(R.drawable.unavailable);
        }
    }

    private void getReferences() {
        int[] idna = {R.id.name1, R.id.name2, R.id.name3, R.id.name4};
        int[] idno = {R.id.no1, R.id.no2, R.id.no3, R.id.no4};
        RelativeLayout contacts = (RelativeLayout) findViewById(R.id.contacts);
        for (int i = 0; i < 3; i++) {
            r[i] = (RelativeLayout) findViewById(idRefs[i]);
            rh[i] = (TextView) r[i].findViewById(R.id.roundHeader);
            rd[i] = (TextView) r[i].findViewById(R.id.roundDescription);
        }
        contMail = (TextView) findViewById(R.id.contMail);
        eventName = (TextView) findViewById(R.id.eventName);
        image = (ImageView) findViewById(R.id.eventImage);
        overview = (TextView) findViewById(R.id.overview);
        overviewDesc = (TextView) findViewById(R.id.overviewDescription);
        eveLink = (TextView) findViewById(R.id.eventLink);
        regLink = (Button) findViewById(R.id.registerLink);
        rHeader = (TextView) findViewById(R.id.rHeader);
        outcomes = (TextView) findViewById(R.id.outcomes);
        oHeader = (TextView) findViewById(R.id.outcomeHeader);
        addText = (TextView) findViewById(R.id.addText);
        regFee = (TextView) findViewById(R.id.regfee);
        String[] cnames = {eventItem.getContact1().getContact_name(), eventItem.getContact2().getContact_name(), eventItem.getContact3().getContact_name(), eventItem.getContact4().getContact_name()};
        String[] cnos = {eventItem.getContact1().getContact_no(), eventItem.getContact2().getContact_no(), eventItem.getContact3().getContact_no(), eventItem.getContact4().getContact_no()};

        for (int i = 0; i < 4; i++) {
            na[i] = (TextView) contacts.findViewById(idna[i]);
            no[i] = (TextView) contacts.findViewById(idno[i]);
            if ((!cnames[i].equals("")) && (!cnos[i].equals(""))) {
                na[i].setText(cnames[i]);
                no[i].setText(cnos[i]);
            } else {
                na[i].setVisibility(View.GONE);
                no[i].setVisibility(View.GONE);
            }

        }
    }
}
