package com.example.billy.energyflo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;

public class MainActivity extends AppCompatActivity {

    ImageView drawingImageView;
    DBHandler mDbHelper;
    TextClock time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //contactdb db = new contactdb(this);

        mDbHelper = new DBHandler(this.getApplicationContext());
        time = (TextClock) findViewById(R.id.textClock);


        drawingImageView = (ImageView) this.findViewById(R.id.DrawingImageView);
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        // Circle

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        float x = 100;
        float y = 100;
        float radius = 100;
        canvas.drawCircle(x, y, radius, paint);

    }

    public void addItem(View view){
        //TODO query db, check if value exits, if yes update, if no create
        CharSequence now = time.getText();
        //TODO raise API to accomodate this function
        //time.getFormat24Hour();
        CharSequence amPm = now.subSequence(6,8);
        CharSequence current_time = now.subSequence(0,2);
        int number_time = Integer.parseInt(current_time.toString());
        int number_rating = 5;

        if((amPm.toString().equals("PM") && !current_time.toString().equals("12")) || ((amPm.toString().equals("AM") && current_time.toString().equals("12")))){

            //number = Integer.parseInt(current_time.toString());
            number_time += 12;
            //android.util.Log.v("TRUE", "IT IS NIGHTTIME add 12 = "+number_time);
        }


        try{
            Log current_log = mDbHelper.getLog(number_time);
            //TODO edit log to add new params and update
            current_log.updateLog(number_rating);
            mDbHelper.updateHour(current_log);
            android.util.Log.v("Success", "updated hour " + current_log.getHour() +"Average = "+ current_log.getAverage() +
                    "Number of ratings = "+current_log.getNumber_of_ratings() +"Total = " + current_log.getTotal());

        }
        catch(Exception e){
            android.util.Log.v("Exception", "Adding value " + number_time);
            Log log = new Log(number_time,number_rating,1,number_rating);
            mDbHelper.addHour(log);
            return;
        }
        //android.util.Log.v("BAD", "Added value but didn't return");

    }
}
