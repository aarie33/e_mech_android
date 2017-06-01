package com.example.arieahmad.e_mech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                    Intent iCekEmail = new Intent(getApplicationContext(), Home.class);
                    startActivity(iCekEmail);
                    finish();
                }else {
                    Intent iCekEmail = new Intent(getApplicationContext(), Login.class);
                    startActivity(iCekEmail);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
