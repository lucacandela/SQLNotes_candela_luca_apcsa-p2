package com.lucacandela.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LC_ContactDB.db";
    public static final String TABLE_NAME = "LC_ContactDB_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_PHONE_NUM = "phoneNumber";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_CONTACT + " TEXT," + COLUMN_NAME_PHONE_NUM +" TEXT)" ;

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
        Log.d("MyContactApp","DatabaseHelper: 2/2 constructed DatabaseHelper!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp","DatabaseHelper: 1/2 creating database...");
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("MyContactApp","DatabaseHelper: 2/2 created database!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyContactApp","DatabaseHelper: 1/2 upgrading database...");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        Log.d("MyContactApp","DatabaseHelper: 2/2 upgraded database!");
    }

    public boolean insertData(String name,String number){
        Log.d("MyContactApp","DatabaseHelper: 1/2 Inserting data...");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT,name);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result != -1){
            Log.d("MyContactApp","DatabaseHelper: Contact insert \"" + name +"\" - PASSED");
            return true;
        }
        else {
            Log.d("MyContactApp", "DatabaseHelper: Contact insert \"" + name + "\" - FAILED");
            return false;
        }
    }
}
