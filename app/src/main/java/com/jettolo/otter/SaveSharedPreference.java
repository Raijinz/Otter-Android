package com.jettolo.otter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String PREF_USER_NAME = "username";
    private static final String PREF_AUTH_TOKEN = "token";
    private static final String PREF_FCM_TOKEN = "fcm";
    private static final String PREF_KEY = "key";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setPrefUserName(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, username);
        editor.apply();
    }

    public static String getPrefUserName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }

    public static void setPrefAuthToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_AUTH_TOKEN, token);
        editor.apply();
    }

    public static String getPrefAuthToken(Context context) {
        return getSharedPreferences(context).getString(PREF_AUTH_TOKEN, "");
    }

    public static void setPrefKey(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY, key);
        editor.apply();
    }

    public static String getPrefKey(Context context) {
        return getSharedPreferences(context).getString(PREF_KEY, "");
    }

    public static void setPrefFcmToken(Context context, String fcm) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_FCM_TOKEN, fcm);
        editor.apply();
    }

    public static String getPrefFcmToken(Context context) {
        return getSharedPreferences(context).getString(PREF_FCM_TOKEN, "");
    }
}
