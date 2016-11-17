package com.hiultra.assetManagerNeutral.UHF;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ftdi.j2xx.D2xxManager;
import com.handset.Device;
import com.minttown.hiultra.R;

public class DeviceService extends Service{

    public static D2xxManager ftD2xx = null;
    public Device device;

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {

        public DeviceService GetService() {
            return DeviceService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded!");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getString(R.string.notification_title));
        mBuilder.setContentText(getString(R.string.notification_content));
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mNotificationManager.notify(1, mBuilder.build());

        /*
         * Notification notification = new Notification(R.drawable.ic_launcher,
         * "CMCID Service", System.currentTimeMillis());
         * notification.setLatestEventInfo(this, "CMCID", "CMCID Service",
         * null); startForeground(1, notification);
         */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is Ubbinded!");
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    public void SetBinder(Context context, Handler handler) {

        try {
            ftD2xx = D2xxManager.getInstance(this);
            device = new Device(ftD2xx, getApplicationContext(), null);
        } catch (D2xxManager.D2xxException e) {
            Log.e("TT", "getInstance fail!!");
        }
    }
}
