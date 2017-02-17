package com.example.billy.energyflo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Billy on 2/15/17.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "energyflowdb";
    //tables
    private static final String TABLE_LOG = "Log";
//    private static final String TABLE_MON = "Monday";
//    private static final String TABLE_TUE = "Tuesday";
//    private static final String TABLE_WED = "Wednesday";
//    private static final String TABLE_THU = "Thursday";
//    private static final String TABLE_FRI = "Friday";
//    private static final String TABLE_SAT = "Saturday";
//    private static final String TABLE_SUN = "Sunday";
    // Shops Table Columns names
    private static final String KEY_HR = "Hour";
    private static final String KEY_AVG = "Average";
    private static final String KEY_NUM_OF_RATINGS = "Number_Of_Ratings";
    private static final String KEY_TOTAL = "Total";
    private static final String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
            + KEY_HR + " INTEGER PRIMARY KEY," + KEY_AVG + " DOUBLE,"
            + KEY_NUM_OF_RATINGS + " INTEGER," + KEY_TOTAL + " INTEGER)";
    //DBHandler mDbHelper = new DBHandler(getContext());



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    private static DBHandler mInstance = null;

    public static DBHandler getInstance(Context activityContext) {

        // Get the application context from the activityContext to prevent leak
        if (mInstance == null) {
            mInstance = new DBHandler (activityContext.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
// Creating tables again
        onCreate(db);
    }

    // Adding new hour
    public void addHour(Log log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HR, log.getHour());
        values.put(KEY_AVG, log.getAverage());
        values.put(KEY_NUM_OF_RATINGS, log.getNumber_of_ratings());
        values.put(KEY_TOTAL,log.getTotal());

// Inserting Row
        try{

            long insert = db.insert(TABLE_LOG, null, values);
            android.util.Log.v("!!!!!!!!!!!", "insert = "+insert);
        }
        catch(Exception e){
            android.util.Log.v("Entry Exists", "This is good");
        }

        db.close(); // Closing database connection

    }
    // Getting a row
    public Log getLog(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOG, new String[] { KEY_HR,
                        KEY_AVG, KEY_NUM_OF_RATINGS,KEY_TOTAL}, KEY_HR + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Log current = new Log(Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
// return shop
        return current;
    }
    //update database
    public void updateHour(Log log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HR, log.getHour());
        values.put(KEY_AVG, log.getAverage());
        values.put(KEY_NUM_OF_RATINGS, log.getNumber_of_ratings());
        values.put(KEY_TOTAL,log.getTotal());

// Inserting Row
        try{

            long insert = db.update(TABLE_LOG, values, KEY_HR + " = ?",
            new String[]{String.valueOf(log.getHour())});
            android.util.Log.v("Update", "hour updated = "+ log.getHour());
        }
        catch(Exception e){
            android.util.Log.v("Entry Exists", "This is good");
        }

        db.close(); // Closing database connection

    }



}
