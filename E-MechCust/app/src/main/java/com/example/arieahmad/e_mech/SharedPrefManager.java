package com.example.arieahmad.e_mech;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arie Ahmad on 4/30/2017.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "e_mech_sharedpref_cust";
    private static final String KEY_EMAIL_CUST = "email_cust_e_mech";
    private static final String KEY_NAMA_CUST = "nama_cust_e_mech";
    private static final String KEY_TELP_CUST = "telp_cust_e_mech";
    private static final String KEY_JKEL_CUST = "jkel_cust_e_mech";
    private static final String KEY_ALAMAT_CUST = "alamat_cust_e_mech";
    private static final String STATUS_LOGIN = "login_status_cust_e_mech";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean custLogin(String email, String nama, String telp, String jkel, String alamat){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL_CUST, email);
        editor.putString(KEY_NAMA_CUST, nama);
        editor.putString(KEY_TELP_CUST, telp);
        editor.putString(KEY_JKEL_CUST, jkel);
        editor.putString(KEY_ALAMAT_CUST, alamat);
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

        editor.putString(KEY_EMAIL_CUST, email);

        editor.apply();
        return true;
    }

    public String getNama(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_CUST, null);
    }
    public String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL_CUST, null);
    }
    public String getTelp(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TELP_CUST, null);
    }
    public String getJkel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JKEL_CUST, null);
    }
    public String getAlamat(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ALAMAT_CUST, null);
    }

}
