package com.hiultra.assetManagerNeutral.UHF;

import android.text.TextUtils;

import com.handset.Common;
import com.hiultra.assetManagerNeutral.global.App;

/**
 * 写卡...这个模块能不开发就尽量不要开发,容易出问题..
 * 
 * @author Tom
 * @date 2016年10月19日
 * @Time 下午2:57:45
 */
public class EpcWriter implements IUhf {
    
    private static String strPwd = "00 00 00 00";
    /** 写卡区块:1代表epc区 */
    private static int bank = 1;
    private static int begin = 2;
    private static int length = 6;
    
    private App app;
    
    private static EpcWriter instance = new EpcWriter();
    
    public static EpcWriter getInstance(App app) {
        return instance.init(app);
    }
    
    /**
     * 写卡
     * @param code
     * @return
     */
    public boolean write(String code) {
        byte[] password = new byte[4];
        Common.hexStr2Bytes(strPwd, password, 0, 4);
        byte[] writeData = new byte[length * 2];
        
        if (code.length() > length * 2) {
            throw new IllegalArgumentException("编码长度过长,最长为12位...");
        }
        byte[] writeTemp = code.getBytes();
        com.handset.Common.memcpy(writeData, writeTemp, 0, 0, writeTemp.length);
        if (app.device.writeUhfTag(bank, begin, length, password, writeData)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读卡
     * @return
     */
    public String read() {
        byte[] password = new byte[4];
        Common.hexStr2Bytes(strPwd, password, 0, 4);
        String s = app.device.readUhfTag(bank, begin, length,
                password, false);
        if (TextUtils.isEmpty(s) || TextUtils.equals(s, "80") || s.trim().length() == 0) {
            return null;
        } else {
            return s.trim();
        }
    }
    
    public EpcWriter init(App app) {
        this.app = app;
        return this;
    }

    @Override
    public App getApp() {
        return app;
    }
    
}
