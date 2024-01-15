package com.udemy.javaexample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    public static String getString(Activity activity, String key) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setString(Activity activity, String key, String value) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Activity activity, String key) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setBoolean(Activity activity, String key, boolean value) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static int getInteger(Activity activity, String key) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    public static void setInteger(Activity activity, String key, int value) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
}
