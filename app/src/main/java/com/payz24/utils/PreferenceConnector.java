package com.payz24.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mahesh on 1/8/2018.
 */
public class PreferenceConnector {

    public static final String PREF_NAME = "Payz24_SharedPreferences";
    public static final int MODE = Context.MODE_PRIVATE;

    /**
     * to write integer into prefernce connector
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    /**
     * to read integer into prefernce connector
     *
     * @param context  context
     * @param key      key
     * @param defValue defValue
     */
    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    /**
     * to write string into prefernce connector
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    /**
     * to read integer into prefernce connector
     *
     * @param context  context
     * @param key      key
     * @param defValue defValue
     */
    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    /**
     * to write integer into prefernce connector
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    /**
     * to read integer into prefernce connector
     *
     * @param context  context
     * @param key      key
     * @param defValue defValue
     */
    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    /**
     * to get shared preferences
     *
     * @param context context
     * @return name and mode of shared preferences
     */
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    /**
     * to get edited value
     *
     * @param context context
     * @return edited shared preference
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    /**
     * to cleasr data in SharedPreferences
     *
     * @param context context
     */
    public static void clearData(Context context) {
        getEditor(context).clear().commit();
    }

}
