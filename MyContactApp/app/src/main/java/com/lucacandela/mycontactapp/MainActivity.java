package com.lucacandela.mycontactapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        boolean isInserted = myDb.insertData(name,phone,address);

        if (isInserted){
            Toast.makeText(MainActivity.this, "Success - contact inserted",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Failure - contact not inserted",Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        //Log.d("MyContactApp","MainActivity: ");


        if (getData() !=null) {
            String[] results = getData().toString().split("--");

            String result = "";
            for (String s : results){

                result+= formatContactEntry(s);
            }
            showMessage("Contact List", result);
        }
        else
            showMessage("Error","No data found in db");
    }

    public StringBuffer getData(){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            //append res coulmns to the buffer -- see stringbuffer and cursor API's
            buffer.append(
                    res.getString(0) + ";" +
                            res.getString(1) + ";" +
                            res.getString(2) + ";" +
                           res.getString(3) + ";--");
        }
        return buffer;

    }

    public String formatContactEntry(String s){
        String out ="";

        String[] entry = s.split(";");
        String[] mod = new String[entry.length];
        mod[0] = "ID: " + entry[0];
        mod[1] = "Contact: " + entry[1];
        mod[2] = "Phone number: " + entry[2];
        mod[3] = "Address: " + entry[3];

        for (String x : mod){
            out+= x + "\n";
        }
        out+="\n";
        return out;
    }

    public void showMessage(String title, String msg){
        Log.d("MyContactApp","ActivityMonitor: Building dialogue window");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(msg);
        builder.show();
    }

    public void search(View v){
        Log.d("MyContactApp","ActivityMonitor: Searching for parameters");
        String name = editName.getText().toString();
        String phone = editNum.getText().toString();
        String address = editAddress.getText().toString();

        if (name.equals(""))
            name = null;
        if (phone.equals(""))
            phone = null;
        if (address.equals(""))
            address = null;

        if (!(name == null && phone == null && address == null))
        searchContacts(name,phone,address);
        else
            showMessage("Search Params\nNone","No results");
    }

    public void searchContacts(String name, String phone, String address){
        StringBuffer rawData = getData();
        Log.d("MyContactApp","searchContacts: " + rawData.toString());
        String[] contacts = rawData.toString().split("--");

        String results = "";
        int count = 0;

        if(name!=null)
            name = name.toLowerCase();
        if(address!= null)
            address = address.toLowerCase();
        List<String> returnedStrings = new ArrayList<String>();
        for (String c : contacts){
            String[] col = c.split(";");
            count = 0;
            for (String  s : col){

                col[count]=s.toLowerCase();
                count++;
                Log.d("MyContactApp",s);
            }
            if (name != null && phone != null && address != null){
                Log.d("MyContactApp","all params");
                if (col[1].contains(name) && col[2].contains(phone) && col[3].contains(address)){
                    returnedStrings.add(c);
                }

            }
            else if (name != null && phone != null){
                Log.d("MyContactApp","name & phone params");
                if (col[1].indexOf(name) >= 0 && col[2].indexOf(phone) >=0){
                    returnedStrings.add(c);
                }
            }
            else if (phone != null && address != null){
                Log.d("MyContactApp","phone and address params");
                if (col[2].indexOf(phone) >=0 && col[3].indexOf(address) >=0){
                    returnedStrings.add(c);
                }
            }
            else if (name != null && address!= null){
                Log.d("MyContactApp","name and address params");
                if (col[1].indexOf(name) >= 0 && col[3].indexOf(address) >=0){
                    returnedStrings.add(c);
                }
            }
            else if (name != null){
                Log.d("MyContactApp","name param only");
                if (col[1].contains(name)){
                    returnedStrings.add(c);
                }
                else
                {
                    Log.d("MyContactApp","Name param [ " + name + " ] did not match [ " + col[1] + " ] in contacts" );
                }
            }
            else if (phone != null){
                Log.d("MyContactApp","phone param only");
                if (col[2].indexOf(phone) >=0){
                    returnedStrings.add(c);
                }
            }
            else if (address != null){
                Log.d("MyContactApp","address param only");
                if (col[3].indexOf(address) >=0){
                    returnedStrings.add(c);
                }
            }
        }


        Log.d("MyContactApp",returnedStrings.toString());

        if (returnedStrings.size() == 0){
            results+= "No results found... check your search fields again.";
        }
        else
        {
            for (String r : returnedStrings){
                results+= formatContactEntry(r);
            }
        }
        String title = "Search Params:\n";
        if(name!= null)     title+="Name: " + name;
        if (phone!= null)   title+=", Phone: " + phone;
        if (address!=null)  title+= ", Address: " + address;
        if (title.equals("Search Params\n")) title += "none";



        showMessage(title, results);

    }

}
