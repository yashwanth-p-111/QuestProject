package com.deviloper.yashwanth.projectnothing.Otherjavafiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.deviloper.yashwanth.projectnothing.EventCode.MyApplication;
import com.deviloper.yashwanth.projectnothing.EventCode.VolleySingleton;
import com.deviloper.yashwanth.projectnothing.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.deviloper.yashwanth.projectnothing.R.id.con;

public class WorkshopActivity extends AppCompatActivity {
    Toolbar toolbar;
    /* RoundedBitmapDrawable drawable;
     int[] drId ={R.drawable.mobile,R.drawable.aipic2,R.drawable.virtualreality};*/
    View w1, w2, w3, sub1, sub2, sub3;
    VolleySingleton volleySingleton;

    int[] idna = {R.id.name1, R.id.name2, R.id.name3, R.id.name4};
    int[] idno = {R.id.no1, R.id.no2, R.id.no3, R.id.no4};
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    Button r1, r2, r3, e1, e2, e3;
    Button o1, o2, o3;
    RelativeLayout con1, con2, con3;
    TextView[] na = new TextView[4];
    TextView[] no = new TextView[4];
    TextView rf1, rf2, rf3;
    TextView wmail1, wmail2, wmail3;
    TextView wLink1, wLink2, wLink3;
    String url;
    ArrayList<WorkshopData> dataSet;
    ExpandableRelativeLayout expandableRelativeLayout, expandableRelativeLayout2, expandableRelativeLayout3;
    TextView wname1, details2, out2, high2, date2;
    TextView wname2, details3, out3, high3, date3;
    TextView wname0, details1, out1, high1, date1;
    TextView wh, wv;
    Typeface custom_font;
    int prevVer;
    boolean xp1 = false;
    boolean xp2 = false;
    boolean xp3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_from_corner, R.anim.scale_to_corner);
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
        //prevVer = getPrevVerson();
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/exo.otf");
        ((TextView) findViewById(R.id.name)).setTypeface(custom_font);
        getViewReferences();
        getExpandedrefs();
        setValues();
        //MyTask task = new MyTask(this);
        //task.execute();
    }

    private void startExecutionToGetArrayListData() {
        if (isConnected()) {
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
            imageLoader = volleySingleton.getImageLoader();
            url = MyApplication.WSUrl;
            sendJsonRequest();
        } else {
            volleySingleton = VolleySingleton.getInstance();
            imageLoader = volleySingleton.getImageLoader();
            dataSet = getCurrentStoredDataset();
            if (dataSet == null) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Network required to get updates on Workshop-3", Snackbar.LENGTH_SHORT);
                snackbar.show();


            } else {
                //setValues(dataSet);
            }
        }

    }

    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dataSet = parseJsonResponse(response);
                if (dataSet == null) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Good Connection required to get updates on Workshop-3", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                // else if (dataSet.size() != 0)
                // setValues(dataSet);
               /* if (progressDialog.isShowing())
                    progressDialog.dismiss();*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataSet = getCurrentStoredDataset();
                if (dataSet == null) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Good Connection required to get updates on Workshop-3", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    // setValues(dataSet);
                }

               /* if (progressDialog.isShowing())
                    progressDialog.dismiss();*/
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<WorkshopData> parseJsonResponse(JSONObject response) {
        ArrayList<WorkshopData> currentDataSet = new ArrayList<>();

        if (response != null && response.length() != 0) {
            if (response.has("workshops")) {
                try {
                    int ver = response.getInt("version");
                    // Toast.makeText(this, ver+" "+prevVer, Toast.LENGTH_SHORT).show();
                    storeVer(ver);
                    if (ver > prevVer) {
                        //Toast.makeText(this, "fetching", Toast.LENGTH_SHORT).show();
                        JSONArray workshopArray = response.getJSONArray("workshops");
                        for (int i = 0; i < workshopArray.length(); i++) {
                            JSONObject wshop = workshopArray.getJSONObject(i);
                            // String e_color = event.getString(color);
                            // int e_number = event.getInt(number);
                            String w_name = wshop.getString("name");
                            String outline = wshop.getString("outline");
                            String date = wshop.getString("date");
                            String link = wshop.getString("link");
                            String mail = wshop.getString("mail");
                            String imgpath = wshop.getString("imgpath");
                            String olLink = wshop.getString("paymentLink");
                            String rf = wshop.getString("rf");
                            String wlink = wshop.getString("sitelink");
                            // Toast.makeText(this, "" + olLink, Toast.LENGTH_SHORT).show();
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
                            String[] cnames = new String[4];
                            String[] cnos = new String[4];
                            cnames[0] = contact1Array.getString(0);
                            cnos[0] = contact1Array.getString(1);
                            cnames[1] = contact2Array.getString(0);
                            cnos[1] = contact2Array.getString(1);
                            cnames[2] = contact3Array.getString(0);
                            cnos[2] = contact3Array.getString(1);
                            cnames[3] = contact4Array.getString(0);
                            cnos[3] = contact4Array.getString(1);

                            WorkshopData workshopData = new WorkshopData();
                            workshopData.setName(w_name);
                            workshopData.setOutline(outline);
                            workshopData.setHighlights(highlights);
                            workshopData.setOutcomes(outcome);
                            workshopData.setDate(date);
                            workshopData.setOlLink(olLink);
                            workshopData.setLink(link);
                            workshopData.setCnames(cnames);
                            workshopData.setCnos(cnos);
                            workshopData.setRf(rf);
                            workshopData.setMail(mail);
                            workshopData.setWlink(wlink);
                            workshopData.setImgpath(imgpath);
                            currentDataSet.add(workshopData);

                        }
                        store(currentDataSet);
                    } else
                        return getCurrentStoredDataset();
                } catch (Exception e) {
                    // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    return getCurrentStoredDataset();
                }
            }
        }
        return currentDataSet;
    }

    void storeVer(int ver) {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        editor.putInt("wversion", ver);
        editor.commit();

    }

    public ArrayList<WorkshopData> getCurrentStoredDataset() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sp.getString("workshops", null);
        Type type = new TypeToken<ArrayList<WorkshopData>>() {
        }.getType();
        if (json != null)
            dataSet = gson.fromJson(json, type);
        else
            dataSet = null;
        return dataSet;
    }

    public int getPrevVerson() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int prevVersion = sp.getInt("wversion", 0);
        return prevVersion;
    }

    private void store(ArrayList<WorkshopData> currentDataSet) {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentDataSet);
        editor.putString("workshops", json);
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
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getViewReferences() {
        w1 = findViewById(R.id.w1);
        w2 = findViewById(R.id.w2);
        w3 = findViewById(R.id.w3);
        sub1 = findViewById(R.id.sub1);
        sub2 = findViewById(R.id.sub2);
        sub3 = findViewById(R.id.sub3);
        r1 = (Button) findViewById(R.id.reg1);
        r2 = (Button) findViewById(R.id.reg2);
        r3 = (Button) findViewById(R.id.reg3);
        e1 = (Button) findViewById(R.id.e1);
        e2 = (Button) findViewById(R.id.e2);
        e3 = (Button) findViewById(R.id.e3);
        con1 = (RelativeLayout) sub1.findViewById(con);
        con2 = (RelativeLayout) sub2.findViewById(con);
        con3 = (RelativeLayout) sub3.findViewById(con);
        wname0 = (TextView) w1.findViewById(R.id.name);
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
        o1 = (Button) sub1.findViewById(R.id.payOnline);
        o2 = (Button) sub2.findViewById(R.id.payOnline);
        o3 = (Button) sub3.findViewById(R.id.payOnline);
        rf1 = (TextView) sub1.findViewById(R.id.regfeew);
        rf2 = (TextView) sub2.findViewById(R.id.regfeew);
        rf3 = (TextView) sub3.findViewById(R.id.regfeew);
        wLink1 = (TextView) sub1.findViewById(R.id.workshopLink);
        wLink2 = (TextView) sub2.findViewById(R.id.workshopLink);
        wLink3 = (TextView) sub3.findViewById(R.id.workshopLink);
        setFonts();
    }

    private void setFonts() {
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/robotolt.ttf");
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/hmed.ttf");
        ((TextView) findViewById(R.id.expdetails)).setTypeface(textFont);
        ((TextView) findViewById(R.id.expoutcomes)).setTypeface(textFont);
        ((TextView) findViewById(R.id.exphighlights)).setTypeface(textFont);
        wh = (TextView) findViewById(R.id.workHead);
        wh.setTypeface(headFont);
        wv = (TextView) findViewById(R.id.workView);
        wv.setTypeface(textFont);
        details2.setTypeface(textFont);
        out2.setTypeface(textFont);
        high2.setTypeface(textFont);
        details3.setTypeface(textFont);
        out3.setTypeface(textFont);
        high3.setTypeface(textFont);
    }

    private void setValues() {

        String[] cnames1 = getResources().getStringArray(R.array.w1cnames);
        String[] cnos1 = getResources().getStringArray(R.array.w1cnos);
        String[] cnames3 = getResources().getStringArray(R.array.w3cnames);
        String[] cnos3 = getResources().getStringArray(R.array.w3cnos);
        String[] cnames2 = getResources().getStringArray(R.array.w2cnames);
        String[] cnos2 = getResources().getStringArray(R.array.w2cnos);
        for (int i = 0; i < 4; i++) {
            na[i] = (TextView) con2.findViewById(idna[i]);
            no[i] = (TextView) con2.findViewById(idno[i]);
            na[i].setText(cnames2[i]);
            no[i].setText(cnos2[i]);
        }

        for (int i = 0; i < 4; i++) {
            na[i] = (TextView) con3.findViewById(idna[i]);
            no[i] = (TextView) con3.findViewById(idno[i]);
            na[i].setText(cnames3[i]);
            no[i].setText(cnos3[i]);
        }
        for (int i = 0; i < 4; i++) {
            na[i] = (TextView) con1.findViewById(idna[i]);
            no[i] = (TextView) con1.findViewById(idno[i]);
            na[i].setText(cnames1[i]);
            no[i].setText(cnos1[i]);
        }
        wmail1 = (TextView) con1.findViewById(R.id.contMail);
        wmail1.setText(R.string.w1_mail);
        wmail2 = (TextView) con2.findViewById(R.id.contMail);
        wmail2.setText(R.string.w2_mail);
        wmail3 = (TextView) con3.findViewById(R.id.contMail);
        wname0.setText(R.string.w1name);
        wname0.setBackgroundResource(R.drawable.w1img);
        details1.setText(R.string.w1details);
        rf1.setText("Rs 1500/-");
        wLink1.setText("www.csequest.com/workshops/hackIT/");
        wLink2.setText("www.csequest.com/workshops/webon/");
        out1.setText(R.string.w1learn);
        date1.setText(R.string.w1date);
        high1.setText(R.string.w1highlights);
        wname1.setTextColor(Color.BLACK);
        wname1.setText(R.string.w2name);
        wname1.setBackgroundResource(R.drawable.w2img);
        rf2.setText("Rs 1200/-");
        details2.setText(R.string.w2details);
        out2.setText(R.string.w2learn);
        date2.setText(R.string.w2date);
        high2.setText(R.string.w2highlights);
        wname2.setText(R.string.w3name);
        wname2.setBackgroundResource(R.drawable.w3i);
        wname2.setTextColor(Color.BLACK);
        wmail3.setText(R.string.w3_mail);
        details3.setText(R.string.w3details);
        out3.setText(R.string.w3learn);
        date3.setText(R.string.w3date);
        high3.setText(R.string.w3highlights);
        rf3.setText("Rs 1000/-");
        wLink3.setText("www.csequest.com/workshops/bigdata");
        final String link1 = "https://goo.gl/yGpA0X";
        final String link2 = "https://tinyurl.com/z8ga25s";

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                intent.putExtra("link", link1);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!link2.equals("")) {
                    Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                    intent.putExtra("link", link2);
                    startActivity(intent);
                } else {
                    Toast.makeText(WorkshopActivity.this, "Not opened Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableRelativeLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
                expandableRelativeLayout.toggle();
                if (!xp1) {
                    Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e1.setText("LESS ▲");
                        }
                    }, 600);
                    e1.setAlpha(0.4f);
                    xp1 = true;
                } else {
                    xp1 = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e1.setText("MORE ▼");
                        }
                    }, 600);
                    e1.setAlpha(1f);

                }


            }
        });
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expandableRelativeLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
                expandableRelativeLayout2.toggle();
                if (xp2 == false) {
                    Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e2.setText("LESS ▲");
                        }
                    }, 600);
                    xp2 = true;
                    e2.setAlpha(0.4f);
                } else {
                    xp2 = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e2.setText("MORE ▼");
                        }
                    }, 600);

                    e2.setAlpha(1f);

                }


            }
        });

        final String olLink1 = "http://www.meraevents.com/event/hackIT/118766?ucode=organizer";
        final String olLink2 = "https://localbells.com/story/52402";
        o1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(WorkshopActivity.this, "" + olLink1, Toast.LENGTH_SHORT).show();
                if (!olLink1.equals("")) {
                    Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                    intent.putExtra("link", olLink1);
                    startActivity(intent);
                } else
                    Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
            }
        });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!olLink2.equals("")) {
                    Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                    intent.putExtra("link", olLink2);
                    startActivity(intent);
                } else
                    Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
            }
        });
        final String link3 = getResources().getString(R.string.w3reglink);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!link3.equals("")) {
                    Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                    intent.putExtra("link", link3);
                    startActivity(intent);
                } else {
                    Toast.makeText(WorkshopActivity.this, "Not opened Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expandableRelativeLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
                expandableRelativeLayout3.toggle();
                if (xp3 == false) {
                    Toast.makeText(WorkshopActivity.this, "Swipe Up", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e3.setText("LESS ▲");
                        }
                    }, 600);

                    xp3 = true;
                    e3.setAlpha(0.4f);

                } else {
                    xp3 = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            e3.setText("MORE ▼");
                        }
                    }, 600);
                    e3.setAlpha(1f);

                }

            }


        });
        final String olLink3 = getResources().getString(R.string.w3reglink);
        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!olLink3.equals("")) {
                    Intent intent = new Intent(WorkshopActivity.this, WebViewActivity.class);
                    intent.putExtra("link", olLink3);
                    startActivity(intent);
                } else
                    Toast.makeText(WorkshopActivity.this, "Updated Soon...", Toast.LENGTH_SHORT).show();
            }
        });
        // final WorkshopData wdata3 = dataSet.get(0);


        /*String path2 = wdata3.getImgpath();
        if (path2 != null && !path2.equals("")) {
            imageLoader.get(path2, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    wname2.setBackground(new BitmapDrawable(getResources(), response.getBitmap()));
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
*/


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
            /*
            progressDialog = ProgressDialog.show(context, "Please Wait...", "Updating Workshops", false, true);
            progressDialog.setCanceledOnTouchOutside(false);*/
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
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
