package com.hiultra.assetManagerNeutral.domain;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.hiultra.assetManagerNeutral.MVP_M.DownloadModel;
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
import com.hiultra.assetManagerNeutral.dao.table.BaseTable;
import com.hiultra.assetManagerNeutral.dao.table.DepartmentInfo;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.dao.table.ProviderInfo;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgument;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgumentType;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.JsonUtil;
import com.hiultra.assetManagerNeutral.util.SpUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.minttown.hiultra.R;

/**
 * 资产下载信息
 * 
 * @author Tom
 * @date 2016年10月31日
 * @Time 下午9:25:40
 */
public class DownloadInfo {
    
    
    public ItemInfo itemInfo;
    public int state;
    public int max;
    public int progress;
    public ArrayList<Class<? extends BaseTable>> tableList;
//    public int page;
    public String lastDate;
    
    public static DownloadInfo create(ItemInfo itemInfo) {
        if(itemInfo == null) throw new IllegalArgumentException(Util.getString(R.string.exception_null_widgetInfo));
        DownloadInfo downloadInfo = null;
        int tag = itemInfo.getTag();
        switch (tag) {
            case Constants.TAG_BASE:
                downloadInfo = createBase(tag);
                break;
            case Constants.TAG_CHECK:
                downloadInfo = createCheck(tag);
                break;
            case Constants.TAG_HANDLE:
                downloadInfo = createHandle(tag);
                break;
            case Constants.TAG_INSPECTION:
                downloadInfo = createInspection(tag);
                break;
            case Constants.TAG_REPAIR:
                downloadInfo = createRepair(tag);
                break;
            case Constants.TAG_SCRAP:
                downloadInfo = createScrap(tag);
                break;
            case Constants.TAG_STOP:
                downloadInfo = createStop(tag);
                break;
            case Constants.TAG_TRANSFER:
                downloadInfo = createTransfer(tag);
                break;
        }
        downloadInfo.setState(DownloadModel.STATE_NONE);
        downloadInfo.setItemInfo(itemInfo);
//        downloadInfo.setPage(1);
        return downloadInfo;
    }
    
    private static DownloadInfo createBase(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(AssetMaterialInfo.class);
        list.add(DepartmentInfo.class);
        list.add(MaterialModelInfo.class);
        list.add(ProviderInfo.class);
        list.add(UserInfo.class);
        list.add(WarehouseInfo.class);
        list.add(SystemArgument.class);
//        list.add(SystemArgumentType.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createCheck(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_CheckInfo01.class);
        list.add(Asset_CheckInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createHandle(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_HandleInfo01.class);
        list.add(Asset_HandleInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createInspection(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_InspectionInfo01.class);
        list.add(Asset_InspectionInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createRepair(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_RepairInfo01.class);
        list.add(Asset_RepairInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createStop(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_StopInfo01.class);
        list.add(Asset_StopInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createTransfer(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_AllocateInfo01.class);
        list.add(Asset_AllocateInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static DownloadInfo createScrap(int tag) {
        DownloadInfo info = new DownloadInfo();
        info.setLastDate(SpUtil.getString(String.valueOf(tag), "2012-01-01"));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_ScrapInfo01.class);
        list.add(Asset_ScrapInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    @SuppressWarnings("unchecked")
    public List<? extends BaseTable> parseJson(String json, Class<?> clazz) {
        List<? extends BaseTable> list = null;
        switch (clazz.getSimpleName()) {
            case "AssetMaterialInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<AssetMaterialInfo>>() {}.getType());
                break;
            case "DepartmentInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<DepartmentInfo>>() {}.getType());
                break;
            case "MaterialModelInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<MaterialModelInfo>>() {}.getType());
                break;
            case "ProviderInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<ProviderInfo>>() {}.getType());
                break;
            case "UserInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<UserInfo>>() {}.getType());
                break;
            case "WarehouseInfo":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<WarehouseInfo>>() {}.getType());
                break;
            case "SystemArgument":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<SystemArgument>>() {}.getType());
                break;
            case "Asset_CheckInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_CheckInfo01>>() {}.getType());
                break;
            case "Asset_CheckInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_CheckInfo02>>() {}.getType());
                break;
            case "Asset_HandleInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_HandleInfo01>>() {}.getType());
                break;
            case "Asset_HandleInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_HandleInfo02>>() {}.getType());
                break;
            case "Asset_InspectionInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_InspectionInfo01>>() {}.getType());
                break;
            case "Asset_InspectionInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_InspectionInfo02>>() {}.getType());
                break;
            case "Asset_RepairInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_RepairInfo01>>() {}.getType());
                break;
            case "Asset_RepairInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_RepairInfo02>>() {}.getType());
                break;
            case "Asset_StopInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_StopInfo01>>() {}.getType());
                break;
            case "Asset_StopInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_StopInfo02>>() {}.getType());
                break;
            case "Asset_AllocateInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_AllocateInfo01>>() {}.getType());
                break;
            case "Asset_AllocateInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_AllocateInfo02>>() {}.getType());
                break;
            case "Asset_ScrapInfo01":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_ScrapInfo01>>() {}.getType());
                break;
            case "Asset_ScrapInfo02":
                list = (List<? extends BaseTable>) JsonUtil.parseJsonToList(json,
                        new TypeToken<List<Asset_ScrapInfo02>>() {}.getType());
                break;
        }
        return list;
    }
    
    /**
     * 存储控件的实体类,用来创建{@link DownloadInfo}</br> 实在没能找到更好的办法了
     * 
     * @author Tom
     * @date 2016年11月2日
     * @Time 上午9:34:22
     */
    public static class ItemInfo {
        
        private String title;
        private int tag;
        
        public ItemInfo(int tag, String title) {
            super();
            this.tag = tag;
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTag() {
            return tag;
        }
        
        public void setTag(int tag) {
            this.tag = tag;
        }
        
    }
    
    public void updateLastDate(String date) {
        SpUtil.putString(String.valueOf(itemInfo.getTag()), date);
    }
    
    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo widgetInfo) {
        this.itemInfo = widgetInfo;
    }

    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public ArrayList<Class<? extends BaseTable>> getTableList() {
        return tableList;
    }
    
    public void setTableList(ArrayList<Class<? extends BaseTable>> tableList) {
        this.tableList = tableList;
    }
    
    public String getLastDate() {
        return lastDate;
    }
    
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
    
    public int getTag() {
        return itemInfo.getTag();
    }
    
    public void setTag(int tag) {
        this.itemInfo.setTag(tag);
    }
    
    public int getMax() {
        return max;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getProgress() {
        return progress;
    }
    
    public void setProgress(int progress) {
        this.progress = progress;
    }
    
}
