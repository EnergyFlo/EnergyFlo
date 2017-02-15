package com.example.billy.energyflo;

import android.content.Context;
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
    private static final String TABLE_MON = "Monday";
    private static final String TABLE_TUE = "Tuesday";
    private static final String TABLE_WED = "Wednesday";
    private static final String TABLE_THU = "Thursday";
    private static final String TABLE_FRI = "Friday";
    private static final String TABLE_SAT = "Saturday";
    private static final String TABLE_SUN = "Sunday";
    // Shops Table Columns names
    private static final String KEY_HR = "Hour";
    private static final String KEY_AVG = "Average";
    private static final String KEY_NUM_OF_RATINGS = "Number_Of_Ratings";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOG + "("
            + KEY_HR + " INTEGER PRIMARY KEY," + KEY_AVG + " FLOAT,"
            + KEY_NUM_OF_RATINGS + " INTEGER" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
