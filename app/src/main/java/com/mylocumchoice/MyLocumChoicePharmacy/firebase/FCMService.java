package com.mylocumchoice.MyLocumChoicePharmacy.firebase;

/**
 * Created by POPLIFY on 5/16/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


public class FCMService extends NotificationListenerService {

    Context context;
    String pack="",ticker="",title="",text="";

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {


        pack = sbn.getPackageName();
        try {
            ticker = sbn.getNotification().tickerText.toString();
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }
        Bundle extras = sbn.getNotification().extras;

        try {
            title = extras.getString("android.title");
        }catch (NullPointerException ne){ne.printStackTrace();}

        try {
            text = extras.getCharSequence("android.text").toString();
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }
}
