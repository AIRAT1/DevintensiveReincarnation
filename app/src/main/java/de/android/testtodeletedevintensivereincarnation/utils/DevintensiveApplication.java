package de.android.testtodeletedevintensivereincarnation.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DevintensiveApplication extends Application {
    private static Context context;
    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static Context getContext() {
        return context;
    }
}
