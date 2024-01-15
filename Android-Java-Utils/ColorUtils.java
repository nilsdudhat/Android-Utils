package com.udemy.javaexample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class ColorUtils {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setBottomNavigationColor(Activity activity, int color) {
        activity.getWindow().setNavigationBarColor(color);
    }

    public static int getAttributeColor(Context context, int attributeId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attributeId, typedValue, true);
        int colorRes = typedValue.resourceId;
        int color = -1;
        try {
            color = ContextCompat.getColor(context, colorRes);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return color;
    }
}
