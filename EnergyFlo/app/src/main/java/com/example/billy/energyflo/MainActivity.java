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
import android.widget.Toast;

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
        //Calendar rightNow = Calendar.getInstance();
        //int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        CharSequence now = time.getText();
        //TODO raise API to accomodate this function
        //time.getFormat24Hour();
        CharSequence amPm = now.subSequence(6,8);
        CharSequence current_time = now.subSequence(0,2);

        if((amPm.toString().equals("PM") && !current_time.toString().equals("12")) || ((amPm.toString().equals("AM") && current_time.toString().equals("12")))){

            int number = Integer.parseInt(current_time.toString());
            number += 12;
            android.util.Log.v("TRUE", "IT IS NIGHTTIME add 12 = "+number);
        }
//        android.util.Log.v("!!!!!!!!!!!", amPm.toString() + current_time.toString());
//        int number = Integer.parseInt(current_time.toString());
//        number += 12;
//        android.util.Log.v("!!!!!!!!!!!", "IT IS NIGHTTIME add 12 "+number);

        Log log = new Log(12,0,1,1);
        try{
            mDbHelper.addHour(log);
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }
}
