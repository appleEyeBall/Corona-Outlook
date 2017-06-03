package com.tisecodes.corona.outlook.BroadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tisecodes.corona.outlook.Activities.MainActivity;

/**
 * Created by Oluwatise on 5/30/2017.
 */

public class MyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);
    }
}
