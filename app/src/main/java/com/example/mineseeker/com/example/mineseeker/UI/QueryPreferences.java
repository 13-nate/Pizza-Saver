package com.example.mineseeker.com.example.mineseeker.UI;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

/**
 * An interface for reading and writting to the shared preference filee
 */
public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public  static  void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

}
