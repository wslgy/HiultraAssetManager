package com.hiultra.assetManagerNeutral.domain;

import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 存储控件的实体类,用来创建{@link DownloadInfo}</br> 实在没能找到更好的办法了
 * 
 * @author Tom
 * @date 2016年11月2日
 * @Time 上午9:34:22
 */
public class WidgetInfo {
    
    private ProgressBar pbH;
    private ProgressBar pbC;
    private ImageView iv;
    private int tag;
    
    
    
    public ProgressBar getPbH() {
        return pbH;
    }
    
    public void setPbH(ProgressBar pbH) {
        this.pbH = pbH;
    }
    
    public ProgressBar getPbC() {
        return pbC;
    }
    
    public void setPbC(ProgressBar pbC) {
        this.pbC = pbC;
    }
    
    public ImageView getIv() {
        return iv;
    }
    
    public void setIv(ImageView iv) {
        this.iv = iv;
    }
    
    public int getTag() {
        return tag;
    }
    
    public void setTag(int tag) {
        this.tag = tag;
    }
    
}
