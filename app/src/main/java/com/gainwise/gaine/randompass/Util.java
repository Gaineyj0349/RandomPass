package com.gainwise.gaine.randompass;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import spencerstudios.com.fab_toast.FabToast;

/**
 * Created by gaine on 12/28/2017.
 */

public class Util {
    final String fileName = "audio_cover";
    String masterJSONstring;
    Context context;
    ArrayList<PasswordEntry> passwordEntries;
    int position;
    JSONArray jsonArray;

    public Util(){

    }
    public Util(Context context){
        this.context = context;
    }

    public void deleteItemWithPass(String password, String label){
        Log.i("JOSH2 delete called", "" + password + " " + label);
        initJSONarray();
        passwordEntries = getPassAL();

        Log.i("JOSH2 masterJSONstring", masterJSONstring);

        for(int i = 0; i < passwordEntries.size(); i++){
          if(passwordEntries.get(i).getPassword().equals(password)&&(passwordEntries.get(i).getLabel().equals(label))){
                Log.i("JOSH2 this is position", "" + position);
                Log.i("JOSH2 this is value", "" + passwordEntries.get(i).getPassword());
                position = i;
                break;
            }
        }
        Log.i("JOSH2 DELETED POS", "" + position);
        passwordEntries.remove(position);
        rebuildJSON();


    }




    public void writeMessage(){



        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fileOutputStream.write(masterJSONstring.getBytes());
            fileOutputStream.close();
            FabToast.makeText(context, "Success!", FabToast.LENGTH_LONG,
                    FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage(){
        try {
            String message;
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((message = bufferedReader.readLine())!= null){
                stringBuffer.append(message);
            }
            masterJSONstring = stringBuffer.toString();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<PasswordEntry> getPassAL(){
        ArrayList<PasswordEntry> tempAL = new ArrayList<>();
        readMessage();

        try {

            JSONArray jarray2 = new JSONArray(masterJSONstring);

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


    public void reset(){

        masterJSONstring = "";
        passwordEntries.clear();
        position = -1;
    }

    public void initJSONarray() {
        readMessage();
        jsonArray = new JSONArray();


    }

    public void rebuildJSON() {



        try {

            for(int i = 0; i < passwordEntries.size(); i++) {
                JSONObject tempObj = new JSONObject();
                tempObj.put("label", passwordEntries.get(i).getLabel());
                tempObj.put("imageNum", passwordEntries.get(i).getImageNum());
                tempObj.put("password", passwordEntries.get(i).getPassword());
                jsonArray.put(tempObj);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        masterJSONstring = jsonArray.toString();
        Log.i("JOSH2 here array", masterJSONstring);

        writeMessage();
        reset();


    }

}
