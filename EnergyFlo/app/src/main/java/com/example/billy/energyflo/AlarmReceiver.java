package com.example.billy.energyflo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        android.util.Log.d("AlarmReceiver", "received alarm broadcast");

        String alarmType = intent.getStringExtra("alarmType");

        if (alarmType.equals("RECORDING_REMINDER")) {

            Notification.Builder myBuilder = new Notification.Builder(context)
                    .setContentTitle("Time to log your energy level")
                    .setContentText("Tap this notification")
                    .setSmallIcon(R.mipmap.ic_launcher_textver)
                    .setAutoCancel(true);

            Intent myIntent = new Intent(context, MainActivity.class);

            PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0 , myIntent, 0);
            myBuilder.setContentIntent(myPendingIntent);

            Notification myNotification = myBuilder.build();
            NotificationManager myNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            myNotificationManager.notify(1234, myNotification);

        }


        if (alarmType.equals("PEAK_REMINDER")) {
            Notification.Builder myBuilder = new Notification.Builder(context)
                    .setContentTitle("Peak energy in 30 minutes")
                    .setContentText("Plan to work on something important")
                    .setSmallIcon(R.mipmap.ic_launcher_textver)
                    .setAutoCancel(true);

            Intent myIntent = new Intent(context, MainActivity.class);

            PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0 , myIntent, 0);
            myBuilder.setContentIntent(myPendingIntent);

            Notification myNotification = myBuilder.build();
            NotificationManager myNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            myNotificationManager.notify(4567, myNotification);
        }


    }
}
