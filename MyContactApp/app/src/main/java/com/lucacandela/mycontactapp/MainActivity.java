package com.lucacandela.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp","MainActivity: Setting up the layout");
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: Instantiated DatabaseHelper");
    }
}
