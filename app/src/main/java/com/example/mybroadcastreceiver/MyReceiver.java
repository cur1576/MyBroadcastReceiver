package com.example.mybroadcastreceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    private static final int ID = 123;
    private final String CHANNEL_ID = "chnnel_1";
    CharSequence name = "MyChannel";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = DateFormat.getDateTimeInstance().format(new Date());
        Log.d("Test", "onReceive: wurde aufgerufen ");
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            channel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationManager.createNotificationChannel(channel);
        }

        switch (intent.getAction()){
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle(context.getString(R.string.app_name))
                        .setContentText(msg + " Flugmodus geändert!")
                        .setWhen(System.currentTimeMillis());

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    builder.setChannelId(CHANNEL_ID);

                }

                Intent myIntent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,ID,myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();


                notificationManager.notify(ID,notification);
        }
    }
}
