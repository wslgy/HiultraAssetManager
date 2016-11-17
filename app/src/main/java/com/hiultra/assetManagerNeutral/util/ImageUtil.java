package com.hiultra.assetManagerNeutral.util;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtil {
    
    private ImageUtil(){}
    
    public static void show(Context c, String path, ImageView iv) {
        Picasso.with(c).load(path).into(iv);
    }
    
    public static void show(Context c, File file, ImageView iv) {
        Picasso.with(c).load(file).into(iv);
    }
    
    public static void show(Context c, Uri uri, ImageView iv) {
        Picasso.with(c).load(uri).into(iv);
    }
    
    public static void show(Context c, int resId, ImageView iv) {
        Picasso.with(c).load(resId).into(iv);
    }
    
}
