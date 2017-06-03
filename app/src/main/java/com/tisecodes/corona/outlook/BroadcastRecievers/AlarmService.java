package com.tisecodes.corona.outlook.BroadcastRecievers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.R;

/**
 * Created by Oluwatise on 5/30/2017.
 */

public class AlarmService extends Service {
    private NotificationManager mManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

//    @Override
//    public int onStartCommand(Intent intent, @IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true) int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mManager = (NotificationManager)this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Good morning!")
                .setContentText("see today's weather")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags|=Notification.FLAG_AUTO_CANCEL;
        mManager.notify(0, notification);
        return super.onStartCommand(intent, flags, startId);

    }
}
