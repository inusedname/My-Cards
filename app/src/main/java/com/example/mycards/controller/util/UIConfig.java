package com.example.mycards.controller.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class UIConfig {
    public static int isNightMode (@NonNull Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                return 1;
            case Configuration.UI_MODE_NIGHT_NO:
                return 0;
            default:
                return -1;
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
        return -1;
    }
    public static void setWindowLightStatusBar(View view, boolean bool) {
        if (bool)
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        else
            view.setSystemUiVisibility(0);
    }
}
