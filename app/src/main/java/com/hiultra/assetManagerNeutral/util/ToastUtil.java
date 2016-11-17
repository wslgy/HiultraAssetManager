package com.hiultra.assetManagerNeutral.util;

import android.widget.Toast;

import com.hiultra.assetManagerNeutral.global.App;

public class ToastUtil {

    private static Toast mToast;

    public static void show(final String msg) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(msg);
            }
        });
    }
    
    public static void show(final int resId) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(resId);
            }
        });
        // show(msg);
    }

    private static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
    
    private static void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

}
