package com.example.yashwanth.projectnothing.EventCode;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yashwanth on 17-Jan-17.
 */

public class MyApplication extends Application
{
    private static MyApplication myApplication;
    public static  String URL="https://rawgit.com/yashwanth-p-111/20c763a2898483ace2af0b853d9c2010/raw/events_template";
    public static String WSUrl="https://rawgit.com/yashwanth-p-111/184636ffd5ea344901720d06b0d09637/raw/workshop_template";

    //30e216ea395018344fa24808c92e667637032a53
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static Context getAppContext()
    {
        return  myApplication.getApplicationContext();
    }


}

