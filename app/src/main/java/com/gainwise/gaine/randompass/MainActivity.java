package com.gainwise.gaine.randompass;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
import java.util.Collections;
import java.util.Random;

import osmandroid.project_basics.Task;
import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class MainActivity extends AppCompatActivity {

    public static boolean goToSetup = false;

    TextView tv1inLL1;
    TextView tv2inLL1;
    int currentValuesb1 = 0;
    JSONArray jsonArray;
    final String fileName = "audio_cover";
    AdView adView;

    int imagelevel = 0;

    SeekBar seekBar2;
    Switch switchinLL2;
    LinearLayout LLforLower;
    LinearLayout LLforLowerP;
    TextView tv2inLL2;
    int currentValuesb2 = 0;

    String firstRun;
    String PINcreated;
    boolean readyToArchive = false;



    SeekBar seekBar3;
    Switch switchinLL3;
    LinearLayout LLforUpper;
    LinearLayout LLforUpperP;
    TextView tv2inLL3;
    int currentValuesb3 = 0;

    SeekBar seekBar6;
    Switch switchinLL6;
    LinearLayout LLforNumber;
    LinearLayout LLforNumberP;
    TextView tv2inLL6;
    int currentValuesb6 = 0;


    SeekBar seekBar4;
    Switch switchinLL4;
    LinearLayout LLforSpecial;
    LinearLayout LLforSpecialP;
    TextView tv2inLL4;
    int currentValuesb4 = 0;


    EditText editText;
    Switch switchinLL5;
    LinearLayout LLforString;
    LinearLayout LLforStringP;
    TextView tv2inLL5;
    int actualValueb5forCalc = 0;

    boolean haslowercase = false;
    boolean hasuppercase = false;
    boolean hasspecial = false;
    boolean hasstring = false;
    boolean hasNumber = false;

    TextView tvForGen;
    Button genButt;
    Button copyButt;
    Button archiveButt;


    StringBuilder magicString;
    String passwordBuilt = "";

    ArrayList<String> alFORlower = new ArrayList<>();
    ArrayList<String> alFORupper = new ArrayList<>();
    ArrayList<String> alFORnumber = new ArrayList<>();
    ArrayList<String> alFORspecial = new ArrayList<>();


    String masterJSONstring;
    ArrayList<PasswordEntry> builtALfromSP;
    Random myRandom;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String actualPass;
    int[] passcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abl);
        initThePrefs();
        builtALfromSP = new ArrayList<>();
        masterJSONstring = "";
        initPrefAndJSON();
        myRandom = new Random();

        tv1inLL1 = (TextView) findViewById(R.id.tv1LL1);
        tv2inLL1 = (TextView) findViewById(R.id.tv2LL1);

        seekBar2 = (SeekBar) findViewById(R.id.sb1LL2);
        switchinLL2 = (Switch) findViewById(R.id.switch1LL2);
        tv2inLL2 = (TextView) findViewById(R.id.tv2LL2);
        LLforLower = (LinearLayout) findViewById(R.id.LLforlower);
        LLforLowerP = (LinearLayout) findViewById(R.id.LLforlowerP);

        seekBar3 = (SeekBar) findViewById(R.id.sb1LL3);
        switchinLL3 = (Switch) findViewById(R.id.switch1LL3);
        tv2inLL3 = (TextView) findViewById(R.id.tv2LL3);
        LLforUpper = (LinearLayout) findViewById(R.id.LLforupper);
        LLforUpperP = (LinearLayout) findViewById(R.id.LLforupperP);

        seekBar6 = (SeekBar) findViewById(R.id.sb1LL6);
        switchinLL6 = (Switch) findViewById(R.id.switch1LL6);
        tv2inLL6 = (TextView) findViewById(R.id.tv2LL6);
        LLforNumber = (LinearLayout) findViewById(R.id.LLfornumber);
        LLforNumberP = (LinearLayout) findViewById(R.id.LLfornumberP);

        seekBar4 = (SeekBar) findViewById(R.id.sb1LL4);
        switchinLL4 = (Switch) findViewById(R.id.switch1LL4);
        tv2inLL4 = (TextView) findViewById(R.id.tv2LL4);
        LLforSpecial = (LinearLayout) findViewById(R.id.LLforspecial);
        LLforSpecialP = (LinearLayout) findViewById(R.id.LLforspecialP);

        editText = (EditText) findViewById(R.id.editText);
        switchinLL5 = (Switch) findViewById(R.id.switch1LL5);
        tv2inLL5 = (TextView) findViewById(R.id.tv2LL5);
        LLforString = (LinearLayout) findViewById(R.id.LLforstring);
        LLforStringP = (LinearLayout) findViewById(R.id.LLforstringP);

        tvForGen = (TextView) findViewById(R.id.tvFORRANDOMGEN);
        archiveButt = (Button) findViewById(R.id.button5);
        copyButt = (Button) findViewById(R.id.button4);
        genButt = (Button) findViewById(R.id.button3);


        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                currentValuesb2 = progress;
                tv2inLL2.setText(currentValuesb2 + " Characters");

                Log.i("JOSH progress = ", "" + progress);
                Log.i("JOSH actual = ", "" + currentValuesb2);
                updateSB1();

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        switchinLL2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    currentValuesb2 = seekBar2.getProgress();
                    Log.i("JOSH actual = ", "" + currentValuesb2);
                    haslowercase = true;
                    LLforLower.setVisibility(View.VISIBLE);
                    LLforLowerP.setBackgroundColor(Color.parseColor("#ffffff"));
                    updateSB1();
                } else {
                    seekBar2.setProgress(0);
                    Log.i("JOSH actual = ", "" + currentValuesb2);
                    haslowercase = false;
                    LLforLower.setVisibility(View.GONE);
                    LLforLowerP.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    updateSB1();
                }
            }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentValuesb3 = progress;
                tv2inLL3.setText(currentValuesb3 + " Characters");

                Log.i("JOSH progress = ", "" + progress);
                Log.i("JOSH actual = ", "" + currentValuesb3);

                updateSB1();


            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        switchinLL3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    currentValuesb3 = seekBar3.getProgress();
                    Log.i("JOSH actual = ", "" + currentValuesb3);
                    hasuppercase = true;
                    LLforUpper.setVisibility(View.VISIBLE);
                    LLforUpperP.setBackgroundColor(Color.parseColor("#ffffff"));
                    updateSB1();
                } else {
                    seekBar3.setProgress(0);
                    Log.i("JOSH actual = ", "" + currentValuesb3);
                    hasuppercase = false;
                    LLforUpper.setVisibility(View.GONE);
                    LLforUpperP.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    updateSB1();
                }
            }
        });


        seekBar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentValuesb6 = progress;
                tv2inLL6.setText(currentValuesb6 + " Characters");

                Log.i("JOSH progress = ", "" + progress);
                Log.i("JOSH actual = ", "" + currentValuesb6);

                updateSB1();


            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        switchinLL6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    currentValuesb6 = seekBar6.getProgress();
                    Log.i("JOSH actual = ", "" + currentValuesb3);
                    hasNumber = true;
                    LLforNumber.setVisibility(View.VISIBLE);
                    LLforNumberP.setBackgroundColor(Color.parseColor("#ffffff"));
                    updateSB1();
                } else {
                    seekBar6.setProgress(0);
                    Log.i("JOSH actual = ", "" + currentValuesb3);
                    hasNumber = false;
                    LLforNumber.setVisibility(View.GONE);
                    LLforNumberP.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    updateSB1();
                }
            }
        });

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                currentValuesb4 = progress;
                tv2inLL4.setText(currentValuesb4 + " Characters");

                Log.i("JOSH progress = ", "" + progress);
                Log.i("JOSH actual = ", "" + currentValuesb4);

                updateSB1();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        switchinLL4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    currentValuesb4 = seekBar4.getProgress();
                    Log.i("JOSH actual = ", "" + currentValuesb4);
                    hasspecial = true;
                    LLforSpecial.setVisibility(View.VISIBLE);
                    LLforSpecialP.setBackgroundColor(Color.parseColor("#ffffff"));
                    updateSB1();
                } else {
                    seekBar4.setProgress(0);
                    Log.i("JOSH actual = ", "" + currentValuesb4);
                    hasspecial = false;
                    LLforSpecial.setVisibility(View.GONE);
                    LLforSpecialP.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    updateSB1();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                actualValueb5forCalc = editText.getText().toString().trim().length();
                tv2inLL5.setText(actualValueb5forCalc + " Characters");
                updateSB1();
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        switchinLL5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hasstring = true;
                    LLforString.setVisibility(View.VISIBLE);
                    LLforStringP.setBackgroundColor(Color.parseColor("#ffffff"));
                    updateSB1();
                } else {
                    editText.setText("");
                    actualValueb5forCalc = 0;
                    hasstring = false;
                    LLforString.setVisibility(View.GONE);
                    LLforStringP.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    updateSB1();
                }
            }
        });


        changeFonts();
        initSeekBars();
        initSwitches();

        adView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
       if(firstRun.equals("yes")){
           seeTheDialog();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.follow) {
            Task.FollowOnFb(MainActivity.this,"1953614828228585","https://www.facebook.com/RealGainWise/");
            return true;
        }
        if (id == R.id.rate) {
            Task.RateApp(MainActivity.this, "com.gainwise.gaine.randompass");
            return true;
        }
        if (id == R.id.forgot) {
            forgotDialog();
            return true;
        }
        if (id == R.id.share) {
            Task.ShareApp(MainActivity.this, "com.gainwise.gaine.randompass",
                    "Password Generator","Easily create random passwords, and store them in a hidden locked local file.");
            return true;
        }
          if (id == R.id.pin) {
            goToSetup = true;
            seeTheDialog2();
           return true;
        }

        if (id == R.id.otherapps) {
/*
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub: GainWise")));
                Bungee.zoom(MainActivity.this);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=GainWise")));
                Bungee.zoom(MainActivity.this);
            }
*/
            Task.MoreApps(MainActivity.this,"GainWise");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeFonts() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        tv1inLL1.setTypeface(custom_font);
        tv2inLL1.setTypeface(custom_font);
        switchinLL2.setTypeface(custom_font);
        tv2inLL2.setTypeface(custom_font);
        switchinLL3.setTypeface(custom_font);
        tv2inLL3.setTypeface(custom_font);
        switchinLL4.setTypeface(custom_font);
        tv2inLL4.setTypeface(custom_font);
        switchinLL5.setTypeface(custom_font);
        tv2inLL5.setTypeface(custom_font);
        switchinLL6.setTypeface(custom_font);
        tv2inLL6.setTypeface(custom_font);
        editText.setTypeface(custom_font);
        tvForGen.setTypeface(custom_font);
        archiveButt.setTypeface(custom_font);
        genButt.setTypeface(custom_font);
        copyButt.setTypeface(custom_font);
    }

    public void initSeekBars() {


        tv2inLL1.setText(currentValuesb1 + " Characters");

        seekBar2.setProgress(0);
        currentValuesb2 = seekBar2.getProgress();

        tv2inLL2.setText(currentValuesb2 + " Characters");

        seekBar3.setProgress(0);
        currentValuesb3 = seekBar3.getProgress();

        tv2inLL3.setText(currentValuesb3 + " Characters");

        seekBar6.setProgress(0);
        currentValuesb6 = seekBar6.getProgress();

        tv2inLL6.setText(currentValuesb3 + " Characters");

        seekBar4.setProgress(0);
        currentValuesb4 = seekBar3.getProgress();

        tv2inLL4.setText(currentValuesb4 + " Characters");

        tv2inLL5.setText("0 Characters");
    }

    public void initSwitches() {

        switchinLL2.setChecked(false);
        switchinLL3.setChecked(false);
        switchinLL4.setChecked(false);
        switchinLL5.setChecked(false);
        switchinLL6.setChecked(false);
        LLforLower.setVisibility(View.GONE);
        LLforLowerP.setBackgroundColor(Color.parseColor("#FFCDD2"));
        LLforUpper.setVisibility(View.GONE);
        LLforUpperP.setBackgroundColor(Color.parseColor("#FFCDD2"));
        LLforSpecial.setVisibility(View.GONE);
        LLforSpecialP.setBackgroundColor(Color.parseColor("#FFCDD2"));
        LLforString.setVisibility(View.GONE);
        LLforStringP.setBackgroundColor(Color.parseColor("#FFCDD2"));
        LLforNumber.setVisibility(View.GONE);
        LLforNumberP.setBackgroundColor(Color.parseColor("#FFCDD2"));
    }


    public void archiveClicked(View view) {
    if(readyToArchive){
        final Dialog dialog = new Dialog(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogentry, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final EditText etInDialog = (EditText)mView.findViewById(R.id.etInDialog);
        Button button = (Button)mView.findViewById(R.id.buttonInDialog);
        Button button2 = (Button)mView.findViewById(R.id.buttonInDialog2);

       final ImageButton ib1 = (ImageButton)mView.findViewById(R.id.ib1);
       final ImageButton ib2 = (ImageButton)mView.findViewById(R.id.ib2);
       final ImageButton ib3 = (ImageButton)mView.findViewById(R.id.ib3);
      final  ImageButton ib4 = (ImageButton)mView.findViewById(R.id.ib4);
       final ImageButton ib5 = (ImageButton)mView.  findViewById(R.id.ib5);
      final  ImageButton ib6 = (ImageButton)mView.findViewById(R.id.ib6);
    final    ImageButton ib7 = (ImageButton)mView.findViewById(R.id.ib7);
     final   ImageButton ib8 = (ImageButton)mView.findViewById(R.id.ib8);
      final  ImageButton ib9 = (ImageButton)mView.findViewById(R.id.ib9);
     final   ImageButton ib10 = (ImageButton)mView.findViewById(R.id.ib10);
     final   ImageButton ib11 = (ImageButton)mView.findViewById(R.id.ib11);
      final  ImageButton ib12 = (ImageButton)mView.findViewById(R.id.ib12);

       final TextView tvdialog = (TextView)mView.findViewById(R.id.passIDdialog);
        tvdialog.setText(passwordBuilt);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 1;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 2;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 3;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 4;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 5;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 6;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 7;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 8;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 9;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 10;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 11;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        ib12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 12;
                ib1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib2.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib5.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib6.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib7.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib8.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib9.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib10.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib11.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);

                ib12.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = etInDialog.getText().toString().trim();

                if((label.length()>0) && (imagelevel>0)){
                    createEntry(label, imagelevel);
                    readyToArchive = false;
                    imagelevel = 0;
                    dialog.dismiss();
                }else{
                    FabToast.makeText(MainActivity.this, "You must pick an icon and enter a label.", FabToast.LENGTH_LONG,
                            FabToast.WARNING,FabToast.POSITION_DEFAULT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelevel = 0;
                    dialog.dismiss();

            }
        });


        dialog.setContentView(mView);
        dialog.show();


    }else{
        FabToast.makeText(MainActivity.this, "Nothing to archive!", FabToast.LENGTH_LONG, FabToast.WARNING,
                FabToast.POSITION_DEFAULT).show();
    }



    }

    public void copyClicked(View view) {
        if (passwordBuilt.length() > 0) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("New Password", passwordBuilt);
            clipboard.setPrimaryClip(clip);
            Log.i("JOSH here master", masterJSONstring);
        } else {
            FabToast.makeText(MainActivity.this, "Nothing to copy!", FabToast.LENGTH_LONG, FabToast.WARNING,
                    FabToast.POSITION_DEFAULT).show();

        }
    }

    public void generateClick(View view) {
        startGen();
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            hideSoftKeyboard(MainActivity.this);
            Log.d("JOSH UP","Software Keyboard was shown");
        } else {
            Log.d("JOSH DOWN","Software Keyboard was not shown");

        }
    }

    public void updateSB1() {
        if (haslowercase && !hasuppercase && !hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && !hasuppercase && !hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(+seekBar2.getProgress() + " Characters");
        }
        if (haslowercase && hasuppercase && !hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && hasuppercase && !hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar3.getProgress() + " Characters");

        }
        if (haslowercase && hasuppercase && hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && hasuppercase && hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar3.getProgress() + seekBar4.getProgress() + " Characters");


        }
        if (haslowercase && hasuppercase && hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && hasuppercase && hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar3.getProgress() + seekBar4.getProgress() + actualValueb5forCalc + " Characters");
        }

        if (haslowercase && !hasuppercase && hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && !hasuppercase && hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar4.getProgress() + " Characters");
        }
        if (haslowercase && !hasuppercase && hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && !hasuppercase && hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar4.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (haslowercase && !hasuppercase && !hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && !hasuppercase && !hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (haslowercase && hasuppercase && !hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "haslowercase && hasuppercase && !hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar2.getProgress() + seekBar3.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (!haslowercase && hasuppercase && hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && hasuppercase && hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar4.getProgress() + seekBar3.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (!haslowercase && hasuppercase && hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && hasuppercase && hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar4.getProgress() + seekBar3.getProgress() + " Characters");
        }
        if (!haslowercase && hasuppercase && !hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && hasuppercase && !hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar3.getProgress() + " Characters");
        }
        if (!haslowercase && !hasuppercase && hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && !hasuppercase && hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(seekBar4.getProgress() + actualValueb5forCalc + " Characters"
            );
        }
        if (!haslowercase && !hasuppercase && hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && !hasuppercase && hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText(seekBar4.getProgress() + " Characters");
        }
        if (!haslowercase && !hasuppercase && !hasspecial && hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && !hasuppercase && !hasspecial && hasstring && !hasNumber");
            tv2inLL1.setText(actualValueb5forCalc + " Characters");
        }
        if (!haslowercase && !hasuppercase && !hasspecial && !hasstring && !hasNumber) {
            Log.i("JOSH updatesub1 = ", "!haslowercase && !hasuppercase && !hasspecial && !hasstring && !hasNumber");
            tv2inLL1.setText("" + 0 + " Characters");
        }


        if (hasNumber && !haslowercase && !hasuppercase && !hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && !hasuppercase && !hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + " Characters");
        }
        if (hasNumber && haslowercase && !hasuppercase && !hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && haslowercase && !hasuppercase && !hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar2.getProgress() + " Characters");
        }
        if (hasNumber && haslowercase && hasuppercase && !hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && haslowercase && hasuppercase && !hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar2.getProgress()
                    + seekBar3.getProgress() + " Characters");
        }

        if (hasNumber && haslowercase && hasuppercase && hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && haslowercase && hasuppercase && hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar2.getProgress()
                    + seekBar3.getProgress() + seekBar4.getProgress() + " Characters");
        }
        if (hasNumber && haslowercase && hasuppercase && hasspecial && hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && haslowercase && hasuppercase && hasspecial && hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar2.getProgress()
                    + seekBar3.getProgress() + seekBar4.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (hasNumber && !haslowercase && hasuppercase && !hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && hasuppercase && !hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar3.getProgress() + " Characters");
        }
        if (hasNumber && !haslowercase && hasuppercase && hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && hasuppercase && hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress()
                    + seekBar3.getProgress() + seekBar4.getProgress() + " Characters");
        }
        if (hasNumber && !haslowercase && hasuppercase && hasspecial && hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && hasuppercase && hasspecial && hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + actualValueb5forCalc
                    + seekBar3.getProgress() + seekBar4.getProgress() + " Characters");
        }
        if (hasNumber && !haslowercase && !hasuppercase && hasspecial && !hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && !hasuppercase && hasspecial && !hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar4.getProgress() + " Characters");

        }
        if (hasNumber && !haslowercase && !hasuppercase && hasspecial && hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && !hasuppercase && hasspecial && hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar4.getProgress()
                    + actualValueb5forCalc + " Characters");

        }
        if (hasNumber && !haslowercase && !hasuppercase && !hasspecial && hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && !hasuppercase && !hasspecial && hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + actualValueb5forCalc + " Characters");
        }
        if (hasNumber && !haslowercase && hasuppercase && !hasspecial && hasstring) {
            Log.i("JOSH updatesub1 = ", "hasNumber && !haslowercase && hasuppercase && !hasspecial && hasstring");
            tv2inLL1.setText(seekBar6.getProgress() + seekBar3.getProgress() + actualValueb5forCalc + " Characters");
        }
    }

    public char getRandomLowerCase() {

        return (char) (myRandom.nextInt(26) + 'a');
    }

    public char getRandomUpperCase() {

        return (char) (myRandom.nextInt(26) + 'A');
    }

    public char getRandomSpecial() {
        char[] symbols = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};

        return symbols[myRandom.nextInt(9)];
    }

    public void startGen() {
        magicString = new StringBuilder();
        passwordBuilt = "";
        int numOflower = seekBar2.getProgress();
        Log.i("lower count is ", "" + numOflower);
        int numOfUpper = seekBar3.getProgress();
        Log.i("upper count is ", "" + numOfUpper);
        int numOfNumber = seekBar6.getProgress();
        Log.i("number count is ", "" + numOfNumber);
        int numOfSpecial = seekBar4.getProgress();
        Log.i("special count is ", "" + numOfSpecial);
        int numOfSeed = actualValueb5forCalc;
        Log.i("edittext count is ", "" + numOfSeed);

        if (numOflower > 0) {
            for (int i = 0; i < numOflower; i++) {
                String temp = String.valueOf(getRandomLowerCase());
                alFORlower.add(temp);
                magicString.append(temp);
            }
        }
        if (numOfUpper > 0) {
            for (int i = 0; i < numOfUpper; i++) {
                String temp = String.valueOf(getRandomUpperCase());
                alFORlower.add(temp);
                magicString.append(temp);
            }
        }
        if (numOfNumber > 0) {
            for (int i = 0; i < numOfNumber; i++) {
                String temp = String.valueOf(myRandom.nextInt(10));
                alFORlower.add(temp);
                magicString.append(temp);
            }
        }
        if (numOfSpecial > 0) {
            for (int i = 0; i < numOfSpecial; i++) {
                String temp = String.valueOf(getRandomSpecial());
                alFORlower.add(temp);
                magicString.append(temp);
            }
        }


        passwordBuilt = getScrambledString(magicString.toString());


        if (actualValueb5forCalc > 0) {
            StringBuilder tempBuilder = new StringBuilder();
            String str = editText.getText().toString().trim();
            tempBuilder.append(str);
            tempBuilder.append(passwordBuilt);
            passwordBuilt = tempBuilder.toString();
        }


        if (passwordBuilt.length() > 0) {
            readyToArchive = true;
            tvForGen.setText(passwordBuilt);
        } else {
            readyToArchive = false;
            tvForGen.setText("Generated password will appear here!");
            FabToast.makeText(MainActivity.this, "Generate what?", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();

        }


        alFORlower.clear();
        alFORnumber.clear();
        alFORspecial.clear();
        alFORupper.clear();


//        initSeekBars();
        //      initSwitches();


    }



    public String getScrambledString(String stringIn) {
        String stringOut = "";
        int size = stringIn.length();
        ArrayList<String> AL1 = new ArrayList<>();
        StringBuilder SB1 = new StringBuilder();

        for (int i = 0; i < size; i++) {
            AL1.add(String.valueOf(stringIn.charAt(i)));
        }

        Collections.shuffle(AL1);

        for (int i = 0; i < size; i++) {
            SB1.append(AL1.get(i));
        }

        stringOut = SB1.toString();
        return stringOut;
    }

    @Override
    protected void onResume() {
        super.onResume();
        goToSetup = false;
        initThePrefs();
    }

    public void createEntry(String label, int imagelevelpassed) {


        try {
            //    PasswordEntry entry = new PasswordEntry();
            JSONObject tempObj = new JSONObject();

            //   entry.setLabel("label test");
            tempObj.put("label", label);

            //  entry.setImageNum(2);
            tempObj.put("imageNum", imagelevel);

            //  entry.setPassword(passwordBuilt);
            tempObj.put("password", passwordBuilt);

            jsonArray.put(tempObj);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        masterJSONstring = jsonArray.toString();
        Log.i("JOSH here array", masterJSONstring);

        writeMessage();
    //    editor.putString("master", masterJSONstring);
     //   editor.apply();
        masterJSONstring = "";
        initSwitches();
        initSeekBars();
        tvForGen.setText("Generated password will appear here!");

    }

    public void initPrefAndJSON() {
 //       prefs = getSharedPreferences("JSON", MODE_PRIVATE);
   //     editor = getSharedPreferences("JSON", MODE_PRIVATE).edit();
     //   masterJSONstring = prefs.getString("master", "");
        readMessage();
        if (masterJSONstring.length() > 0) {
            try {
                jsonArray = new JSONArray(masterJSONstring);
                Log.i("JOSH array initialized", "yes");
            } catch (JSONException e) {
                Log.i("JOSH array initialized", "no" + e.toString());
                e.printStackTrace();
            }
        } else {
            jsonArray = new JSONArray();
        }


    }

    public void writeMessage(){

        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(masterJSONstring.getBytes());
            fileOutputStream.close();
            FabToast.makeText(MainActivity.this, "Success!", FabToast.LENGTH_LONG,
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
            FileInputStream fileInputStream = openFileInput(fileName);
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.fade(MainActivity.this);
    }

    public void ibClicked(View view){
        seeTheDialog2();
    }
    public void pinActivityLaunch(){
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
        Bungee.slideDown(MainActivity.this);
    }
    public void pinSetupActivityLaunch(){
        Intent intent = new Intent(MainActivity.this, Main4Activity.class);
        startActivity(intent);
        Bungee.slideDown(MainActivity.this);
    }
    public void forgotDialog(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(" Reset the App")
                .setMessage("If you forgot your PIN, you must clear the app data in your phone settings. This will also erase all of your " +
                        "saved passwords within app. If you need instructions on how to do this, just google how to reset app data on your specific device.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


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

    public void seeTheDialog(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(" Welcome")
                .setMessage(R.string.dialog2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("first_run", "no");

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
    public void seeTheDialog2() {

        if (PINcreated.equals("yes")) {


            pinActivityLaunch();

        } else {


            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setTitle(" Setup PIN")
                    .setMessage("You must set up a PIN before going into the vault! Would you like to setup a PIN now?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            pinSetupActivityLaunch();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            goToSetup = false;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show()
            .setCanceledOnTouchOutside(false);

        }
    }

    public void initThePrefs(){
         prefs = getSharedPreferences("PASSAPP", MODE_PRIVATE);
        editor = getSharedPreferences("PASSAPP", MODE_PRIVATE).edit();
        PINcreated = prefs.getString("pin_created", "no");
        firstRun = prefs.getString("first_run", "yes");

    }



}




