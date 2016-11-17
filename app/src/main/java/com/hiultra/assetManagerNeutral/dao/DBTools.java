package com.hiultra.assetManagerNeutral.dao;

import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo02;
import com.hiultra.assetManagerNeutral.dao.table.DepartmentInfo;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.dao.table.ProviderInfo;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgument;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgumentType;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;
import com.hiultra.assetManagerNeutral.global.App;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 数据库工具类
 * 
 * @author Tom
 * @date 2016年7月22日
 * @Time 上午10:29:08
 */
public class DBTools {

    private static DbUtils db;

    private static final String DBNAME = "AssetManagerNeutral.DB";

    private static final int DBVERSION = 10;

    private static String dbDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hiultra";

    private static boolean allowTransaction = true;

    static {
        if (db == null) {
            DaoConfig config = new DaoConfig(App.getContext());
            config.setDbDir(dbDir);
            config.setDbName(DBNAME);
            config.setDbVersion(DBVERSION);
            db = DbUtils.create(config);
        }
        configAllowTransaction(allowTransaction);
    }

    public static void createTable() throws DbException {
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(AssetMaterialInfo.class);
        list.add(DepartmentInfo.class);
        list.add(MaterialModelInfo.class);
        list.add(ProviderInfo.class);
        list.add(UserInfo.class);
        list.add(SystemArgument.class);
        list.add(SystemArgumentType.class);
        list.add(WarehouseInfo.class);
        list.add(Asset_CheckInfo01.class);
        list.add(Asset_CheckInfo02.class);
        list.add(Asset_HandleInfo01.class);
        list.add(Asset_HandleInfo02.class);
        list.add(Asset_InspectionInfo01.class);
        list.add(Asset_InspectionInfo02.class);
        list.add(Asset_RepairInfo01.class);
        list.add(Asset_RepairInfo02.class);
        list.add(Asset_StopInfo01.class);
        list.add(Asset_StopInfo02.class);
        list.add(Asset_AllocateInfo01.class);
        list.add(Asset_AllocateInfo02.class);
        list.add(Asset_ScrapInfo01.class);
        list.add(Asset_ScrapInfo02.class);
        createTable(list);
    }

    public static void configAllowTransaction(boolean flag) {
        if (db != null)
            db.configAllowTransaction(flag);
    }

    public static void createTable(Class<?> clazz) throws DbException {
        db.createTableIfNotExist(clazz);
    }

    public static void createTable(List<Class<?>> clazzs) throws DbException {
        for (Class<?> clazz : clazzs) {
            db.createTableIfNotExist(clazz);
        }
    }

    public static void save(Object o) throws DbException {
        db.save(o);
    }

    public static <T> List<T> findAll(Class<T> clazz) {
        try {
            return db.findAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> findAll(Selector selector) {
        try {
            return (ArrayList<T>) db.findAll(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> findAll(Class<T> clazz, String columnName, Object value) {
        try {
            return (ArrayList<T>) db.findAll(Selector.from(clazz).where(columnName, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static <T> T findFirst(Class<T> clazz, String columnName, String value) {
        try {
            return db.findFirst(Selector.from(clazz).where(columnName, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T findFirst(Selector selector) {
        try {
            return db.findFirst(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void update(Object o) throws DbException {
        db.update(o, new String[]{});
    }
    
    public static void update(List<?> list, WhereBuilder builder, String... updateColumnNames) {
        try {
            db.updateAll(list, builder, updateColumnNames);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void update(Object o, WhereBuilder builder, String... updateColumnNames) throws DbException {
        db.update(o, builder, updateColumnNames);
    }
    
    public static void saveOrUpdate(Object o) throws DbException {
        db.saveOrUpdate(o);
    }

    public static void saveOrUpdate(List<?> list) throws DbException {
        db.saveOrUpdateAll(list);
    }
    
    

    public static <T> void delete(Class<T> clazz, WhereBuilder builder) throws DbException {
        db.delete(clazz, builder);
    }

    public static <T> void deleteAll(Class<T> clazz) throws DbException {
        db.deleteAll(clazz);
    }

}