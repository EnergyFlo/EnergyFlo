package com.example.billy.energyflo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        SharedPreferences prefs = getSharedPreferences("com.example.billy.energyflo", MODE_PRIVATE);
        boolean logReminderSwitchState = prefs.getBoolean("logReminderSwitch", false);
        boolean peakReminderSwitchState = prefs.getBoolean("peakReminderSwitch", false);

        reminderNotificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        reminderNotificationSwitch.setChecked(logReminderSwitchState);
        peakNotificationSwitch = (Switch) findViewById(R.id.peakNotificationSwitch);
        peakNotificationSwitch.setChecked(peakReminderSwitchState);

        reminderNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /* Save state of switch when flipped */
                SharedPreferences.Editor editor = getSharedPreferences("com.example.billy.energyflo", MODE_PRIVATE).edit();
                editor.putBoolean("logReminderSwitch", reminderNotificationSwitch.isChecked());
                editor.commit();

                if (isChecked) {
                    scheduleAlarm();
                }
                else {
                    // cancel alarm
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
                    //scheduleAlarm();
                }
                else {
                    // cancel alarm
                }
            }
        });



    }

    public void scheduleAlarm()
    {
        DBHandler h = new DBHandler(this);
        h.findPeakHour();
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        //setting alarmTime for 2 seconds in future (but it actually waits 4 seconds)
//        Long alarmTime = new GregorianCalendar().getTimeInMillis()+2000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        android.util.Log.d("alarm", String.valueOf(calendar.getTimeInMillis()));

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReceiver in the Intent, the onReceive() method of this class will execute when
        // alarm triggers and
        //we will write the code to create notification inside onReceive() method pf Alarmreceiver class
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time (alarmTime)
//        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//        Toast.makeText(this, "Reminder Scheduled", Toast.LENGTH_LONG).show();


// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
        //sets alarm to particular hour defined in military time by calender.set() and repeats daily
        //TODO make loop to set multiple alarms
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
