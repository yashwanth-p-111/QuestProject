package com.deviloper.yashwanth.projectnothing.EventCode;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yashwanth on 17-Jan-17.
 */

public class MyApplication extends Application {
    public static SharedPreferences preferences;
    public static String URL = "https://rawgit.com/yashwanth-p-111/20c763a2898483ace2af0b853d9c2010/raw/events_template";
    public static String WSUrl = "https://rawgit.com/yashwanth-p-111/184636ffd5ea344901720d06b0d09637/raw/workshop_template";
    public static String VERURL = "https://rawgit.com/yashwanth-p-111/a4069b533b250de6355c0e8a651240a3/raw/test.json";
    private static MyApplication myApplication;

    public static Context getAppContext() {
        return myApplication.getApplicationContext();
    }

    //30e216ea395018344fa24808c92e667637032a53
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        preferences = myApplication.getApplicationContext().getSharedPreferences("myprefs", MODE_PRIVATE);
    }


}

