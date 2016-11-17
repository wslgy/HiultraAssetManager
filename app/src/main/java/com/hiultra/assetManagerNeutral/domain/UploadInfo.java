package com.hiultra.assetManagerNeutral.domain;

import java.util.ArrayList;

import com.hiultra.assetManagerNeutral.MVP_M.UploadModel;
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
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.Util;
import com.minttown.hiultra.R;

/**
 * 上传信息实体类
 * 
 * @author Tom
 * @date 2016年11月4日
 * @Time 下午3:25:50
 */
public class UploadInfo {
    
    public String title;
    public int tag;
    public int state;
    public ArrayList<Class<? extends BaseTable>> tableList;
    
    
    
    public static UploadInfo create(int tag) {
        UploadInfo info = null;
        switch (tag) {
            case Constants.TAG_BASE:
                info = createBase(tag);
                break;
            case Constants.TAG_CHECK:
                info = createCheck(tag);
                break;
            case Constants.TAG_HANDLE:
                info = createHandle(tag);
                break;
            case Constants.TAG_INSPECTION:
                info = createInspection(tag);
                break;
            case Constants.TAG_REPAIR:
                info = createRepair(tag);
                break;
            case Constants.TAG_SCRAP:
                info = createScrap(tag);
                break;
            case Constants.TAG_STOP:
                info = createStop(tag);
                break;
            case Constants.TAG_TRANSFER:
                info = createTransfer(tag);
                break;
        }
        info.setTag(tag);
        info.setState(UploadModel.STATE_NONE);
        return info;
    }
    
    private static UploadInfo createBase(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_base));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(AssetMaterialInfo.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createCheck(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_check));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_CheckInfo01.class);
        list.add(Asset_CheckInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createHandle(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_handle));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_HandleInfo01.class);
        list.add(Asset_HandleInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createInspection(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_inspection));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_InspectionInfo01.class);
        list.add(Asset_InspectionInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createRepair(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_repair));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_RepairInfo01.class);
        list.add(Asset_RepairInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createScrap(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_scrap));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_ScrapInfo01.class);
        list.add(Asset_ScrapInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createStop(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_stop));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_StopInfo01.class);
        list.add(Asset_StopInfo02.class);
        info.setTableList(list);
        return info;
    }
    
    private static UploadInfo createTransfer(int tag) {
        UploadInfo info = new UploadInfo();
        info.setTitle(Util.getString(R.string.title_transfer));
        ArrayList<Class<? extends BaseTable>> list = new ArrayList<>();
        list.add(Asset_AllocateInfo01.class);
        list.add(Asset_AllocateInfo02.class);
        info.setTableList(list);
        return info;
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
    
}
