package com.hiultra.assetManagerNeutral.domain;

import android.graphics.drawable.Drawable;

/**
 * DrawerLayout左侧ListView条目填充对象
 * @author Tom
 * @date 2016年10月8日
 * @Time 上午9:59:08
 */
public class StartLvItem {
    
    public Drawable icon;
    
    public String title;
    
    public StartLvItem(Drawable icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
