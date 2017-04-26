package com.example.billy.energyflo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.statsToolbar);
        setSupportActionBar(myToolbar);

        mDbHelper = new DBHandler(this.getApplicationContext());

        android.util.Log.d("statsActivity", "Begin Logging from Stats Activity");
        /*
        *
        * Graph from:  https://github.com/PhilJay/MPAndroidChart
        *
        */

        //Initialize a chart

        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setBackgroundColor(Color.rgb(51, 51, 51));
        Description des= chart.getDescription();
        des.setEnabled(false);
        Legend leg=chart.getLegend();
        leg.setEnabled(true);
        chart.setDrawGridBackground(false); //no grid line on background

        //format X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(20f);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisMinValue(0);
        xAxis.setAxisMaximum(24);

        //format Y-axis
        YAxis yAxis=chart.getAxisLeft();
        yAxis.setTextSize(20f);
        yAxis.setTextColor(Color.rgb(255, 255, 255));
        yAxis.setAxisMinValue(0);
        yAxis.setAxisMaximum(11);
        chart.getAxisRight().setEnabled(false);

        //Initialize a List of Entries to store all the data points
        List<Entry> averageHourRating = new ArrayList<>();
        //Loop through the database and add average rating on appropriate data points on line graph, in case the hour has not been recorded, rating is 0
        //All the entries are stored in
        for (hour=1; hour<=24;hour++){
            try{
                thisHour = mDbHelper.getLog(hour);
                android.util.Log.d("StatsActivity","Hour looking at: " + thisHour.getHour() +" ,The AVERAGE IS: "+thisHour.getAverage());
                Entry entry = new Entry(thisHour.getHour(),(float)thisHour.getAverage());
                averageHourRating.add(entry);
            }catch(Exception e){
                Entry entry = new Entry(hour,0f);
                averageHourRating.add(entry);
            }
        }

        //Initialize the line with data by passing a list of data needed to be displayed
        LineDataSet setHour= new LineDataSet(averageHourRating, " ");
        //Formatting its elements (text, dot, line, color)
        setHour.setDrawCircles(false);
        setHour.setLineWidth(3f);
        setHour.setColors(Color.rgb(102, 255, 255));
        setHour.setAxisDependency(YAxis.AxisDependency.LEFT);
        setHour.setMode(LineDataSet.Mode.CUBIC_BEZIER); //instead of straight lines, draw line in cubic form
        setHour.setCircleColor(Color.rgb(255, 255, 255));
        setHour.setCircleRadius(4f);
        setHour.setDrawCircleHole(false);
        setHour.setValueTextSize(20f);
        setHour.setValueTextColor(Color.rgb(102, 255, 255));
        setHour.setDrawValues(false);

        //Parse the data in appropriate data set for the Line chart class
        List<ILineDataSet> dataSets= new ArrayList<>();
        dataSets.add(setHour);

        //Initialize the line chart class
        LineData data= new LineData(dataSets);
        chart.setData(data);
        chart.invalidate(); //refreshing the chart every time the chart is brought up
    }
}
