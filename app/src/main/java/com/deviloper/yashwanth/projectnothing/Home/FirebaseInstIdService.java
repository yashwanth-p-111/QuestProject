package com.deviloper.yashwanth.projectnothing.Home;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Yashwanth on 02-Mar-17.
 */

public class FirebaseInstIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {


        String rec_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("tag", rec_token);
    }
}

