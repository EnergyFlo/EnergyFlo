/*
* GROUP 16
* WILLIAM PIERCE    2643343
* NAM DO            2594704
* DILLON PURVIS     2532954
* */

package com.example.billy.energyflo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DBHandler mDbHelper;
    TextClock time;
    SeekBar ratingSelector;
    TextView ratingTextView;
    Animation out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ratingTextView = (TextView) findViewById(R.id.ratingText);
        mDbHelper = new DBHandler(this.getApplicationContext());
        time = (TextClock) findViewById(R.id.textClock);
        ratingSelector = (SeekBar) findViewById(R.id.seekBar);
        ratingSelector.setOnSeekBarChangeListener(yourListener);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        getSupportActionBar().setHomeButtonEnabled(true);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(3000);
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ratingTextView.setText("");
                //mSwitcher.startAnimation(in);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private SeekBar.OnSeekBarChangeListener yourListener = new  SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // Log the progress
            android.util.Log.d("DEBUG", "Progress is: "+progress);
            //set ratingTextView's text
            ratingTextView.setText(""+progress);
            ratingTextView.startAnimation(out);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    };



    public void addItem(View view){

        //TODO give user feedback that their button press was recorded

        /*Determine Current Time and Parse*/

        CharSequence currentTimeWholeString = time.getText();
        android.util.Log.d("addItem", "currentTimeWholeString:  " + time.getText());


        CharSequence amPm = currentTimeWholeString.subSequence(6,8);
        CharSequence currentTime = currentTimeWholeString.subSequence(0,2);
        int number_time = Integer.parseInt(currentTime.toString());

        android.util.Log.d("addItem", "amPm:  " + amPm);
        android.util.Log.d("addItem", "currentTime:  " + currentTime);
        android.util.Log.d("addItem", "number_time:  " + number_time);

        if((amPm.toString().equals("PM") && !currentTime.toString().equals("12")) || ((amPm.toString().equals("AM") && currentTime.toString().equals("12")))){
            // if it is afternoon, or, if it is midnight:  convert to military time equivalent
            number_time += 12;
            //android.util.Log.d("addItem", "IT IS NIGHTTIME add 12 = " + number_time);
        }

        android.util.Log.d("addItem", "number_time (after condition):  " + number_time);

        /*Fetch User Rating*/
        int number_rating = ratingSelector.getProgress();
        android.util.Log.d("addItem", "number_rating:  " + number_rating);




        try{
            // Find EnergyLog for the current hour
            Log current_log = mDbHelper.getLog(number_time);

            //edit log to add new params and update
            current_log.updateLog(number_rating);
            mDbHelper.updateHour(current_log);
            android.util.Log.d("Success", "updated hour " + current_log.getHour() +" Average = "+ current_log.getAverage() + " Number of ratings = "+current_log.getNumber_of_ratings() +" Total = " + current_log.getTotal());

            ratingTextView.setText("✓");
            ratingTextView.startAnimation(out);
        }
        catch(Exception e){
            android.util.Log.d("Exception", "Creating new EnergyLog for hour: " + number_time);
            Log log = new Log(number_time,number_rating,1,number_rating);
            mDbHelper.addHour(log);
            return;
        }

        //android.util.Log.d("BAD", "Added value but didn't return");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.gotoStats){
            Intent intentToViewStats = new Intent(this, StatsActivity.class);
            startActivity(intentToViewStats);
            return true;
        }
        if (item.getItemId() == R.id.gotoSettings) {
            Intent intentToViewSettings = new Intent(this, SettingsActivity.class);
            startActivity(intentToViewSettings);
            return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
