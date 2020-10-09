package com.example.lpdam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


public class SQLDBHelper extends SQLiteOpenHelper {
    private static SQLDBHelper sInstance;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sensors_database";
    public static final String TABLE_NAME = "GPS";
    public static final String ID = "_id";
    public static final String TIMESTAMP = "timestamp";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static synchronized SQLDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLDBHelper(context);
        }
        return sInstance;
    }

    private SQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TIMESTAMP + " INTEGER, " +
                LONGITUDE + " TEXT, " +
                LATITUDE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public JSONArray getAllGPSData(){ // This is stupid, to be improved
        JSONArray locations = new JSONArray();

        String SELECT_QUERY = String.format("SELECT * FROM %s ", TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    JSONObject json = new JSONObject();
                    json.put("timestamp",cursor.getString(cursor.getColumnIndex(TIMESTAMP)));
                    json.put("longitude",cursor.getString(cursor.getColumnIndex(LONGITUDE)));
                    json.put("latitude",cursor.getString(cursor.getColumnIndex(LATITUDE)));
                    locations.put(json);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("LOCATION", "Error while trying to get locations from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return locations;
    }

    public void deleteAllLocations() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("LOCATION", "Error while trying to delete all locations");
        } finally {
            db.endTransaction();
        }
    }
}
