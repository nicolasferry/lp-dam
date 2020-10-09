package com.example.lpdam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MyLocationListener implements LocationListener {

    private Context context;

    public MyLocationListener(Context context){
        Log.v("LOCATION", "Listener initiated");
        this.context=context;
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        Log.v("LOCATION", "Longitude: "+longitude);
        double latitude = location.getLatitude();
        Log.v("LOCATION", "Latitude "+latitude);

        long t = System.currentTimeMillis();

        SQLiteDatabase database = SQLDBHelper.getInstance(this.context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLDBHelper.TIMESTAMP, t+"");
        values.put(SQLDBHelper.LONGITUDE, longitude+"");
        values.put(SQLDBHelper.LATITUDE, latitude+"");
        long newRowId = database.insert(SQLDBHelper.TABLE_NAME, null, values);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
