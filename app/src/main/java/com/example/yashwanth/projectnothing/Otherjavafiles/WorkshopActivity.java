package com.example.yashwanth.projectnothing.Otherjavafiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yashwanth.projectnothing.EventCode.MyApplication;
import com.example.yashwanth.projectnothing.EventCode.VolleySingleton;
import com.example.yashwanth.projectnothing.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WorkshopActivity extends AppCompatActivity {
    Toolbar toolbar;
   /* RoundedBitmapDrawable drawable;
    int[] drId ={R.drawable.mobile,R.drawable.aipic2,R.drawable.virtualreality};*/
    View w1, w2, w3, sub1,sub2, sub3;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    Button r1,r2,r3,e1,e2,e3;
    Button o1,o2,o3;
    RelativeLayout con1,con2,con3;
    TextView[] na = new TextView[4];
    TextView[] no = new TextView[4];
    String url;
    ArrayList<WorkshopData> dataSet;
    ExpandableRelativeLayout expandableRelativeLayout, expandableRelativeLayout2, expandableRelativeLayout3;
    TextView  wname1, details2, out2, high2, date2;
    TextView  wname2, details3, out3, high3, date3;
    TextView  wname0, details1, out1, high1, date1;
    TextView wh,wv;
    Typeface custom_font;
    ProgressDialog progressDialog;
    int prevVer;
    boolean xp1=false;
    boolean xp2=false;
    boolean xp3=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.scale_to_corner, R.anim.scale_from_corner);
        setContentView(R.layout.activity_workshop);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setBackgroundColor(Color.parseColor("#FF0A7033"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF15602A"));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        w1 = findViewById(R.id.w1);
        w2 = findViewById(R.id.w2);
        w3 = findViewById(R.id.w3);
        sub1=findViewById(R.id.sub1);
        sub2 = findViewById(R.id.sub2);
        sub3 = findViewById(R.id.sub3);
        r1=(Button) findViewById(R.id.reg1);
        r2=(Button) findViewById(R.id.reg2);
        r3=(Button) findViewById(R.id.reg3);
        e1=(Button) findViewById(R.id.e1);
        e2=(Button) findViewById(R.id.e2);
        e3=(Button) findViewById(R.id.e3);
        prevVer=getPrevVerson();
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/exo.otf");
        ((TextView) findViewById(R.id.name)).setTypeface(custom_font);
        getViewReferences();
        getExpandedrefs();
        MyTask task = new MyTask(this);
        task.execute();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {
        Context context;

        MyTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            startExecutionToGetArrayListData();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "Please Wait...", "Updating Workshops", false, true);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private void startExecutionToGetArrayListData() {
        if(isConnected())
        {

            volleySingleton=VolleySingleton.getInstance();
            requestQueue=volleySingleton.getRequestQueue();
            imageLoader=volleySingleton.getImageLoader();
            url=MyApplication.WSUrl;
            sendJsonRequest();
        }
        else
        {
            volleySingleton=VolleySingleton.getInstance();
            imageLoader=volleySingleton.getImageLoader();
            dataSet= getCurrentStoredDataset();
            if(dataSet==null)
            {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Connect To Internet For Updates", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
            else
            {
                setValues(dataSet);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }

    }

    private void sendJsonRequest() {
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dataSet = parseJsonResponse(response);
                Toast.makeText(WorkshopActivity.this, "size "+dataSet.size(), Toast.LENGTH_SHORT).show();
                if(dataSet.size()!=0)
                setValues(dataSet);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorkshopActivity.this, "OOps! it seems an error has occured", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<WorkshopData> parseJsonResponse(JSONObject response) {
        ArrayList<WorkshopData> currentDataSet = new ArrayList<>();

        if(response!=null&& response.length()!=0)
        {
            if(response.has("workshops"))
            {
                try
                {
                    int ver=response.getInt("version");
                    storeVer(ver);
                    Toast.makeText(this, ""+ver+" "+prevVer, Toast.LENGTH_SHORT).show();
                    if(ver>prevVer) {
                        JSONArray workshopArray = response.getJSONArray("workshops");
                        for (int i = 0; i < workshopArray.length(); i++) {
                            JSONObject wshop = workshopArray.getJSONObject(i);
                           // String e_color = event.getString(color);
                           // int e_number = event.getInt(number);
                            String w_name = wshop.getString("name");
                            String outline = wshop.getString("outline");
                            String date = wshop.getString("date");
                            String imgPath=wshop.getString("imgpath");
                            String link = wshop.getString("link");
                            String olLink=wshop.getString("paymentLink");
                            Toast.makeText(this, ""+olLink, Toast.LENGTH_SHORT).show();
                            JSONArray outcome_array = wshop.getJSONArray("outcomes");
                            StringBuilder builder = new StringBuilder();
                            for (int l = 0; l < outcome_array.length(); l++) {
                                builder.append("* " + outcome_array.getString(l) + "\n");
                            }
                            String outcome = builder.toString();

                            JSONArray highlight_array = wshop.getJSONArray("highlights");
                            StringBuilder h_builder = new StringBuilder();
                            for (int l = 0; l < highlight_array.length(); l++) {
                                h_builder.append("* " + highlight_array.getString(l) + "\n");
                            }
                            String highlights = h_builder.toString();
                            JSONArray contact1Array = wshop.getJSONArray("contact1");
                            JSONArray contact2Array = wshop.getJSONArray("contact2");
                            JSONArray contact3Array = wshop.getJSONArray("contact3");
                            JSONArray contact4Array = wshop.getJSONArray("contact4");
                            String[] cnames=new String[4];
                            String[] cnos=new String[4];
                            cnames[0]=contact1Array.getString(0);
                            cnos[0]=contact1Array.getString(1);
                            cnames[1]=contact2Array.getString(0);
                            cnos[1]=contact2Array.getString(1);
                            cnames[2]=contact3Array.getString(0);
                            cnos[2]=contact3Array.getString(1);
                            cnames[3]=contact4Array.getString(0);
                            cnos[3]=contact4Array.getString(1);

                            WorkshopData workshopData= new WorkshopData();
                            workshopData.setName(w_name);
                            workshopData.setOutline(outline);
                            workshopData.setHighlights(highlights);
                            workshopData.setOutcomes(outcome);
                            workshopData.setDate(date);
                            workshopData.setOlLink(olLink);
                            workshopData.setImgpath(imgPath);
                            workshopData.setLink(link);
                            workshopData.setCnames(cnames);
                            workshopData.setCnos(cnos);
                            currentDataSet.add(workshopData);

                        }
                        store(currentDataSet);
                    }
                    else
                        return getCurrentStoredDataset();
                }
                catch (Exception e)
                {
                    Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return  currentDataSet;
    }
    void storeVer(int ver)
    {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        editor.putInt("wversion",ver);
        editor.commit();

    }
    public  ArrayList<WorkshopData> getCurrentStoredDataset()
    {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson= new Gson();
        String json=sp.getString("workshops",null);
        Type type = new TypeToken<ArrayList<WorkshopData>>(){}.getType();
        if(json!=null)
        dataSet= gson.fromJson(json,type);
        else
        dataSet=null;
        return dataSet;
    }

    public int getPrevVerson() {
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        int prevVersion=sp.getInt("wversion",0);
        return prevVersion;
    }
    private void store(ArrayList<WorkshopData> currentDataSet) {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        Gson gson = new Gson();
        String json= gson.toJson(currentDataSet);
        editor.putString("workshops",json);
        editor.commit();
    }



    //check for connection
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeState = cm.getActiveNetworkInfo();
        return activeState != null && activeState.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getViewReferences() {
        wname0 = (TextView) w1.findViewById(R.id.name);
        wname0.setBackgroundResource(R.drawable.w1img);
        wname0.setTypeface(custom_font);
        wname0.setTextColor(Color.WHITE);
        wname1 = (TextView) w2.findViewById(R.id.name);
        wname1.setTypeface(custom_font);
        wname1.setTextColor(Color.WHITE);
        wname2 = (TextView) w3.findViewById(R.id.name);
        wname2.setTypeface(custom_font);
    }

    private void getExpandedrefs() {


        details1 = (TextView) sub1.findViewById(R.id.expdetails);
        out1 = (TextView) sub1.findViewById(R.id.expoutcomes);
        high1 = (TextView) sub1.findViewById(R.id.exphighlights);
        date1 = (TextView) sub1.findViewById(R.id.date);
        details2 = (TextView) sub2.findViewById(R.id.expdetails);
        out2 = (TextView) sub2.findViewById(R.id.expoutcomes);
        high2 = (TextView) sub2.findViewById(R.id.exphighlights);
        date2 = (TextView) sub2.findViewById(R.id.date);
        details3 = (TextView) sub3.findViewById(R.id.expdetails);
        out3 = (TextView) sub3.findViewById(R.id.expoutcomes);
        high3 = (TextView) sub3.findViewById(R.id.exphighlights);
        date3 = (TextView) sub3.findViewById(R.id.date);
        o1=(Button)sub1.findViewById(R.id.payOnline);
        o2=(Button)sub2.findViewById(R.id.payOnline);
        o3=(Button)sub3.findViewById(R.id.payOnline);

        setFonts();
    }

    private void setFonts() {
        Typeface textFont = Typeface.createFromAsset(getAssets(),  "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(),  "fonts/hmed.ttf");
        ((TextView)findViewById(R.id.expdetails)).setTypeface(textFont);
        ((TextView)findViewById(R.id.expoutcomes)).setTypeface(textFont);
        ((TextView)findViewById(R.id.exphighlights)).setTypeface(textFont);
        wh=(TextView)findViewById(R.id.workHead);
        wh.setTypeface(headFont);
        wv=(TextView)findViewById(R.id.workView);
        wv.setTypeface(textFont);
        details2.setTypeface(textFont);
        out2.setTypeface(textFont);
        high2.setTypeface(textFont);
        details3.setTypeface(textFont);
        out3.setTypeface(textFont);
        high3.setTypeface(textFont);
    }

    private void setValues(ArrayList<WorkshopData> dataSet) {

        WorkshopData wdata1=dataSet.get(0);
        final WorkshopData wdata2=dataSet.get(1);
        final WorkshopData wdata3=dataSet.get(2);
        con1=(RelativeLayout) sub1.findViewById(R.id.con);
        con2=(RelativeLayout) sub2.findViewById(R.id.con);
        con3=(RelativeLayout) sub3.findViewById(R.id.con);
        int[] idna={R.id.name1,R.id.name2,R.id.name3,R.id.name4};
        int[] idno={R.id.no1,R.id.no2,R.id.no3,R.id.no4};
        String[] cnames1=wdata1.getCnames();
        String[] cnos1=wdata1.getCnos();
        for(int i=0;i<4;i++)
        {
            na[i]=(TextView) con1.findViewById(idna[i]);
            no[i]=(TextView) con1.findViewById(idno[i]);
            na[i].setText(cnames1[i]);
            no[i].setText(cnos1[i]);
        }
        String[] cnames2=wdata2.getCnames();
        String[] cnos2=wdata2.getCnos();
        for(int i=0;i<4;i++)
        {
            na[i]=(TextView) con2.findViewById(idna[i]);
            no[i]=(TextView) con2.findViewById(idno[i]);
            na[i].setText(cnames2[i]);
            no[i].setText(cnos2[i]);
        }
        String[] cnames3=wdata3.getCnames();
        String[] cnos3=wdata3.getCnos();
        for(int i=0;i<4;i++)
        {
            na[i]=(TextView) con3.findViewById(idna[i]);
            no[i]=(TextView) con3.findViewById(idno[i]);
            na[i].setText(cnames3[i]);
            no[i].setText(cnos3[i]);
        }

        wname0.setText(wdata1.getName());
        wname1.setTextColor(Color.BLACK);
        wname1.setText(wdata2.getName());
        wname2.setText(wdata3.getName());
        details1.setText(wdata1.getOutline());
        out1.setText(wdata1.getOutcomes());
        date1.setText(wdata1.getDate());
        high1.setText(wdata1.getHighlights());
        details2.setText(wdata2.getOutline());
        out2.setText(wdata2.getOutcomes());
        date2.setText(wdata2.getDate());
        high2.setText(wdata2.getHighlights());
        String path=wdata2.getImgpath();
        if(path!=null&&!path.equals(""))
        {
            imageLoader.get(path, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    //img2.setImageBitmap(response.getBitmap());
                    wname1.setBackground(new BitmapDrawable(getResources(),response.getBitmap()));
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        details3.setText(wdata3.getOutline());
        out3.setText(wdata3.getOutcomes());
        date3.setText(wdata3.getDate());
        high3.setText(wdata3.getHighlights());
        String path2=wdata3.getImgpath();
        if(path2!=null&&!path2.equals(""))
        {
            imageLoader.get(path, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    wname2.setBackground(new BitmapDrawable(getResources(),response.getBitmap()));
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        final String link1=wdata1.getLink();
        final String link2=wdata2.getLink();
       final  String link3=wdata3.getLink();

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                intent.putExtra("link",link1);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!link2.equals(""))
                    {
                        Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                        intent.putExtra("link",link2);
                        startActivity(intent);
                    }
                else{
                        Toast.makeText(WorkshopActivity.this, "Not opened Yet", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!link3.equals(""))
                {
                    Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                    intent.putExtra("link",link3);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(WorkshopActivity.this, "Not opened Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableRelativeLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
                expandableRelativeLayout.toggle();
                if(!xp1)
                {
                    Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                    e1.setText("LESS ▲");
                    xp1=true;
                }
                else{
                    xp1=false;
                    e1.setText("MORE ▼");
                }


            }
        });
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wdata2.getName().equals("")){
                    Toast.makeText(WorkshopActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
                }
                else{
                    expandableRelativeLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
                    expandableRelativeLayout2.toggle();
                    if(xp2==false)
                    {
                        Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                        e2.setText("LESS ▲");
                        xp2=true;
                    }
                    else{
                        xp2=false;
                        e2.setText("MORE ▼");
                    }

                }


            }
        });
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wdata3.getName().equals("")){
                    Toast.makeText(WorkshopActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    expandableRelativeLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
                    expandableRelativeLayout3.toggle();
                    if(xp3==false)
                    {
                        Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                        e3.setText("LESS ▲");
                        xp3=true;
                    }
                    else{
                        xp3=false;
                        e3.setText("MORE ▼");
                    }

                }

            }
        });
        final String olLink1=wdata1.getOlLink();
        final String olLink2=wdata2.getOlLink();
        final  String olLink3=wdata3.getOlLink();
       o1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               Toast.makeText(WorkshopActivity.this, ""+olLink1, Toast.LENGTH_SHORT).show();
               if(!olLink1.equals(""))
               {
                   Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                   intent.putExtra("link",olLink1);
                   startActivity(intent);
               }
               else
                   Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
           }
       });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!olLink2.equals(""))
                {
                    Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                    intent.putExtra("link",olLink2);
                    startActivity(intent);
                }
                else
                    Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
            }
        });
        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!olLink3.equals(""))
                {
                    Intent intent=new Intent(WorkshopActivity.this,WebViewActivity.class);
                    intent.putExtra("link",olLink3);
                    startActivity(intent);
                }
                else
                    Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
            }
        });
    }



       /* Resources res= getResources();
        for(int i=0;i<3;i++)
        {
            Bitmap src= BitmapFactory.decodeResource(res,drId[i]);
            drawable= RoundedBitmapDrawableFactory.create(res,src);
            drawable.setCornerRadius(30.0f);
            img[i].setImageDrawable(drawable);
        }*/



}
