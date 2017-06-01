package com.example.arieahmad.e_mechteknisi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arie Ahmad on 4/29/2017.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "e_mech_sharedpref_teknisi";
    private static final String KEY_EMAIL_TEKNISI = "email_teknisi_e_mech";
    private static final String KEY_NAMA_TEKNISI = "nama_teknisi_e_mech";
    private static final String KEY_STATUS_TEKNISI = "status_teknisi_e_mech";
    private static final String STATUS_LOGIN = "login_status_teknisi_e_mech";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean teknisiLogin(String email, String nama, String status){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL_TEKNISI, email);
        editor.putString(KEY_NAMA_TEKNISI, nama);
        editor.putString(KEY_STATUS_TEKNISI, status);
        editor.putString(STATUS_LOGIN, "login");

        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(STATUS_LOGIN, null) != null){
        //if(STATUS_LOGIN.equals("login")){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public boolean setEmail(String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL_TEKNISI, email);

        editor.apply();
        return true;
    }

    public String getNama(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_TEKNISI, null);
    }
    public String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL_TEKNISI, null);
    }
    public String getStatusTeknisi(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STATUS_TEKNISI, null);
    }
}
