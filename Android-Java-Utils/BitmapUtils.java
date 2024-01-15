package com.udemy.javaexample.utils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BitmapUtils {

    public static Bitmap getBitmapFromAsset(Activity activity, String strName) {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("Raw/" + strName);
        } catch (IOException e) {
            Log.d("--bitmap--", "getBitmapFromAsset: " + Log.getStackTraceString(e));
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public static Bitmap getBitmapFromPath(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(path, bmOptions);
    }

    public static Bitmap getBitmapFromURL(String path) {
        try {
            URL url = new URL(path);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            Log.d("--bitmap--", "getBitmapFromURL: " + Log.getStackTraceString(e));
        }
        return null;
    }

    public static String getBitmapSize(long length) {
        float sizeInKB = (float) (length / 1024);
        if (sizeInKB >= 1000) {
            return (int) (sizeInKB / 1024) + " MB";
        }
        return (int) sizeInKB + " KB";
    }

}
