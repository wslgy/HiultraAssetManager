package com.hiultra.assetManagerNeutral.UHF;

import java.util.ArrayList;

import android.os.SystemClock;

import com.handset.pakage.UHFTag;
import com.hiultra.assetManagerNeutral.global.App;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.lidroid.xutils.util.LogUtils;

/**
 * 异步连续寻卡模块
 * 
 * @author Tom
 * @date 2016年9月2日
 * @Time 下午5:34:57
 */
public class EpcReader implements Runnable, IUhf {
    
    private boolean scanFlag = true;
    private App app;
    private static EpcReader instance = new EpcReader();
    /** 是否十六进制,默认false */
    private boolean inventoryHex = false;
    /** 最小长度标准 */
    private static final int MIN_LENGHT = 12;
    
    private ArrayList<EpcReadObserver> observers = new ArrayList<>();
    
    /**
     * 读取epc标签的监听器
     * 
     * @author Tom
     * @date 2016年9月21日
     * @Time 下午3:48:55
     */
    public interface EpcReadObserver {
        
        /**
         * 获得模块读取到的epc数据
         * 
         * @param epc
         */
        void onEpcRead(String epc);
        
        /**
         * 获取当前监听器对象</br> 子类可根据自身需求,在此方法中执行数据保存等逻辑
         * 
         * @return
         */
        EpcReadObserver getCurrentObserver();
        
    }
    
    private EpcReader() {}
    
    private EpcReader(App app) {
        init(app);
    }
    
    public void init(App app) {
        this.app = app;
        DevBeep.init(app);
    }
    
    public static synchronized EpcReader getInstance(App app) {
        instance.init(app);
        return instance;
    }
    
    @Override
    public void run() {
        while (scanFlag) {
            SystemClock.sleep(200);
            int cnt = app.device.getSize();
            for (int i = 0; i < cnt; i++) {
                UHFTag uhfTag = app.device.getItem();
//                String epc = uhfTag.getEpcHexWithoutPc();
                String epc = uhfTag.getEpc(inventoryHex);
                /* 过滤筛选 */
                if (epc == null) continue;
                epc = epc.trim();
                if (epc.length() < MIN_LENGHT) continue;
                /* --- */
                epc = epc.length() == MIN_LENGHT ? epc : epc.substring(0, MIN_LENGHT);
                onEpcRead(epc);
            }
            if (cnt > 0) DevBeep.PlayOK();
        }
    }
    
    /** 注册监听器 */
    public void registerObserver(EpcReadObserver observer) {
        if (observers != null && !observers.contains(observer)) observers.add(observer);
    }
    
    /** 解除注册监听器 */
    public void unregisterObserver(EpcReadObserver observer) {
        if (observers != null && observers.contains(observer)) observers.remove(observer);
    }
    
    /** 清空监听器 */
    public void clearObserver() {
        if (observers != null && !observers.isEmpty()) {
            // 解除注册之前通知所有监听器,处理并保存数据
            for (EpcReadObserver observer : observers) {
                observer.getCurrentObserver();
                unregisterObserver(observer);
            }
        }
    }
    
    public void onEpcRead(String epc) {
        for (EpcReadObserver observer : observers) {
            observer.onEpcRead(epc);
//            LogUtils.e(observer.getClass().getSimpleName() + " epc : " + epc);
        }
    }
    
    /**
     * 是否扫描
     * 
     * @param scanFlag
     * @return
     */
    public EpcReader setFlag(boolean scanFlag) {
        this.scanFlag = scanFlag;
        return this;
    }
    
    /**
     * 是否十六进制
     * 
     * @param inventoryHex
     * @return
     */
    public EpcReader setInventoryHex(boolean inventoryHex) {
        this.inventoryHex = inventoryHex;
        return this;
    }

    @Override
    public App getApp() {
        return app;
    }
    
}
