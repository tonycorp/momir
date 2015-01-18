package com.magic.momir.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
    private static final String KEY = "com.magic.momir";

    private interface Preferences{
        public static final String IMAGES_ENABLED = "images_enabled";
    }

    public static boolean imagesEnabled(final Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(Preferences.IMAGES_ENABLED, true);
    }

    public static boolean toggleImages(final Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        final boolean newState = !imagesEnabled(context);
        sharedPreferences.edit().putBoolean(Preferences.IMAGES_ENABLED, newState).apply();
        return newState;
    }

    private static SharedPreferences getSharedPreferences(final Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }
}
