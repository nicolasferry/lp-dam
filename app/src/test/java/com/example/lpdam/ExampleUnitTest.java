package com.example.lpdam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//a library class that enables you to have Android context for your testing.
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class ExampleUnitTest {
    private SQLDBHelper database;

    @Before
    public void onSetup() {
        database = SQLDBHelper.getInstance(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //Be careful with Roboelectric database is reset between each test
    @Test
    public void addInSQL(){
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLDBHelper.TIMESTAMP, "123");
        values.put(SQLDBHelper.LONGITUDE, "43");
        values.put(SQLDBHelper.LATITUDE, "42");
        long newRowId = writableDatabase.insert(SQLDBHelper.TABLE_NAME, null, values);

        assertEquals(newRowId, 1);

        JSONArray allData = database.getAllGPSData();
        assertTrue(allData.length() > 0);
    }

}