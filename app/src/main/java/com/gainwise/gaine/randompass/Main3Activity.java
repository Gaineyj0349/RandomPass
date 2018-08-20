package com.gainwise.gaine.randompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kevalpatel.passcodeview.KeyNamesBuilder;
import com.kevalpatel.passcodeview.PinView;
import com.kevalpatel.passcodeview.indicators.CircleIndicator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.keys.RoundKey;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class Main3Activity extends AppCompatActivity {
    String actualPass;
    int[] passcode;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        PinView pinView = (PinView) findViewById(R.id.pin_view);
        initThePrefs();
        convertToIntArray(actualPass);
        pinView.setCorrectPin(passcode);

        //Build the desired key shape and pass the theme parameters.
        //REQUIRED
        pinView.setKey(new RoundKey.Builder(pinView)
                .setKeyPadding(R.dimen.lib_key_padding)
                .setKeyStrokeColorResource(R.color.colorAccent)
                .setKeyStrokeWidth(R.dimen.lib_key_stroke_width)
                .setKeyTextColorResource(R.color.colorAccent)
                .setKeyTextSize(R.dimen.lib_key_text_size)
                .build());

        //Build the desired indicator shape and pass the theme attributes.
        //REQUIRED
        pinView.setIndicator(new CircleIndicator.Builder(pinView)
                .setIndicatorRadius(R.dimen.lib_indicator_radius)
                .setIndicatorFilledColorResource(R.color.colorAccent)
                .setIndicatorStrokeColorResource(R.color.colorAccent)
                .setIndicatorStrokeWidth(R.dimen.lib_indicator_stroke_width)
                .build());

        //Set the name of the keys based on your locale.
        //OPTIONAL. If not passed key names will be displayed based on english locale.
        pinView.setKeyNames(new KeyNamesBuilder()
                .setKeyOne(this, R.string.key_1)
                .setKeyTwo(this, R.string.key_2)
                .setKeyThree(this, R.string.key_3)
                .setKeyFour(this, R.string.key_4)
                .setKeyFive(this, R.string.key_5)
                .setKeySix(this, R.string.key_6)
                .setKeySeven(this, R.string.key_7)
                .setKeyEight(this, R.string.key_8)
                .setKeyNine(this, R.string.key_9)
                .setKeyZero(this, R.string.key_0));

        pinView.setAuthenticationListener(new AuthenticationListener() {
            @Override
            public void onAuthenticationSuccessful() {
                if(MainActivity.goToSetup){

                FabToast.makeText(Main3Activity.this, "Access Granted", Toast.LENGTH_LONG
                , FabToast.SUCCESS, FabToast.POSITION_CENTER).show();
               Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
               startActivity(intent);

                Bungee.slideUp(Main3Activity.this);
                Main3Activity.this.finish();}
                else{
                    FabToast.makeText(Main3Activity.this, "Access Granted", Toast.LENGTH_LONG
                            , FabToast.SUCCESS, FabToast.POSITION_CENTER).show();
                    Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                    startActivity(intent);
                    Bungee.slideUp(Main3Activity.this);

                }
            }

            @Override
            public void onAuthenticationFailed() {
                Bungee.fade(Main3Activity.this);
                Main3Activity.this.finish();
                FabToast.makeText(Main3Activity.this, "Access Denied", Toast.LENGTH_LONG
                        , FabToast.ERROR, FabToast.POSITION_CENTER).show();

            }
        });

    }

    public void convertToIntArray(String actualPassPassed){
        int length = actualPassPassed.length();
       int[] temparr = new int[length];
        for(int i = 0; i< length; i++){
            temparr[i] = Integer.parseInt(String.valueOf(actualPassPassed.charAt(i)));
        }
        passcode = temparr;
    }
    public void initThePrefs(){
        prefs = getSharedPreferences("PASSAPP", MODE_PRIVATE);
        actualPass = prefs.getString("actual_pass", "000608");

    }



}
