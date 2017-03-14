package com.deviloper.yashwanth.projectnothing.Home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.deviloper.yashwanth.projectnothing.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Yashwanth on 02-Mar-17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    PendingIntent pendingIntent;
    Notification notification;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.d("tag","notification received");
        Intent home = new Intent(this, HomeActivity.class);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        //Map<String,String> data=remoteMessage.getData();
        // String title=data.get("title");
        //String body=data.get("body");
        pendingIntent = PendingIntent.getActivity(this, 0, home, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = notBuilder.setSmallIcon(R.drawable.logo).setTicker("Q").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("Welcome Message")
                    .setStyle(new android.support.v7.app.NotificationCompat.BigTextStyle().bigText(body))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo))
                    .setContentText(body).build();
        } else {
            notification = notBuilder.setSmallIcon(R.drawable.logos)
                    .setAutoCancel(true)
                    .setContentTitle("Welcome Message")
                    .setStyle(new android.support.v7.app.NotificationCompat.BigTextStyle().bigText(body))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(body).build();

        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notBuilder.build());

    }

}
