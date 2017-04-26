package com.example.billy.energyflo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    ImageView drawingImageView;
    DBHandler mDbHelper;
    TextClock time;
    SeekBar ratingSelector;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    TextView textView;
    Animation out;


    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //contactdb db = new contactdb(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        textView = (TextView) findViewById(R.id.ratingText);
        mDbHelper = new DBHandler(this.getApplicationContext());
        time = (TextClock) findViewById(R.id.textClock);
        ratingSelector = (SeekBar) findViewById(R.id.seekBar);
        ratingSelector.setOnSeekBarChangeListener(yourListener);
        drawer = (DrawerLayout) findViewById(R.id.nav_view);
        //setting drawer and list for drawer content
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        //        R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle("energyflo");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("energyflo");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(3000);
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText("");
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
            //set textView's text
            textView.setText(""+progress);
            textView.startAnimation(out);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    };


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLayout);
//        menu.findItem(R.id.drawer_layout).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }


    public void addItem(View view){

        /*Determine Current Time and Parse*/

        //TODO query db, check if value exits, if yes update, if no create
        CharSequence currentTimeWholeString = time.getText();
        android.util.Log.d("addItem", "currentTimeWholeString:  " + time.getText());

        //TODO raise API to accommodate this function
        //time.getFormat24Hour();

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

            //TODO edit log to add new params and update
            current_log.updateLog(number_rating);
            mDbHelper.updateHour(current_log);
            android.util.Log.d("Success", "updated hour " + current_log.getHour() +" Average = "+ current_log.getAverage() + " Number of ratings = "+current_log.getNumber_of_ratings() +" Total = " + current_log.getTotal());

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
//        if (item.getItemId() == R.id.drawer_menu) {
//            //mDrawerLayout.openDrawer(Gravity.LEFT);
//            return true;
//
//        }
        if (item.getItemId() == R.id.gotoSettings) {
            Intent intentToViewSettings = new Intent(this, SettingsActivity.class);
            startActivity(intentToViewSettings);
            return true;

        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
