package com.example.billy.energyflo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dillon on 4/25/2017.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub


        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        Notification.Builder myBuilder = new Notification.Builder(context)
                .setContentTitle("EnergyFlo Reminder")
                .setContentText("Record Yoself!")
                .setSmallIcon(R.drawable.ic_menu_send)
                .setAutoCancel(true);

        Intent myIntent = new Intent(context, MainActivity.class);

        PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0 , myIntent, 0);
        myBuilder.setContentIntent(myPendingIntent);

        Notification myNotification = myBuilder.build();
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(1234, myNotification);
    }

}
