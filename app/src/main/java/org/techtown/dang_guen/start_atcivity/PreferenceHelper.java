package org.techtown.dang_guen.start_atcivity;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper
{
    private final String INTRO = "intro";
    private final String phone_number = "phone_number";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context)
    {
        app_prefs = context.getSharedPreferences("shared", 0);
        this.context = context;
    }

    public void putIsLogin(boolean loginOrOut)
    {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginOrOut);
        edit.apply();
    }

    public void putName(String loginOrOut)
    {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(phone_number, loginOrOut);
        edit.apply();
    }

}

