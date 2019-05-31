package com.lucacandela.mycontactapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ContactScreenActivity  extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp","MainActivity: 1/2 Setting up the layout...");

        setContentView(R.layout.activity_contactscreen);

        myDb = new DatabaseHelper(this);

    }
}
