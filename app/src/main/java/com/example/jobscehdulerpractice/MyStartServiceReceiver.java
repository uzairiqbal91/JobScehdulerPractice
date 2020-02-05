package com.example.jobscehdulerpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        new Util().scheduleJob(context);


    }
}
