package com.example.billy.energyflo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity
{
    Switch reminderNotificationSwitch;
    Switch peakNotificationSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /* Retrieve user preferences */
        SharedPreferences prefs = getSharedPreferences("com.example.billy.energyflo", MODE_PRIVATE);
        boolean logReminderSwitchState = prefs.getBoolean("logReminderSwitch", false);
        boolean peakReminderSwitchState = prefs.getBoolean("peakReminderSwitch", false);


        /* Restore user preferences to the switches*/
        reminderNotificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        reminderNotificationSwitch.setChecked(logReminderSwitchState);
        peakNotificationSwitch = (Switch) findViewById(R.id.peakNotificationSwitch);
        peakNotificationSwitch.setChecked(peakReminderSwitchState);


        /* Listen for changes to switch states */
        reminderNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /* Save state of switch when flipped */
                SharedPreferences.Editor editor = getSharedPreferences("com.example.billy.energyflo", MODE_PRIVATE).edit();
                editor.putBoolean("logReminderSwitch", reminderNotificationSwitch.isChecked());
                editor.commit();

                if (isChecked) {
                    scheduleEnergyLoggingAlarm();
                }
                else {
                    cancelEnergyLoggingAlarm();
                }
            }
        });


        peakNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /* Save state of switch when flipped */
                SharedPreferences.Editor editor = getSharedPreferences("com.example.billy.energyflo", MODE_PRIVATE).edit();
                editor.putBoolean("peakReminderSwitch", peakNotificationSwitch.isChecked());
                editor.commit();

                if (isChecked) {
                    schedulePeakAlarm();
                }
                else {
                    cancelPeakAlarm();
                }
            }
        });



    }

    public void scheduleEnergyLoggingAlarm()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("alarmType", "RECORDING_REMINDER");



        /* Schedules hourly alarms between 6am and 10pm */
        for (int reminderHour = 6; reminderHour < 23; reminderHour++) {

            calendar.set(Calendar.HOUR_OF_DAY, reminderHour);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(this,reminderHour,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

            android.util.Log.d("SettingsActivity",
                    "Energy logging reminder #" + reminderHour
                            + " set for: " + calendar.getTimeInMillis());
        }
    }


    public void cancelEnergyLoggingAlarm()
    {
        /* Cancels hourly alarms between 6am and 10pm */
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        for (int reminderHour = 6; reminderHour < 23; reminderHour++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    reminderHour,
                    intentAlarm,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }


        Log.d("SettingsActivity", "Cancelled energy logging alarms");
    }




    public void schedulePeakAlarm() {
        DBHandler dbHandler = new DBHandler(this);
        int peakHour = dbHandler.findPeakHour();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.setTimeInMillis(System.currentTimeMillis() + 1801000L);  // for immediate testing of notification
        calendar.set(Calendar.HOUR_OF_DAY, peakHour);
        android.util.Log.d("Settings Activity", "set alarm for peak hour: " + String.valueOf(calendar.getTimeInMillis()));

        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("alarmType", "PEAK_REMINDER");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // subtract 1800000 ms in order to make alarm 30 minutes before peak hour
        //TODO add a scheduled daily task to update alarm when peak hour changes
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 1800000L,
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(this, 2, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }


    public void cancelPeakAlarm()
    {
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);

        Log.d("SettingsActivity", "Cancelled peak energy alarm");
    }

}