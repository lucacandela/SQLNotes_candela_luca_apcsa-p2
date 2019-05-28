package com.lucacandela.mycontactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.lucacandela.mycontactapp.DatabaseHelper.COLUMN_NAME_CONTACT;
import static com.lucacandela.mycontactapp.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editNum,editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp","MainActivity: 1/2 Setting up the layout...");
        setContentView(R.layout.activity_main);
        Log.d ("MyContactApp","MainAcitivity: 2/2 Layout set up!");

        editName = findViewById(R.id.contactNameInput);
        editNum = findViewById(R.id.phoneNumInput);
        editAddress = findViewById(R.id.addressInput);
        Log.d("MyContactApp","MainActivity: 1/2 Instantiating DatabaseHelper...");
        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: 2/2 Instantiated DatabaseHelper!");
    }

    public void addData(View v){
        String name = editName.getText().toString();
        String phone = editNum.getText().toString();
        String address = editAddress.getText().toString();
        boolean isInserted = myDb.insertData(name,phone);

        if (isInserted){
            Toast.makeText(MainActivity.this, "Success - contact inserted",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Failure - contact not inserted",Toast.LENGTH_LONG).show();
        }
    }
}
