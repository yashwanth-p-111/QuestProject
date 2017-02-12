package com.example.yashwanth.projectnothing.Home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.yashwanth.projectnothing.About.ContactsActivity;
import com.example.yashwanth.projectnothing.EventCode.EventActivity;
import com.example.yashwanth.projectnothing.EventCode.VolleySingleton;
import com.example.yashwanth.projectnothing.Otherjavafiles.WorkshopActivity;
import com.example.yashwanth.projectnothing.R;

import java.util.Locale;

public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {
    ActionBarDrawerToggle mdrawerToggle;
    DrawerLayout mdrawerLayout;
    ListView listView;
VolleySingleton volleySingleton;
    String url;
    int version,flag;
    RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navigation_drawer_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.drawer_list);
        //parseVersion();
        Resources res = getResources();
        String[] items = res.getStringArray(R.array.item_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.parseColor("#000000"));
                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

            mdrawerLayout.closeDrawers();
            mdrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
                }
            }, 200);


        } else if (position == 1) {
            mdrawerLayout.closeDrawers();
            mdrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), WorkshopActivity.class));
                }
            }, 200);


        } else if (position == 2) {
            mdrawerLayout.closeDrawers();
            mdrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(getActivity(), EventActivity.class);
                    i.putExtra("flag",flag);
                    startActivity(i);
                }
            }, 200);


        } else if (position == 3) {
            mdrawerLayout.closeDrawers();
            mdrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), ContactsActivity.class));
                }
            }, 200);


        } else if(position==4) {
            mdrawerLayout.closeDrawers();
            mdrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    double destinationLatitude=17.4931480;
                    double destinationLongitude=78.3923100;
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", destinationLatitude, destinationLongitude, "QUEST 2017");
                    //   Uri location= Uri.parse("http://maps.google.com/maps?daddr=17.4931480,78.3923100");
                    Uri location=Uri.parse(uri);
                    Intent intent=new Intent(Intent.ACTION_VIEW,location);
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    try
                    {
                        startActivity(intent);
                    }
                    catch(ActivityNotFoundException ex)
                    {
                        try
                        {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(unrestrictedIntent);
                        }
                        catch(ActivityNotFoundException innerEx)
                        {
                            Toast.makeText(getActivity(), "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, 200);
        }
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mdrawerLayout = drawerLayout;
        mdrawerToggle = new ActionBarDrawerToggle(getActivity(), mdrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        //use this instead of drawerLayout.setDrawerListener(.);
        drawerLayout.addDrawerListener(mdrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawerToggle.syncState();
            }
        });
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeState = cm.getActiveNetworkInfo();
        return activeState != null && activeState.isConnected();
    }


}
