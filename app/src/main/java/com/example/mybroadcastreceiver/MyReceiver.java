package com.example.mybroadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.DateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    private static final int ID = 123;
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = DateFormat.getDateTimeInstance().format(new Date());
        switch (intent.getAction()){
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Notification.Builder builder = new Notification.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle(context.getString(R.string.app_name))
                        .setContentText(msg + " Flugmodus geändert!")
                        .setWhen(System.currentTimeMillis());

                Intent myIntent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(ID,notification);
        }
    }
}
