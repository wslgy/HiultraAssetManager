package com.hiultra.assetManagerNeutral.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hiultra.assetManagerNeutral.global.App;

/**
 * SharedPreferences工具类
 * @author Tom
 *
 */
public class SpUtil {
    private static SharedPreferences mSp;

    private static SharedPreferences getSharedPref() {
        if (mSp == null) {
            mSp = App.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSp;
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPref().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPref().getBoolean(key, defValue);
    }
    
    public static void putString(String key, String value) {
        getSharedPref().edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return getSharedPref().getString(key, defValue);
    }
    
    public static void removeString(String key) {
        getSharedPref().edit().remove(key).commit();
    }
    
    public static void putInt(String key, int value) {
        getSharedPref().edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return getSharedPref().getInt(key, defValue);
    }
}
