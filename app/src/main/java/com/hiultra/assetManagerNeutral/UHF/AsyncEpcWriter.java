package com.hiultra.assetManagerNeutral.UHF;

import java.util.ArrayList;

import android.os.SystemClock;
import android.text.TextUtils;

import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.global.App;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.minttown.hiultra.R;

/**
 * 异步写卡类,这个类项目耦合度较高<br>
 * 如果其他项目需要调用1600设备写卡功能,可以考虑移植{@link EpcWriter}类
 * 
 * @author Tom
 * @date 2016年10月19日
 * @Time 下午4:07:37
 */
public class AsyncEpcWriter extends Thread {
    
    private EpcWriter epcWriter;
    
    private AssetMaterialInfo assetInfo;
    private ArrayList<AssetMaterialInfo> baseList;
    private EpcWriterObserver observer;
    
    public AsyncEpcWriter(App app, AssetMaterialInfo assetInfo,  ArrayList<AssetMaterialInfo> baseList, EpcWriterObserver observer) {
        epcWriter = EpcWriter.getInstance(app);
        this.assetInfo = assetInfo;
        this.baseList = baseList;
        this.observer = observer;
        begin();
    }
    
    @Override
    public void run() {
        super.run();
        // 1, 读卡,
        String epc = epcWriter.read();
        if(epc == null) {
            onBindingFailed(null);
            return;
        }
        AssetMaterialInfo a = null;
        for (AssetMaterialInfo info : baseList) {
            if(TextUtils.equals(info.getBarCode(), epc)) {
                a = info;
                break;
            }
        }
        if(a != null) {
            onBindingFailed(a);
            return;
        }
        // 3,写标签
        SystemClock.sleep(150);
        String code = assetInfo.getCode();
        boolean b = epcWriter.write(code);
        if(!b) {
            onBindingFailed(null);
            return;
        }
        SystemClock.sleep(150);
        String epc2 = epcWriter.read();
        if(!TextUtils.equals(epc2, code)) {
            onBindingFailed(null);
            return;
        }
        // 4,保存数据库
        assetInfo.setBarCode(code);
        assetInfo.setIfuodate("0");
        try {
            DBTools.update(assetInfo);
            onBindingSuccess();
        } catch (DbException e) {
            onBindingFailed(null);
            e.printStackTrace();
        }
    }
    
    public interface EpcWriterObserver {
        void begin();
        void onBindingFailed(AssetMaterialInfo a);
        void onBindingSuccess();
    }
    
    private void begin() {
        if (observer != null) {
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    observer.begin();
                }
            });
        }
    }
    
    private void onBindingFailed(final AssetMaterialInfo a) {
        DevBeep.PlayErr();
        if(observer != null) {
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    observer.onBindingFailed(a);
                }
            });
        }
    }
    
    private void onBindingSuccess() {
        DevBeep.PlayOK();
        if (observer != null) {
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    observer.onBindingSuccess();
                }
            });
        }
    }
    
}
