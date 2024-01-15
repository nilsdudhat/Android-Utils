package com.udemy.javaexample.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.udemy.javaexample.R;

public class AppUtils {

    public static void openBrowser(final Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, "Choose Browser"));
    }

    public static void rateApp(Activity activity) {
        try {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(goMarket);
        } catch (ActivityNotFoundException e) {
            Log.d("--app_utils--", "rateApp: " + Log.getStackTraceString(e));

            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(goMarket);
        }
    }

    public static void shareApp(Activity activity, String message) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            message = message + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            Log.d("--app_utils--", "shareApp: " + Log.getStackTraceString(e));
        }
    }
}
