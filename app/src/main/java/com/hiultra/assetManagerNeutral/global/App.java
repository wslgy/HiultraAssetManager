package com.hiultra.assetManagerNeutral.global;

import im.fir.sdk.FIR;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.handset.Device;
import com.hiultra.assetManagerNeutral.UHF.DeviceService;
import com.hiultra.assetManagerNeutral.UHF.DeviceService.MyBinder;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.minttown.hiultra.R;

public class App extends Application {

    private static Context context;
    private static Handler mainHandler;

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return mainHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        /* 初始化声音 */
        DevBeep.init(context);
        /* 初始化BugHD */
        FIR.init(context);
        mainHandler = new Handler();
        /* 初始化数据库 */
        try {
            DBTools.createTable();
        } catch (DbException e) {
            e.printStackTrace();
        }
        
        bindService();
    }

    /* 注册扫卡模块逻辑 */

    private Intent intent;
    private DeviceService mService;
    public Device device;
    boolean bBind = false;

    public ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("----Service Disconnected----");
            bBind = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("----Service Connected!----");
            MyBinder binder = (MyBinder) service;
            mService = binder.GetService();
            mService.SetBinder(getApplicationContext(), null);
            device = mService.device;
            bBind = true;
        }
    };

    public void bindService() {
        intent = new Intent(this, DeviceService.class);
        startService(intent);
        try {
            bindService(intent, conn, Service.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unBindService() {
        unbindService(conn);
        stopService(intent);
    }

    /**
     * 检查服务是否绑定成功并确认设备是否正常开启
     */
    public boolean checkServiceAndDevice() {
        if (bBind) {
            if (device.getDeviceIsOpen()) {
                return true;
            } else {
                ToastUtil.show(R.string.msg_device_not_open);
            }
        } else {
            ToastUtil.show(R.string.msg_service_init_fail);
        }
        return false;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
