package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dailySteps.db";
    public static final String TABLE_NAME = "dailySteps";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "stepsnumber";
    public static final String COL_4 = "date";


    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dailySteps (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, stepsnumber INTEGER, Date TEXT)");
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addSteps(String username, Integer stepsnumber, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("stepsnumber",stepsnumber);
        contentValues.put("date",date);
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteRecord(int recordId){
        final String SQL_DELETE = "delete from " + TABLE_NAME +
                " where _id=?";
        String valuesArray[] = {recordId+""};

        SQLiteDatabase writeDB = getWritableDatabase();
        writeDB.execSQL(SQL_DELETE, valuesArray);
        writeDB.close();
    }
}
