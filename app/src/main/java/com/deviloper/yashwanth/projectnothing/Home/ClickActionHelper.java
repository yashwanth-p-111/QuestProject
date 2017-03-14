package com.deviloper.yashwanth.projectnothing.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Yashwanth on 04-Mar-17.
 */

public class ClickActionHelper {
    static Class cls;

    public static void startActivity(String clsname, Bundle extras, Context context) {

        try {
            cls = Class.forName(clsname);
        } catch (Exception ex) {

        }
        Intent intent = new Intent(context, cls);
        intent.putExtras(extras);
        context.startActivity(intent);
    }
}
