package com.hiultra.assetManagerNeutral.dao;

import java.util.ArrayList;

import android.text.TextUtils;

import com.hiultra.assetManagerNeutral.dao.table.DepartmentInfo;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgument;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;

/**
 * 数据缓存</br> 内存中存取数据总是比硬盘快些,您说呢?
 * 
 * @author Tom
 * @date 2016年11月2日
 * @Time 下午7:09:05
 */
public class DaoCache {
    
    private DaoCache() {}
    
    private static ArrayList<DepartmentInfo> departmentInfos = new ArrayList<>();
    private static ArrayList<MaterialModelInfo> materialModelInfos = new ArrayList<>();
    private static ArrayList<UserInfo> userInfos = new ArrayList<>();
    private static ArrayList<WarehouseInfo> warehouseInfos = new ArrayList<>();
    private static ArrayList<SystemArgument> handleTypes = new ArrayList<>();
    
    public static DepartmentInfo getDepartinfo(String code) {
        if(TextUtils.isEmpty(code)) return null;
        getDepartmentInfos();
        if (departmentInfos == null || departmentInfos.isEmpty()) return null;
        for (DepartmentInfo info : departmentInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info;
        }
        return null;
    }
    
    public static String getDepartName(String code) {
        if(TextUtils.isEmpty(code)) return new String("null");
        if (departmentInfos.isEmpty()) {
            departmentInfos = (ArrayList<DepartmentInfo>) DBTools.findAll(DepartmentInfo.class);
        }
        if (departmentInfos == null || departmentInfos.isEmpty()) return new String("null");
        for (DepartmentInfo info : departmentInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info.getName();
        }
        return new String("null");
    }
    
    public static ArrayList<DepartmentInfo> getDepartmentInfos() {
        if (departmentInfos.isEmpty()) {
            departmentInfos = (ArrayList<DepartmentInfo>) DBTools.findAll(DepartmentInfo.class);
        }
        return departmentInfos;
    }
    
    public static MaterialModelInfo getModelinfo(String code) {
        if(TextUtils.isEmpty(code)) return null;
        getModelInfos();
        if (materialModelInfos == null || materialModelInfos.isEmpty()) return null;
        for (MaterialModelInfo info : materialModelInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info;
        }
        return null;
    }
    
    public static String getModelName(String code) {
        if(TextUtils.isEmpty(code)) return new String("null");
        if (materialModelInfos.isEmpty()) {
            materialModelInfos = (ArrayList<MaterialModelInfo>) DBTools.findAll(MaterialModelInfo.class);
        }
        if (materialModelInfos == null || materialModelInfos.isEmpty()) return new String("null");
        for (MaterialModelInfo info : materialModelInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info.getName();
        }
        return new String("null");
    }
    
    public static ArrayList<MaterialModelInfo> getModelInfos() {
        if (materialModelInfos.isEmpty()) {
            materialModelInfos = (ArrayList<MaterialModelInfo>) DBTools.findAll(MaterialModelInfo.class);
        }
        return materialModelInfos;
    }
    
    public static UserInfo getUserinfo(String code) {
        if(TextUtils.isEmpty(code)) return null;
        getUserInfos();
        if (userInfos == null || userInfos.isEmpty()) return null;
        for (UserInfo info : userInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info;
        }
        return null;
    }
    
    public static String getUserName(String code) {
        if(TextUtils.isEmpty(code)) return new String("null");
        if (userInfos.isEmpty()) {
            userInfos = (ArrayList<UserInfo>) DBTools.findAll(UserInfo.class);
        }
        if (userInfos == null || userInfos.isEmpty()) return new String("null");
        for (UserInfo info : userInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info.getName();
        }
        return new String("null");
    }
    
    public static ArrayList<UserInfo> getUserInfos() {
        if (userInfos.isEmpty()) {
            userInfos = (ArrayList<UserInfo>) DBTools.findAll(UserInfo.class);
        }
        return userInfos;
    }
    
    public static WarehouseInfo getWarehouseInfo(String code) {
        if(TextUtils.isEmpty(code)) return null;
        getWarehouseInfos();
        if (warehouseInfos == null || warehouseInfos.isEmpty()) return null;
        for (WarehouseInfo info : warehouseInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info;
        }
        return null;
    }
    
    public static String getWarehouseName(String code) {
        if(TextUtils.isEmpty(code)) return new String("null");
        if (warehouseInfos.isEmpty()) {
            warehouseInfos = (ArrayList<WarehouseInfo>) DBTools.findAll(WarehouseInfo.class);
        }
        if (warehouseInfos == null || warehouseInfos.isEmpty()) return new String("null");
        for (WarehouseInfo info : warehouseInfos) {
            if (TextUtils.equals(info.getCode(), code)) return info.getName();
        }
        return new String("null");
    }
    
    public static ArrayList<WarehouseInfo> getWarehouseInfos() {
        if (warehouseInfos.isEmpty()) {
            warehouseInfos = (ArrayList<WarehouseInfo>) DBTools.findAll(WarehouseInfo.class);
        }
        return warehouseInfos;
    }
    
    /**
     * 清空缓存
     */
    public static void cleanCache() {
        if (departmentInfos != null && !departmentInfos.isEmpty()) departmentInfos.clear();
        if (materialModelInfos != null && !materialModelInfos.isEmpty()) materialModelInfos.clear();
        if (userInfos != null && !userInfos.isEmpty()) userInfos.clear();
        if (warehouseInfos != null && !warehouseInfos.isEmpty()) warehouseInfos.clear();
    }
}
