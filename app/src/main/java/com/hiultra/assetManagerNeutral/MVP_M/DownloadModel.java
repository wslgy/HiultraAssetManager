package com.hiultra.assetManagerNeutral.MVP_M;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.DateTimeKeyListener;

import com.google.gson.reflect.TypeToken;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.table.BaseTable;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;
import com.hiultra.assetManagerNeutral.manager.ThreadPoolManager;
import com.hiultra.assetManagerNeutral.util.DateUtil;
import com.hiultra.assetManagerNeutral.util.JsonUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.hiultra.assetManagerNeutral.web.WebController;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

public class DownloadModel implements IModel {
    
    public static final int STATE_NONE = 0;// 初始状态
    public static final int STATE_WAITTING = 1;// 等待状态
    public static final int STATE_DOWNLOADING = 2;// 下载状态
    public static final int STATE_FINISH = 3;// 下载完成状态
    public static final int STATE_ERROR = 4;// 下载出错状态
    public static final int STATE_NULL_DATA = 5;// 没有数据需要下载
//    public static final int STATE_PAUSE = 6;// 下载暂停状态
    
    
    private static DownloadModel instance = new DownloadModel();
    private DownloadModel() {}
    public synchronized static DownloadModel getInstance() {
        return instance;
    }
    
    private ArrayList<DownloadObserver> observers = new ArrayList<>();
    private HashMap<Integer, DownloadInfo> downloadInfoMap = new HashMap<>();
    private HashMap<Integer, DownloadTask> downloadTaskMap = new HashMap<>();
    
    public void download(ItemInfo widgetInfo) {
        DownloadInfo info = downloadInfoMap.get(widgetInfo.getTag());
        if(info == null) {
            info = DownloadInfo.create(widgetInfo);
            downloadInfoMap.put(widgetInfo.getTag(), info);
        }
        if(info.getState() == STATE_NONE || info.getState() == STATE_ERROR) {
            DownloadTask downloadTask = new DownloadTask(info);
            downloadTaskMap.put(info.getTag(), downloadTask);
            info.setState(STATE_WAITTING);
            notifyStateChange(info);
            // 执行任务
            ThreadPoolManager.getInstance().execute(downloadTask);
        }
    }
    
    private class DownloadTask extends Thread {
        
        private DownloadInfo downloadInfo;
        
        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }
        
        @Override
        public void run() {
            super.run();
            downloadInfo.setState(STATE_DOWNLOADING);
            notifyStateChange(downloadInfo);
            ArrayList<String> list = new ArrayList<>();
            for (Class<?> clazz : downloadInfo.getTableList()) {
                list.add(clazz.getSimpleName());
            }
            int count = WebController.getRequestCount(list, downloadInfo.getLastDate());
            LogUtils.e("count : " + count);
            if(count == 0) { // null
                downloadInfo.setState(STATE_NULL_DATA);
                notifyStateChange(downloadInfo);
            } else if(count == -1) { // error
                downloadInfo.setState(STATE_ERROR);
                notifyStateChange(downloadInfo);
            } else { // 开始下载
                downloadInfo.setMax(count);
                onMaxObtain(downloadInfo);
                for (Class<?> clazz : downloadInfo.getTableList()) {
                    String tableName = clazz.getSimpleName();
                    int page = 1;
                    String date = downloadInfo.getLastDate();
                    LogUtils.e("tag : " + downloadInfo.getTag() + " last date : " + date);
                    while(page < Integer.MAX_VALUE && downloadInfo.getState() == STATE_DOWNLOADING) {
                        SystemClock.sleep(300);
                        String result = WebController.getData(tableName, date, page);
                        LogUtils.e("tableName : " + tableName + "result : " + result);
                        if(TextUtils.isEmpty(result)) { // 下载失败
                            downloadInfo.setState(STATE_ERROR);
                            notifyStateChange(downloadInfo);
                            downloadTaskMap.remove(downloadInfo.getTag());
                            return;
                        }
                        if(TextUtils.equals("false", result) || TextUtils.equals("[]", result)) { // 没有数据了
                            break;
                        }
                        // TODO 保存数据库
                        List<? extends BaseTable> list2 = downloadInfo.parseJson(result, clazz);
                        if(list2 == null) {
                            downloadInfo.setState(STATE_ERROR);
                            notifyStateChange(downloadInfo);
                            return;
                        }
                        for (BaseTable o : list2) {
                            try {
                                BaseTable b = DBTools.findFirst(Selector.from(clazz).where("ID", "=", o.getID()));
                                if(b != null) DBTools.update(o, WhereBuilder.b("ID", "=", o.getID()), new String[]{});
                                else DBTools.save(o);
                            } catch (DbException e) {
                                downloadInfo.setState(STATE_ERROR);
                                notifyStateChange(downloadInfo);
                                e.printStackTrace();
                                return;
                            }
                            int p = downloadInfo.getProgress();
                            downloadInfo.setProgress(++p);
                            onProgressChanged(downloadInfo);
                        }
                        page++;
                        notifyStateChange(downloadInfo);
                    }
                }
                downloadInfo.setState(STATE_FINISH);
                downloadInfo.updateLastDate(DateUtil.GetLocalDateTime());
                notifyStateChange(downloadInfo);
            }
            downloadTaskMap.remove(downloadInfo.getTag());
            downloadInfoMap.remove(downloadInfo.getTag());
        }
    }
    
    public DownloadInfo getDownloadInfo(ItemInfo info) {
        return downloadInfoMap.get(info.getTag());
    }
    
    /**
     * 通知所有的监听器状态更新了
     * 
     * @param downloadInfo
     */
    private void notifyStateChange(final DownloadInfo downloadInfo) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (DownloadObserver observer : observers) {
                    observer.onStateChanged(downloadInfo);
                }
            }
        });
    }
    
    private void onMaxObtain(final DownloadInfo downloadInfo) {
       Util.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            for (DownloadObserver observer : observers) {
                observer.onMaxObtain(downloadInfo);
            }
        }
    });
    }
    
    private void onProgressChanged(final DownloadInfo downloadInfo) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (DownloadObserver observer : observers) {
                    observer.onProgressChanged(downloadInfo);
                }
            }
        });
    }
    
    public void registerObserver(DownloadObserver observer) {
        if(observers != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void unregisterObserver(DownloadObserver observer) {
        if(observers != null && observers.contains(observer)) observers.remove(observer);
    }
    
    public interface DownloadObserver {
        void onMaxObtain(DownloadInfo downloadInfo);
        void onStateChanged(DownloadInfo downloadInfo);
        void onProgressChanged(DownloadInfo downloadInfo);
    }
}
