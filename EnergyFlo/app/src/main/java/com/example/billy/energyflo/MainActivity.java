package com.example.billy.energyflo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView drawingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        float x = drawingImageView.getX() + drawingImageView.getWidth()  / 2;
        float y = drawingImageView.getY() + drawingImageView.getHeight() / 2;
        float radius = 100;
        canvas.drawCircle(x, y, radius, paint);
    }


}
