package com.example.mycards.controller.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class UIConfig {
    public static int getNightMode(@NonNull Context context) {
        int nightModeFlags = AppCompatDelegate.getDefaultNightMode();
        switch (nightModeFlags) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                return 1;
            case AppCompatDelegate.MODE_NIGHT_NO:
                return 0;
            default:
                return 2;
        }
    }
    @NonNull
    public static TypedValue getResAttr(@NonNull Context context, int attrName) {
        TypedValue a = new TypedValue();
        context.getTheme().resolveAttribute(attrName, a, true);
        return a;
    }
    public static void setStatusBarColor(@NonNull Window window, int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (color != -1)
            window.setStatusBarColor(color);
    }
    public static int getBGColor(Context context) {
        TypedValue a = getResAttr(context, android.R.attr.windowBackground);
        if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT)
            return a.data;
        return 666;
    }
    public static void setWindowLightStatusBar(View view, boolean bool) {
        if (bool)
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        else
            view.setSystemUiVisibility(0);
    }
    public static void setNightMode(int isNightMode) {
        if (isNightMode == 1)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else if (isNightMode == 0)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if (isNightMode == 2)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        UserPreferences.setThemePreference(isNightMode);
    }
    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
