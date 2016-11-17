package com.hiultra.assetManagerNeutral.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import com.hiultra.assetManagerNeutral.UHF.EpcReader;
import com.hiultra.assetManagerNeutral.UHF.EpcReader.EpcReadObserver;
import com.hiultra.assetManagerNeutral.manager.SingleThreadPoolManager;
import com.hiultra.assetManagerNeutral.manager.ThreadPoolManager;
import com.hiultra.assetManagerNeutral.ui.fragment.BaseFragment;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.minttown.hiultra.R;

/**
 * 所有需要关联扫卡模块的Activity
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:47:33
 */
public abstract class BaseUhfActivity extends BaseActivity {
    
    protected EpcReader epcReader;
    protected SingleThreadPoolManager manager;
    protected BaseFragment currentFragment;
    
    @Override
    protected void initData(Bundle savedInstanceState) throws DbException {
        super.initData(savedInstanceState);
        if (app.checkServiceAndDevice()) {
            LogUtils.e("设备上电...");
            app.device.uhfPowerOn();
            Handler handler = initHandler();
            if (handler != null) app.device.setHandler(handler);
            app.device.runRead();
            manager = SingleThreadPoolManager.getInstance();
            epcReader = EpcReader.getInstance(app).setFlag(true);
            // 注册监听器
            registerObserver(currentFragment);
            manager.execute(epcReader); 
        } else {
            restartService();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    public void restartService() {
        new AlertDialog.Builder(this).setMessage(R.string.msg_restart)
                .setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        app.unBindService();
                    }
                }).setNegativeButton(R.string.msg_button_no, null).show();
    }
    
    protected abstract Handler initHandler();
    
    // 根据当前可见的Fragment对象注册监听器
    protected void registerObserver(EpcReadObserver observer) {
        if (epcReader != null && observer != null) {
            epcReader.clearObserver();
            epcReader.registerObserver(observer);
        }
    }
}
