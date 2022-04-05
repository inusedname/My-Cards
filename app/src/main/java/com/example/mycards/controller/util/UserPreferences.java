package com.example.mycards.controller.util;

import android.content.SharedPreferences;

public class UserPreferences {

    private static SharedPreferences settings;

    public static void setSettings(SharedPreferences settings) {
        UserPreferences.settings = settings;
    }

    public static int getThemePreference () {
        return UserPreferences.settings.getInt("ThemePreference", 2);
    }
    public static boolean showGettingStartDialog () {
        return UserPreferences.settings.getBoolean("ShowGettingStartDialog", true);
    }

    public static void setThemePreference (int choice) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("ThemePreference", choice);
        editor.commit();
    }

    public static void setGettingStartDialog (boolean b) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("ShowGettingStartDialog", b);
        editor.commit();
    }
}
