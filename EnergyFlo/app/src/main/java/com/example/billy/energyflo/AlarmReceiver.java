package com.example.billy.energyflo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        android.util.Log.d("AlarmReceiver", "received alarm broadcast");

        String alarmType = intent.getStringExtra("alarmType");
        Log.d("AlarmReceiver", "alarmType: " + alarmType);

        if (alarmType.equals("RECORDING_REMINDER")) {

            // here you can start an activity or service depending on your need

            Notification.Builder myBuilder = new Notification.Builder(context)
                    .setContentTitle("Reminder")
                    .setContentText("Time to record your energy level!")
                    .setSmallIcon(R.mipmap.ic_launcher_textver)
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
