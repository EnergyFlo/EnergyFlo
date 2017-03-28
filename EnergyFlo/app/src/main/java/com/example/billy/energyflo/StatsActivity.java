package com.example.billy.energyflo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.Random;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    DBHandler mDbHelper;
    int hour;
    Log thisHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mDbHelper = new DBHandler(this.getApplicationContext());

        android.util.Log.d("statsActivity", "Begin Logging from Stats Activity");


//        Log thisHour = mDbHelper.getLog(15);
//        android.util.Log.d("statsActivity", "Average for hour: " + thisHour.getHour() + " is rating: " + thisHour.getAverage());

        for (hour = 0; hour<24; hour++) {
            try {
                thisHour = mDbHelper.getLog(hour);
                android.util.Log.d("statsActivity", "Average for hour " + thisHour.getHour() + " is rating " + thisHour.getAverage());

            }
            catch(Exception e){
                android.util.Log.d("statsActivity", e.toString());
            }
        }

        /*
        *
        * Graph from:  https://github.com/PhilJay/MPAndroidChart
        *
        */
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> averageHourRating = new ArrayList<>();
        chart.setBackgroundColor(Color.rgb(51, 51, 51));
        chart.setDrawGridBackground(false); //no grid line on background

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(20f);
        xAxis.setTextColor(Color.rgb(0, 170, 255));
        xAxis.setAxisMinValue(0);
        xAxis.setAxisMaximum(24);

        YAxis yAxis=chart.getAxisLeft();
        yAxis.setTextSize(20f);
        yAxis.setTextColor(Color.rgb(0, 170, 255));
        yAxis.setAxisMinValue(0);
        yAxis.setAxisMaximum(21);
        chart.getAxisRight().setEnabled(false);
        /*
        for (hour=0; hour<24;hour++){
            thisHour = mDbHelper.getLog(hour);
            android.util.Log.d("StatsActivity","Hour looking at: " + thisHour.getHour() +" ,The AVERAGE IS: "+thisHour.getAverage());
            Entry entry = new Entry(thisHour.getHour(),(float)thisHour.getAverage());
            averageHourRating.add(entry);
        }*/

        //Dummy Hours and Average Rating entry points
        for (int i = 0; i<24; i++){
            Random  random = new Random();
            float averageRating= random.nextInt(20);
            Entry ratingEntry= new Entry((float)i,averageRating);
            averageHourRating.add(ratingEntry);
        }

        LineDataSet setHour= new LineDataSet(averageHourRating, " Average Hour");
        setHour.setDrawCircles(true);
        setHour.setLineWidth(4f);
        setHour.setColors(Color.rgb(255, 102, 0));
        setHour.setAxisDependency(YAxis.AxisDependency.LEFT);
        setHour.setMode(LineDataSet.Mode.CUBIC_BEZIER); //instead of straight lines, draw line in cubic form
        setHour.setCircleColor(Color.rgb(0, 170, 255));
        setHour.setCircleRadius(5f);
        setHour.setDrawCircleHole(false);
        setHour.setValueTextSize(20f);
        setHour.setValueTextColor(Color.rgb(255, 102, 0));

        List<ILineDataSet> dataSets= new ArrayList<ILineDataSet>();
        dataSets.add(setHour);

        LineData data= new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }
}
