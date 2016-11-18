package com.hiultra.assetManagerNeutral.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hiultra.assetManagerNeutral.global.App;
import com.lidroid.xutils.util.LogUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 常用工具类
 * 
 * @author Tom
 *
 */
public class Util {
    
    public static void runOnUiThread(Runnable r) {
        App.getHandler().post(r);
    }
    
    public static void runOnUiThreadDelay(Runnable r, long delay) {
        App.getHandler().postDelayed(r, delay);
    }
    
    /**
     * 获取字符串资源
     * 
     * @param resId
     *            资源id
     * @return 资源id对应的字符串
     */
    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }
    
    /**
     * 获取字符串数组资源
     * 
     * @param resId
     *            资源id
     * @return 资源id对应的字符串数组
     */
    public static String[] getStringArray(int resId) {
        return App.getContext().getResources().getStringArray(resId);
    }
    
    // FIXME 不可用
    private static TypedArray obtainTypedArray(int resId) {
        return App.getContext().getResources().obtainTypedArray(resId);
    }
    
    public static ArrayList<String> getStringList(int resId) {
        String[] a = getStringArray(resId);
        ArrayList<String> l = new ArrayList<>();
        for (String s : a) {
            l.add(s);
        }
        return l;
    }
    
    /**
     * 获取Drawable资源
     * 
     * @param resId
     *            资源id
     * @return 资源id对应的Drawable资源
     */
    public static Drawable getDrawable(int resId) {
        return App.getContext().getResources().getDrawable(resId);
    }
    
    /**
     * 获取颜色资源
     * 
     * @param resId
     *            资源id
     * @return 资源id对应的颜色资源
     */
    public static int getColor(int resId) {
        return App.getContext().getResources().getColor(resId);
    }
    
    /**
     * 从dimens.xml文件中获取dp资源,并将dp资源自动转换为px返回
     * 
     * @param resId
     *            资源id
     * @return 资源id对应的px值
     */
    public static int getDimens(int resId) {
        return App.getContext().getResources().getDimensionPixelSize(resId);
    }
    
    /**
     * 判断当前网络是否可用
     * 
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) return mNetworkInfo.isAvailable();
        return false;
    }
    
    public static int parseStr2int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static int createId() {
        int i = (int)(Math.random()*1000000);
        LogUtils.e("ID : " + i);
        while(i <= 0) {
            i = (int)(Math.random()*1000000);
        }
        return i;
    }
    
    public static String getBatchNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date(System.currentTimeMillis()));
    }
    
    private static String parseInt(int i) {
        if (i <= 0 || i >= 10000) {
            throw new IllegalArgumentException("单据号错误");
        }
        if (i < 10) return new String("000" + i);
        else if (i >= 10 && i < 100) return new String("00" + i);
        else if (i >= 100 && i < 1000) return new String("0" + i);
        else return new String("" + i);
    }
}
