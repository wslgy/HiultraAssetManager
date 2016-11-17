package com.hiultra.assetManagerNeutral.MVP_M;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.hiultra.assetManagerNeutral.dao.DBTools;
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
import com.hiultra.assetManagerNeutral.dao.table.BatchTable;
import com.hiultra.assetManagerNeutral.domain.UploadInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.manager.ThreadPoolManager;
import com.hiultra.assetManagerNeutral.util.JsonUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.hiultra.assetManagerNeutral.web.WebController;
import com.hiultra.assetManagerNeutral.web.WebUtil;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

public class UploadModel implements IModel {
    
    public static final int STATE_NONE = 0;// 初始状态
    public static final int STATE_WAITTING = 1;// 等待状态
    public static final int STATE_UPLOADING = 2;// 上传状态
    public static final int STATE_FINISH = 3;// 上传完成状态
    public static final int STATE_ERROR = 4;// 上传出错状态
    public static final int STATE_NULL_DATA = 5;// 没有数据需要上传
    
    private static UploadModel instance = new UploadModel();
    
    private UploadModel() {}
    
    public synchronized static UploadModel getInstance() {
        return instance;
    }
    
    private ArrayList<UploadObserver> observers = new ArrayList<>();
    private HashMap<Integer, UploadInfo> uploadInfoMap = new HashMap<>();
    private HashMap<Integer, UploadTask> uploadTaskMap = new HashMap<>();
    
    public void upload(int tag) {
        UploadInfo uploadInfo = uploadInfoMap.get(tag);
        if (uploadInfo == null) {
            uploadInfo = UploadInfo.create(tag);
            uploadInfoMap.put(tag, uploadInfo);
        }
        if (uploadInfo.getState() == STATE_NONE || uploadInfo.getState() == STATE_ERROR) {
            UploadTask uploadTask = new UploadTask(uploadInfo);
            uploadTaskMap.put(tag, uploadTask);
            uploadInfo.setState(STATE_WAITTING);
            notifyStateChange(uploadInfo);
            ThreadPoolManager.getInstance().execute(uploadTask);
        }
    }
    
    public class UploadTask extends Thread {
        
        private UploadInfo uploadInfo;
        
        public UploadTask(UploadInfo uploadInfo) {
            this.uploadInfo = uploadInfo;
        }
        
        @Override
        public void run() {
            super.run();
            uploadInfo.setState(STATE_UPLOADING);
            notifyStateChange(uploadInfo);
            ArrayList<Class<? extends BaseTable>> list = uploadInfo.getTableList();
            int count = 0;
            for (Class<? extends BaseTable> clazz : list) {
                ArrayList<? extends BaseTable> findAll = DBTools.findAll(clazz, "ifuodate", "0");
                if (findAll != null && !findAll.isEmpty()) count += findAll.size();
            }
            if (count == 0) {
                uploadInfo.setState(STATE_NULL_DATA);
                notifyStateChange(uploadInfo);
                uploadInfoMap.remove(uploadInfo.getTag());
                uploadTaskMap.remove(uploadInfo.getTag());
                return;
            }
            if (uploadInfo.getTag() == Constants.TAG_BASE) {
                uploadBase(uploadInfo);
            } else {
                uploadExtra(uploadInfo);
            }
        }
        
        private void uploadBase(UploadInfo uploadInfo) {
            for (Class<? extends BaseTable> clazz : uploadInfo.getTableList()) {
                ArrayList<? extends BaseTable> list = DBTools.findAll(clazz, "ifuodate", "0");
                if (list != null && !list.isEmpty()) {
                    String json = JsonUtil.parseListToJson(list);
//                    LogUtils.e(json);
                    try {
                        int count = WebController.uploadBaseData(json, clazz.getSimpleName());
                        if (count == 0) {
                            uploadInfo.setState(STATE_ERROR);
                            notifyStateChange(uploadInfo);
                        }
                        for (BaseTable baseTable : list) {
                            baseTable.setIfuodate("1");
                        }
                        DBTools.update(list, WhereBuilder.b("ifuodate", "=", "0"), "ifuodate");
                    } catch (IOException | XmlPullParserException e) {
                        uploadInfo.setState(STATE_ERROR);
                        notifyStateChange(uploadInfo);
                        e.printStackTrace();
                    }
                }
                // 上传图片
                if (TextUtils.equals(clazz.getSimpleName(), "AssetMaterialInfo")) {
//                    uploadResume(list);
                }
            }
            if (uploadInfo.getState() != STATE_ERROR) {
                uploadInfo.setState(STATE_FINISH);
                notifyStateChange(uploadInfo);
            }
            uploadInfoMap.remove(uploadInfo.getTag());
            uploadTaskMap.remove(uploadInfo.getTag());
        }
        
