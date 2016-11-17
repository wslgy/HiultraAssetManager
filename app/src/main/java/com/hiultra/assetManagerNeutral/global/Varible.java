package com.hiultra.assetManagerNeutral.global;

import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.util.SpUtil;

/**
 * 全局变量
 * 
 * @author Tom
 * @date 2016年10月31日
 * @Time 下午7:15:44
 */
public class Varible {
    
    public static UserInfo userInfo;
    
    public static String ip;
    public static String port;
    
    public static void cache() {
        ip = SpUtil.getString(Constants.SP_IP, new String(""));
        port = SpUtil.getString(Constants.SP_PORT, new String(""));
    }
}
