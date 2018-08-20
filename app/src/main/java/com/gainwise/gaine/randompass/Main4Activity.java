package com.gainwise.gaine.randompass;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class Main4Activity extends AppCompatActivity {

    EditText et1;
    EditText et2;
    AdView adView;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String actualPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        et1 = (EditText)findViewById(R.id.editText1forpasscreation);
        et2 = (EditText)findViewById(R.id.editText2forpasscreation);
        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        et1.setTypeface(custom_font);
        et2.setTypeface(custom_font);

        adView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        initThePrefs();
    }

    public void saveAndExitClicked(View view){
        String et1t = et1.getText().toString().trim();
        String et2t = et2.getText().toString().trim();
        if(et1t.equals(et2t)){
            actualPass = et1.getText().toString().trim();
            editor.putString("actual_pass", actualPass);
            editor.putString("pin_created", "yes");

            editor.apply();
            FabToast.makeText(Main4Activity.this, "PIN created!", Toast.LENGTH_LONG,
                    FabToast.SUCCESS,FabToast.POSITION_DEFAULT).show();
            Bungee.fade(Main4Activity.this);
            Main4Activity.this.finish();

        }else{
            FabToast.makeText(Main4Activity.this, "Passwords do not match!", Toast.LENGTH_LONG,
                    FabToast.WARNING,FabToast.POSITION_CENTER).show();
        }

    }
   public void cancelAndExitClicked(View view){
       Bungee.fade(Main4Activity.this);
    Main4Activity.this.finish();
    }

    public void initThePrefs(){
        prefs = getSharedPreferences("PASSAPP", MODE_PRIVATE);
        editor = getSharedPreferences("PASSAPP", MODE_PRIVATE).edit();
        actualPass = prefs.getString("actual_pass", "000608");

    }
}