        private void uploadResume(ArrayList<? extends BaseTable> list) {
            for (BaseTable b : list) {
                AssetMaterialInfo a = (AssetMaterialInfo) b;
                String key = a.getAttachmentKey();
                if(!TextUtils.isEmpty(key)) {
                    StringBuilder sb = new StringBuilder();
                    String path = sb.append(Constants.PATH).append("/").append(key).append(".jpg").toString();
                    LogUtils.e("path : " + path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if(bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
                        byte[] bytes = baos.toByteArray();
                        //base64 encode
                        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
                        String image = new String(encode);
                        LogUtils.e("key : " + key);
                        LogUtils.e("image : " + image);
                        
                        int result = 0;
                        try {
                            result = WebUtil.uploadResume(key, image);
                        } catch (IOException | XmlPullParserException e) {
//                            uploadInfo.setState(STATE_ERROR);
//                            notifyStateChange(uploadInfo);
                            e.printStackTrace();
                        }
                        LogUtils.e("result : " + result);
                    }
                }
            }
        }

        private void uploadExtra(UploadInfo uploadInfo) {
            uploadAsset(uploadInfo);
            switch (uploadInfo.getTag()) {
                case Constants.TAG_CHECK:
                    uploadExtra(uploadInfo, Asset_CheckInfo01.class, Asset_CheckInfo02.class);
                    break;
                case Constants.TAG_HANDLE:
                    uploadExtra(uploadInfo, Asset_HandleInfo01.class, Asset_HandleInfo02.class);
                    break;
                case Constants.TAG_INSPECTION:
                    uploadExtra(uploadInfo, Asset_InspectionInfo01.class, Asset_InspectionInfo02.class);
                    break;
                case Constants.TAG_REPAIR:
                    uploadExtra(uploadInfo, Asset_RepairInfo01.class, Asset_RepairInfo02.class);
                    break;
                case Constants.TAG_SCRAP:
                    uploadExtra(uploadInfo, Asset_ScrapInfo01.class, Asset_ScrapInfo02.class);
                    break;
                case Constants.TAG_STOP:
                    uploadExtra(uploadInfo, Asset_StopInfo01.class, Asset_StopInfo02.class);
                    break;
                case Constants.TAG_TRANSFER:
                    uploadExtra(uploadInfo, Asset_AllocateInfo01.class, Asset_AllocateInfo02.class);
                    break;
            }
        }
        
        /**
         * 上传资产主表信息
         * @param uploadInfo2
         */
        private void uploadAsset(UploadInfo uploadInfo2) {
            ArrayList<AssetMaterialInfo> list = DBTools.findAll(AssetMaterialInfo.class, "isChanged", 1);
            if(list == null || list.isEmpty()) return;
            String json = JsonUtil.parseListToJson(list);
            try {
                int count = WebController.uploadBaseData1(json, "AssetMaterialInfo");
                if(count == 0) {
                    uploadInfo.setState(STATE_ERROR);
                    notifyStateChange(uploadInfo);
                    return;
                }
                for (AssetMaterialInfo a : list) {
                    a.setIsChanged(0);
                    try {
                        DBTools.saveOrUpdate(a);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                uploadInfo.setState(STATE_ERROR);
                notifyStateChange(uploadInfo);
                e.printStackTrace();
            }
        }

        private void uploadExtra(UploadInfo uploadInfo, Class<? extends BatchTable> mainTable, Class<? extends BatchTable> detailTable) {
            ArrayList<? extends BatchTable> mainList = DBTools.findAll(Selector.from(mainTable).where("ifuodate", "=", "0"));
            if(mainList != null && !mainList.isEmpty()) {
                for (BatchTable main : mainList) {
                    String mainJson = JsonUtil.paserBeanToJson(main);
                    LogUtils.e("mainJson : " + mainJson);
                    ArrayList<? extends BatchTable> detailList = DBTools.findAll(detailTable, "BatchNumber", main.getBatchNumber());
                    if(detailList == null) continue;
                    String detailJson = JsonUtil.parseListToJson(detailList);
                    LogUtils.e("detailJson : " + detailJson);
                    try {
                        int count = WebController.uploadExtraData(mainJson, detailJson, mainTable.getSimpleName().toString().trim());
                        if (count == 0) {
                            uploadInfo.setState(STATE_ERROR);
                            notifyStateChange(uploadInfo);
                        }
                    } catch (IOException | XmlPullParserException e) {
                        uploadInfo.setState(STATE_ERROR);
                        notifyStateChange(uploadInfo);
                        e.printStackTrace();
                    }
                    if (uploadInfo.getState() != STATE_ERROR) {
                        try {
                            DBTools.delete(mainTable, WhereBuilder.b("BatchNumber", "=", main.getBatchNumber()));
                            DBTools.delete(detailTable, WhereBuilder.b("BatchNumber", "=", main.getBatchNumber()));
                        } catch (DbException e) {
                            uploadInfo.setState(STATE_ERROR);
                            notifyStateChange(uploadInfo);
                            e.printStackTrace();
                        }
                    }
                }
                if (uploadInfo.getState() != STATE_ERROR) {
                    uploadInfo.setState(STATE_FINISH);
                    notifyStateChange(uploadInfo);
                }
                uploadInfoMap.remove(uploadInfo.getTag());
                uploadTaskMap.remove(uploadInfo.getTag());
            } else {
                uploadInfo.setState(STATE_NULL_DATA);
                notifyStateChange(uploadInfo);
                uploadInfoMap.remove(uploadInfo.getTag());
                uploadTaskMap.remove(uploadInfo.getTag());
            }
        }
    }
    
    public UploadInfo getUploadInfo(UploadInfo uploadInfo) {
        return uploadInfoMap.get(uploadInfo.getTag());
    }
    
    public void notifyStateChange(final UploadInfo uploadInfo) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (UploadObserver observer : observers) {
                    observer.onStateChanged(uploadInfo);
                }
            }
        });
    }
    
    public void registerObserver(UploadObserver observer) {
        if (observers != null && !observers.contains(observer)) observers.add(observer);
    }
    
    public interface UploadObserver {
        void onStateChanged(UploadInfo uploadInfo);
    }
    
}
