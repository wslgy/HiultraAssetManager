package com.hiultra.assetManagerNeutral.global;

import android.os.Environment;

/**
 * 全局常量
 * @author Tom
 * @date 2016年9月2日
 * @Time 下午3:57:09
 */
public interface Constants {

    /* SP  key */
    String SP_LOGINNAME = "loginName";
    String SP_PASSWORD = "passWord";
    String SP_REMEMBER = "remember";
    String SP_IP = "ip";
    String SP_PORT = "port";
//    String SP_LAST_BASE = "last_base";
//    String SP_LAST_INSPECTION = "last_inspection";
//    String SP_LAST_TRANSFER = "last_transfer";
//    String SP_LAST_BASE = "last_base";
//    String SP_LAST_BASE = "last_base";
//    String SP_LAST_BASE = "last_base";
//    String SP_LAST_BASE = "last_base";
//    String SP_LAST_BASE = "last_base";
    /*  */
    
    
    /* 区分不同模块的Tag */
    int TAG_BASE = 1001;
    int TAG_INSPECTION = 1002;
    int TAG_TRANSFER = 1003;
    int TAG_REPAIR = 1004;
    int TAG_STOP = 1005;
    int TAG_SCRAP = 1006;
    int TAG_HANDLE = 1007;
    int TAG_CHECK = 1008;
    
    /* UHF */
    int DEFAULT_POWER_WRITE = 8; // 默认发卡功率(实际使用需要*10)
    int DEFAULT_TIME_WORKING = 200; // 默认工作时间(ms)
    int DEFAULT_TIME_SLEEP = 300; // 默认休眠时间(ms)
    
    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hiultra/picture";
    
    
}
