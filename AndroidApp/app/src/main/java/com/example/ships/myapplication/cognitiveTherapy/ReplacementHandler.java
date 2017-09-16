package com.example.ships.myapplication.cognitiveTherapy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */

class ReplacementHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "ReplacementData";
    private static final String TABLE_FACTSHEET = "ReplacementSet";

    private static final String KEY_ID = "id";
    private static final String KEY_CONCERNS = "concerns";
    private static final String KEY_POSITIVE = "positive";
    private static final String KEY_NEGATIVE = "negative";

    public ReplacementHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FACTSHEET + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONCERNS + " TEXT,"
                + KEY_POSITIVE + " TEXT," +  KEY_NEGATIVE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTSHEET);

        // Create tables again
        onCreate(db);

    }

    public void addContent(ReplacementContent content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, content.getIndex());
        values.put(KEY_CONCERNS, content.getConcerns());
        values.put(KEY_POSITIVE, content.getPositive());
        values.put(KEY_NEGATIVE, content.getNegative());



        // Inserting Row
        db.insert(TABLE_FACTSHEET, null, values);

        String countQuery = "SELECT * FROM " + TABLE_FACTSHEET;
        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("TAG",Integer.toString(cursor.getCount()));
        db.close(); // Closing database connection
    }


    public ReplacementContent getContent(int index) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FACTSHEET, new String[] { KEY_ID,
                        KEY_CONCERNS, KEY_POSITIVE, KEY_NEGATIVE }, KEY_ID + "=?",
                new String[] { String.valueOf(index) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ReplacementContent content = new ReplacementContent(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        return content;
    }


    public List<ReplacementContent> getAllContents() {
        List<ReplacementContent> contentList = new ArrayList<ReplacementContent>();
        String selectQuery = "SELECT  * FROM " + TABLE_FACTSHEET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReplacementContent content = new ReplacementContent(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3));
                contentList.add(content);
            } while (cursor.moveToNext());
        }
        return contentList;
    }


    public int getContentsCount() {
        String countQuery = "SELECT * FROM " + TABLE_FACTSHEET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

}
