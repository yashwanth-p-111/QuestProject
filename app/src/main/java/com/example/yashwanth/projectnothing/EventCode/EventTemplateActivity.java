package com.example.yashwanth.projectnothing.EventCode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.yashwanth.projectnothing.R;
import com.google.gson.Gson;

public class EventTemplateActivity extends AppCompatActivity {
EventData eventItem;
    TextView eventName,overviewDesc,eveLink,outcomes,overview,rHeader,oHeader;
    Button regLink;
    String path;
    TextView[] rh=new TextView[3];
    TextView[] rd=new TextView[3];
    TextView[] na=new TextView[4];
    TextView[] no=new TextView[4];
    TextView addText;
    RelativeLayout[] r=new RelativeLayout[3];
    int[] idRefs={R.id.r1,R.id.r2,R.id.r3};
    Gson gson;
Toolbar toolbar;
    TextView image;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.scale_to_corner,R.anim.);
        setContentView(R.layout.activity_event_template_);
        volleySingleton=VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();
        toolbar=(Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent i=getIntent();
        gson=new Gson();
        eventItem=gson.fromJson(i.getStringExtra("eventItem"),EventData.class);
        getReferences();
        setValues();
        toolbar.setBackgroundColor(Color.parseColor(eventItem.getColor()));
        toolbar.setTitle("Event #"+eventItem.getNumber());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(eventItem.getColor()));
        }
    }

    private void setValues() {
        String[] roundHead ={eventItem.getRound1().heading,eventItem.getRound2().heading,eventItem.getRound3().heading};
        String[] roundDesc ={eventItem.getRound1().description,eventItem.getRound2().description,eventItem.getRound3().description};
        eventName.setText(eventItem.getName());
        Typeface textFont = Typeface.createFromAsset(getAssets(),  "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(),  "fonts/hmed.ttf");
        eventName.setTypeface(headFont);
        overviewDesc.setText(eventItem.getOverview());
        overviewDesc.setTypeface(textFont);
        overview.setTypeface(headFont);
        for(int i=0;i<3;i++)
        {
             rh[i].setText(roundHead[i]);
            rh[i].setTypeface(headFont);
            rd[i].setText(roundDesc[i]);
            rd[i].setTypeface(textFont);
        }
        eveLink.setText(eventItem.getEventlink());
        //regLink.setText(eventItem.getRegisterlink());
        outcomes.setText(eventItem.getOutcome());
        outcomes.setTypeface(textFont);
        rHeader.setTypeface(headFont);
        rHeader.setText(eventItem.getRoundHeader());
        oHeader.setTypeface(headFont);
        String addData=eventItem.getExtraData();
        if(addData.trim().equals("•"))
        {
            addText.setTypeface(textFont);
            addText.setText(addData);
        }
        else
        {
            addText.setVisibility(View.GONE);
        }

        Linkify.addLinks(addText,Linkify.ALL);
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(eventItem.getRegisterlink());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        path=eventItem.getImgurl();
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
        }

    }

    private void getReferences() {
        int[] idna={R.id.name1,R.id.name2,R.id.name3,R.id.name4};
        int[] idno={R.id.no1,R.id.no2,R.id.no3,R.id.no4};
        RelativeLayout contacts=(RelativeLayout) findViewById(R.id.contacts);
        for(int i=0;i<3;i++)
        {
            r[i]= (RelativeLayout) findViewById(idRefs[i]);
            rh[i]=(TextView) r[i].findViewById(R.id.roundHeader);
            rd[i]=(TextView) r[i].findViewById(R.id.roundDescription);
            Linkify.addLinks(rd[i],Linkify.ALL);

        }
        eventName=(TextView) findViewById(R.id.eventName);
        image=(TextView) findViewById(R.id.eventImage);
        overview= (TextView) findViewById(R.id.overview);
        overviewDesc= (TextView) findViewById(R.id.overviewDescription);
        eveLink=(TextView) findViewById(R.id.eventLink);
        regLink=(Button) findViewById(R.id.registerLink);
        rHeader=(TextView)findViewById(R.id.rHeader);
        outcomes=(TextView) findViewById(R.id.outcomes);
        oHeader=(TextView) findViewById(R.id.outcomeHeader);
        addText=(TextView) findViewById(R.id.addText);
        String[] cnames={eventItem.getContact1().getContact_name(),eventItem.getContact2().getContact_name(),eventItem.getContact3().getContact_name(),eventItem.getContact4().getContact_name()};
        String[] cnos={eventItem.getContact1().getContact_no(),eventItem.getContact2().getContact_no(),eventItem.getContact3().getContact_no(),eventItem.getContact4().getContact_no()};

        for(int i=0;i<4;i++)
        {
             na[i]=(TextView) contacts.findViewById(idna[i]);
             no[i]=(TextView) contacts.findViewById(idno[i]);
             na[i].setText(cnames[i]);
            no[i].setText(cnos[i]);
        }
    }
}
