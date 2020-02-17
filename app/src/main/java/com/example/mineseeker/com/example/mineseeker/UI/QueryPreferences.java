package com.example.mineseeker.com.example.mineseeker.UI;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

/**
 * An interface for reading and writting to the shared preference filee
 */
public class QueryPreferences {

    public static int getStoredQuery(Context context, String query) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(query, 0);
    }

    public static void setStoredQuery(Context context,String query, int data) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(query, data)
                .apply();
    }
}
