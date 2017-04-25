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
                .setContentTitle("Reminder")
                .setContentText("Time to record your energy level!")
                .setSmallIcon(R.drawable.ic_menu_send)
                .setAutoCancel(true);

        Intent myIntent = new Intent(context, MainActivity.class);

        PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0 , myIntent, 0);
        myBuilder.setContentIntent(myPendingIntent);

        Notification myNotification = myBuilder.build();
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(1234, myNotification);


//        rescheduleAlarm(context);
    }


//    public void rescheduleAlarm(Context context)
//    {
//        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
//        // we fetch  the current time in milliseconds and added 1 day time
//        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
//        Long alarmTime = new GregorianCalendar().getTimeInMillis()+2000;
//
//        // create an Intent and set the class which will execute when Alarm triggers, here we have
//        // given AlarmReceiver in the Intent, the onRecieve() method of this class will execute when
//        // alarm triggers and
//        //we will write the code to create notification inside onReceive() method pf Alarmreceiver class
//        Intent intentAlarm = new Intent(context, AlarmReceiver.class);
//
//        // create the object
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        //set the alarm for particular time
//        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime, PendingIntent.getBroadcast(context,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//        Toast.makeText(context, "Reminder Scheduled", Toast.LENGTH_LONG).show();
//
//    }
}
