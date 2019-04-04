package com.mylocumchoice.MyLocumChoicePharmacy.firebase;

import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by POPLIFY on 3/4/2017.
 */

 public class MyReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(android.content.Context context, Intent intent) {
        Log.e("-----","got it");
    }
}
