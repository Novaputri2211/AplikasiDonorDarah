package com.example.aplikasidonordarah.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    public static final String PREFERENCES_NAME = "DONOR_DARAH";
    public static final String SESSION_KEY = "KEY_DONOR";

    public static final String ID_USER = "EXAMPLE_ID_USER";
    public static final String NAMA_USER = "EXAMPLE_NAMA_USER";
    public static final String GENDER = "EXAMPLE_GENDER";
    public static final String TOKEN = "EXAMPLE_TOKEN";

    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //session user(login logout)
    public void saveSessionUser(){
        editor.putBoolean(SESSION_KEY,true);
        editor.commit();
    }
    public boolean getSessionUser(){
        return preferences.getBoolean(SESSION_KEY, false);
    }
    public void removeSessionUser(){
        editor.putBoolean(SESSION_KEY,false);
        editor.commit();
    }

    //id user
    public void setIdUser(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }
    public String getIdUser(){
        return preferences.getString(ID_USER,"");
    }

    //nama user
    public void setNamaUser(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getNamaUser(){
        return preferences.getString(NAMA_USER,"");
    }

    //gender user
    public void setGenderUser(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getGenderUser(){
        return preferences.getString(GENDER,"");
    }

    //token user
    public void setTokenUser(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getTokenUser(){
        return preferences.getString(TOKEN, "");
    }

}
