package com.example.billy.energyflo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextClock;

public class MainActivity extends AppCompatActivity {

//    ImageView drawingImageView;
    DBHandler mDbHelper;
    TextClock time;
    SeekBar ratingSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //contactdb db = new contactdb(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mDbHelper = new DBHandler(this.getApplicationContext());
        time = (TextClock) findViewById(R.id.textClock);
        ratingSelector = (SeekBar) findViewById(R.id.seekBar);

//        drawingImageView = (ImageView) this.findViewById(R.id.DrawingImageView);
//        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
//                .getDefaultDisplay().getWidth(), (int) getWindowManager()
//                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawingImageView.setImageBitmap(bitmap);

        // Circle

//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        float x = 100;
//        float y = 100;
//        float radius = 100;
//        canvas.drawCircle(x, y, radius, paint);

    }

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
            android.util.Log.d("Success", "updated hour " + current_log.getHour() +"Average = "+ current_log.getAverage() + "Number of ratings = "+current_log.getNumber_of_ratings() +"Total = " + current_log.getTotal());

        }
        catch(Exception e){
            android.util.Log.d("Exception", "Creating new EnergyLog for hour: " + number_time);
            Log log = new Log(number_time,number_rating,1,number_rating);
            mDbHelper.addHour(log);
            return;
        }

        //android.util.Log.d("BAD", "Added value but didn't return");

    }
}
