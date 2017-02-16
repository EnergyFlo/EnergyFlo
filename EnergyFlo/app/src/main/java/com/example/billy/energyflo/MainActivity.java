package com.example.billy.energyflo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView drawingImageView;
    DBHandler mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //contactdb db = new contactdb(this);

        mDbHelper = new DBHandler(this.getApplicationContext());


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
        Log log = new Log(12,0,1,1);
        try{
            mDbHelper.addHour(log);
        }
        catch(Exception e){
            Toast.makeText(this,"error",Toast.LENGTH_LONG);
        }



    }



}
