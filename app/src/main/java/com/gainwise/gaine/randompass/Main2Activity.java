package com.gainwise.gaine.randompass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
  public static  MyAdapter adapter;
    ArrayList<PasswordEntry> passwordEntries;

   static String retrievedJSONstring;
    final String fileName = "audio_cover";
    String firstRun2;

    AdView adView;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Bungee.fade(Main2Activity.this);


                Snackbar.make(view, "ReLocked and Archived", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        retrievedJSONstring = "";
        passwordEntries = getPassAL();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(Main2Activity.this, passwordEntries);
        recyclerView.setAdapter(adapter);
        initThePrefs();
        if(firstRun2.equals("yes")){
            seeTheDialog();
        }



    }


    public  ArrayList<PasswordEntry> getPassAL(){
        ArrayList<PasswordEntry> tempAL = new ArrayList<>();
        readMessage();
    //    String retrievedJSONstring = prefs.getString("master", "");
        Log.i("JOSH retrieved this", retrievedJSONstring);
        try {

            JSONArray jarray2 = new JSONArray(retrievedJSONstring);

            for (int i = 0; i<jarray2.length(); i++){
                JSONObject tempobj2 = jarray2.getJSONObject(i);
                PasswordEntry entry = new PasswordEntry();
                entry.setLabel(tempobj2.getString("label"));
                entry.setImageNum(tempobj2.getInt("imageNum"));
                entry.setPassword(tempobj2.getString("password"));
                tempAL.add(entry)  ;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tempAL;
    }



    public void readMessage(){
        try {
            String message;
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((message = bufferedReader.readLine())!= null){
                stringBuffer.append(message);
            }
            retrievedJSONstring = stringBuffer.toString();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.fade(Main2Activity.this); //fire the slide left animation
    }

    @Override
    protected void onPause() {
        super.onPause();
        Main2Activity.this.finish();
    }
    public static void init(){

    }
    public void seeTheDialog(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Main2Activity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Main2Activity.this);
        }
        builder.setTitle(" YOUR VAULT")
                .setMessage(R.string.dialog1)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("first_run2", "no");

                        editor.apply();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public void initThePrefs(){
         prefs = getSharedPreferences("PASSAPP", MODE_PRIVATE);
        editor = getSharedPreferences("PASSAPP", MODE_PRIVATE).edit();

        firstRun2 = prefs.getString("first_run2", "yes");
    }

}
