package com.hiultra.assetManagerNeutral.domain;

import android.graphics.drawable.Drawable;

/**
 * 资产管理模块Gridview条目对象
 * @author Tom
 * @date 2016年10月31日
 * @Time 上午11:06:20
 */
public class AssetGvItem {
    
    public Drawable icon;
    
    public String title;
    
    public AssetGvItem(Drawable icon, String title) {
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
