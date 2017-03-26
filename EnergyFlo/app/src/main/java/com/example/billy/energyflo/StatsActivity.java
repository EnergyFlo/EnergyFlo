package com.example.billy.energyflo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
        * Graph from:   http://www.android-graphview.org/simple-graph/
        *
        */
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }
}
