package com.achmad.madeacademy.moviecataloguemvp.pref;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.ListPreference;

public class AppPreference {
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "reply";
    private static final String APP_ORDER = "reply";
    private ListPreference listPreference;

    public AppPreference(Context context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setOrder(String input){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(APP_ORDER, input);
        editor.apply();
    }

    public String getOrder(){

        return prefs.getString(APP_ORDER,"popular_movies");
    }
}
