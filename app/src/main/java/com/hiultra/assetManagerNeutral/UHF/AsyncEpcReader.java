package com.hiultra.assetManagerNeutral.UHF;

import com.hiultra.assetManagerNeutral.global.App;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.hiultra.assetManagerNeutral.util.Util;

/**
 * 异步寻卡类
 * @author Tom
 * @date 2016年11月9日
 * @Time 上午11:26:00
 */
public class AsyncEpcReader extends Thread {
    
    private EpcWriter writer;
    private EpcReadObserver observer;
    
    public AsyncEpcReader(App app, EpcReadObserver observer) {
        writer = EpcWriter.getInstance(app);
        this.observer = observer;
    }
    
    @Override
    public void run() {
        super.run();
        begin();
        String epc = writer.read();
        if (epc == null) onReadFailed();
        else onReadSuccess(epc);
    }
    
    public interface EpcReadObserver {
        void begin();
        
        void onReadFailed();
        
        void onReadSuccess(String epc);
    }
    
    public void begin() {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                observer.begin();
            }
        });
    }
    
    public void onReadFailed() {
        DevBeep.PlayErr();
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                observer.onReadFailed();
            }
        });
    }
    
    public void onReadSuccess(final String epc) {
        DevBeep.PlayOK();
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                observer.onReadSuccess(epc);
            }
        });
    }
}
