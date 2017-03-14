package com.deviloper.yashwanth.projectnothing.EventCode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deviloper.yashwanth.projectnothing.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.cname;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.cno;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.color;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.contacts;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.eventlink;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.events;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.imgurl;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.name;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.number;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.outcome;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.overview;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.registerlink;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.round1;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.round2;
import static com.deviloper.yashwanth.projectnothing.EventCode.Keys.EventKeys.round3;


public class EventActivity extends AppCompatActivity implements InterfaceForListener {

    int i = 1;
    VolleySingleton volleySingleton;
    RecyclerView recyclerView;
    EventAdapter adapter;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    TextView errmsg;
    ProgressDialog progressDialog;
    String url;
    int[] drawable = {R.drawable.event_1_hbc, R.drawable.event_tech, R.drawable.event_ideate, R.drawable.event_mockinterview, R.drawable.event_stdio, R.drawable.event_crypticenigma, R.drawable.event_datatrix, R.drawable.event_posterparade, R.drawable.event_montage, R.drawable.event_inverso, R.drawable.event_projecto, R.drawable.event_simplyweb, R.drawable.unavailable, R.drawable.unavailable, R.drawable.unavailable, R.drawable.unavailable, R.drawable.unavailable, R.drawable.unavailable, R.drawable.unavailable};
    Contacts[] c = new Contacts[4];
    ArrayList<EventData> dataSet;
    Toolbar toolbar;
    int prevVer;
    String path = "";
    int sendVal;
    int sendCheck, flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_from_corner, R.anim.scale_to_corner);
        setContentView(R.layout.activity_event);
        //flag=getIntent().getIntExtra("flag",1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new EventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setBackgroundColor(Color.parseColor("#af0606"));
        errmsg = (TextView) findViewById(R.id.errView);
        errmsg.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#d01716"));
        }
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (Exception e) {
        }

        //redundant
        prevVer = getPrevVerson();

        //sendVal = getStoredSend();
        //first time
        /*if(sendVal==-1)
        {
            sendCheck=1;
            Toast.makeText(this, "init"+sendVal, Toast.LENGTH_SHORT).show();
        }

        //get already stored val
        else
        {
            sendCheck=sendVal;
            Toast.makeText(this, "exists "+sendCheck, Toast.LENGTH_SHORT).show();
        }*/

        //startExecutionToGetArrayListData();
        MyTask task = new MyTask(this);
        // task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        task.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    //&&flag==1
    private void startExecutionToGetArrayListData() {

        try {
            if (isConnected()) {
                volleySingleton = VolleySingleton.getInstance();
                requestQueue = volleySingleton.getRequestQueue();
                imageLoader = volleySingleton.getImageLoader();
                url = MyApplication.URL;
                sendJsonRequest();

            } else {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                Gson gson = new Gson();
                String json = sp.getString("events", null);
                Type type = new TypeToken<ArrayList<EventData>>() {
                }.getType();
                dataSet = gson.fromJson(json, type);

                if (json != null) {
                    adapter.setEventList(dataSet);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                } else {
                    if (errmsg.getVisibility() == View.GONE) {
                        errmsg.setVisibility(View.VISIBLE);

                    }
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Good Internet Connection Required", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("GO BACK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).setActionTextColor(Color.RED).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();

                    }
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Problem Occured TryAgain", Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dataSet = parseJsonResponse(response);
                if (dataSet != null) {
                    adapter.setEventList(dataSet);
                    progressDialog.dismiss();
                } else {
                    if (errmsg.getVisibility() == View.GONE) {
                        errmsg.setVisibility(View.VISIBLE);

                    }
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Good Internet Connection Required", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("GO BACK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).setActionTextColor(Color.RED).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(EventActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                dataSet = getCurrentStoredDataset();
                if (dataSet != null) {
                    adapter.setEventList(dataSet);
                    progressDialog.dismiss();
                } else {
                    if (errmsg.getVisibility() == View.GONE) {
                        errmsg.setVisibility(View.VISIBLE);

                    }
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Good Connection Required", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Connect and Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).setActionTextColor(Color.RED).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<EventData> parseJsonResponse(JSONObject response) {

        ArrayList<EventData> currentDataSet = new ArrayList<>();

        if (response != null && response.length() != 0) {
            if (response.has(events)) {
                try {
                    int ver = response.getInt("version");
                    storeVer(ver);
                    // Toast.makeText(EventActivity.this, "" + ver + " " + prevVer, Toast.LENGTH_SHORT).show();
                    if (ver > prevVer) {
                        JSONArray eventArray = response.getJSONArray(events);
                        for (int i = 0; i < eventArray.length(); i++) {
                            JSONObject event = eventArray.getJSONObject(i);
                            String e_color = event.getString(color);
                            int e_number = event.getInt(number);
                            String e_name = event.getString(name);
                            String e_imgurl = event.getString(imgurl);
                            final String imgName = event.getString("imgname");
                            String e_overview = event.getString(overview);
                            e_overview.trim().replaceAll("\\n", "\n");
                            String e_rHeader = event.getString("roundhead");
                            JSONArray e_round1 = event.getJSONArray(round1);
                            Rounds r1 = new Rounds(e_round1.getString(0), e_round1.getString(1).trim().replaceAll("\n", "\n"));
                            JSONArray e_round2 = event.getJSONArray(round2);
                            Rounds r2 = new Rounds(e_round2.getString(0), e_round2.getString(1).trim().replaceAll("\n", "\n"));
                            JSONArray e_round3 = event.getJSONArray(round3);
                            Rounds r3 = new Rounds(e_round3.getString(0), e_round3.getString(1).trim().replaceAll("\n", "\n"));
                            String e_reglink = event.getString(registerlink);
                            String e_link = event.getString(eventlink);
                            String shortDesc = event.getString("desc");
                            shortDesc.trim().replaceAll("\\n", "\n");
                            String regFee = event.getString("rfee");
                            String contMail = event.getString("contmail");
                            JSONArray extraDataArr = event.getJSONArray("extras");
                            StringBuilder data1 = new StringBuilder();
                            int count = extraDataArr.length();
                            for (int l = 0; l < extraDataArr.length(); l++) {
                                data1.append("• " + extraDataArr.getString(l));
                                if (l != (count - 1))
                                    data1.append("\n");
                            }
                            String extraData = data1.toString().trim().replaceAll("\\n", "\n");
                            JSONArray e_outcome_array = event.getJSONArray(outcome);
                            StringBuilder builder = new StringBuilder();
                            for (int l = 0; l < e_outcome_array.length(); l++) {
                                builder.append("* " + e_outcome_array.getString(l) + "\n");
                            }
                            String e_outcome = builder.toString().trim().replaceAll("\\n", "\n");
                            JSONArray e_contactArray = event.getJSONArray(contacts);

                            for (int j = 0; j < e_contactArray.length(); j++) {
                                JSONObject e_contact_obj = e_contactArray.getJSONObject(j);
                                String cn = e_contact_obj.getString(cname[j]);
                                String cnum = e_contact_obj.getString(cno[j]);
                                c[j] = new Contacts();
                                c[j].setContact_name(cn);
                                c[j].setContact_no(cnum);
                            }
                            final EventData eventData = new EventData();
                            eventData.setColor(e_color);
                            eventData.setNumber(e_number);
                            eventData.setName(e_name);
                            //eventData.setImgurl(e_imgurl);
                            eventData.setRoundHeader(e_rHeader);
                            eventData.setOverview(e_overview);
                            eventData.setRegisterlink(e_reglink);
                            eventData.setEventlink(e_link);
                            eventData.setImgName(imgName);
                            eventData.setDescription(shortDesc);
                            eventData.setRegFee(regFee);
                            eventData.setOutcome(e_outcome);
                            eventData.setRound1(r1);
                            eventData.setRound3(r3);
                            eventData.setRound2(r2);
                            eventData.setContMail(contMail);
                            eventData.setContact1(c[0]);
                            eventData.setContact2(c[1]);
                            eventData.setContact3(c[2]);
                            eventData.setContact4(c[3]);
                            eventData.setExtraData(extraData);
                            if (e_imgurl != null) {
                                if (e_imgurl.equals("")) {
                                    eventData.setImgurl("");
                                } else {
                                    imageLoader.get(e_imgurl, new ImageLoader.ImageListener() {
                                        @Override
                                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                            Bitmap bitmap = response.getBitmap();
                                            path = saveToInternal(bitmap, imgName);
                                            eventData.setImgurl(path);
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            eventData.setImgurl("");
                                        }
                                    });
                                }
                            }

                            currentDataSet.add(eventData);

                        }
                        store(currentDataSet);
                    }
                    return getCurrentStoredDataset();
                } catch (Exception e) {
                    Toast.makeText(this, "Error Updating data" + e.toString(), Toast.LENGTH_SHORT).show();
                    return getCurrentStoredDataset();
                }
            }
        }
        return currentDataSet;
    }

    private String saveToInternal(Bitmap bitmap, String imgname) {
        ContextWrapper cw = new ContextWrapper(MyApplication.getAppContext());
        File directory = cw.getDir("imgDir", Context.MODE_PRIVATE);
        File myPath = new File(directory, imgname);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
        return directory.getAbsolutePath();
    }

    void storeVer(int ver) {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        editor.putInt("version", ver);
        editor.apply();

    }

    public ArrayList<EventData> getCurrentStoredDataset() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sp.getString("events", null);
        Type type = new TypeToken<ArrayList<EventData>>() {
        }.getType();
        dataSet = gson.fromJson(json, type);
        return dataSet;
    }

    public int getPrevVerson() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int prevVersion = sp.getInt("version", 0);
        return prevVersion;
    }

    private void store(ArrayList<EventData> currentDataSet) {
        SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencces.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentDataSet);
        editor.putString("events", json);
        editor.apply();
    }

    //check for connection
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeState = cm.getActiveNetworkInfo();
        return activeState != null && activeState.isConnected();
    }

    @Override
    public void userItemClick(int pos) {
        EventData data = dataSet.get(pos);
        Gson gson = new Gson();
        String dtStr = gson.toJson(data);
        Intent intent = new Intent(this, EventTemplateActivity.class);
        intent.putExtra("eventItem", dtStr);
        intent.putExtra("drawable", drawable[pos]);
        startActivity(intent);

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
            progressDialog = ProgressDialog.show(context, "Please Wait...", "Downloading Events", false, true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
