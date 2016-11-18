package com.hiultra.assetManagerNeutral.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.MVP_M.DownloadModel;
import com.hiultra.assetManagerNeutral.MVP_M.DownloadModel.DownloadObserver;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

public class DownloadHolder extends BaseHolder<ItemInfo> implements DownloadObserver {
    
    @ViewInject(R.id.tv_download)
    private TextView tv;
    @ViewInject(R.id.pb_download)
    private ProgressBar pb;
    @ViewInject(R.id.iv_download)
    private ImageView iv;
    
    private StringBuilder nullData = new StringBuilder();
    private StringBuilder downloadError = new StringBuilder();
    private StringBuilder downloadComplete = new StringBuilder();
    
    public DownloadHolder(LayoutInflater inflater, ItemInfo t) {
        super(inflater, t);
        nullData.append(t.getTitle()).append(Util.getString(R.string.text_null_data));
        downloadError.append(t.getTitle()).append(Util.getString(R.string.text_download_error));
        downloadComplete.append(t.getTitle()).append(Util.getString(R.string.text_download_complete));
        DownloadModel.getInstance().registerObserver(this);
        DownloadInfo downloadInfo = DownloadModel.getInstance().getDownloadInfo(t);
        if (downloadInfo != null) onStateChanged(downloadInfo);
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.item_dowload, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo downloadInfo = DownloadModel.getInstance().getDownloadInfo(t);
                if (downloadInfo == null) { // 重新下载
                    DownloadModel.getInstance().download(t);
                    LogUtils.e("重新下载....");
                } else { // 已经在下载
                    LogUtils.e("已经在下载....");
                }
            }
        });
        return v;
    }
    
    @Override
    public void bindData(ItemInfo t) {
        tv.setText(t.getTitle());
    }
    
    @Override
    public void onMaxObtain(DownloadInfo downloadInfo) {
        if (downloadInfo.getTag() == t.getTag()) pb.setMax(downloadInfo.getMax());
    }
    
    @Override
    public void onProgressChanged(DownloadInfo downloadInfo) {
        if (downloadInfo.getTag() == t.getTag()) {
            onMaxObtain(downloadInfo);
            pb.setProgress(downloadInfo.getProgress());
        }
    }
    
    @Override
    public void onStateChanged(DownloadInfo downloadInfo) {
        if (downloadInfo.getTag() == t.getTag()) {
            switch (downloadInfo.getState()) {
                case DownloadModel.STATE_NONE:
                    iv.setBackgroundResource(R.drawable.icon_inbox);
                    break;
                case DownloadModel.STATE_NULL_DATA:
                    iv.setBackgroundResource(R.drawable.icon_inbox);
                    ToastUtil.show(nullData.toString());
                    break;
                case DownloadModel.STATE_WAITING:
                    iv.setBackgroundResource(R.drawable.icon_wait);
                    break;
                case DownloadModel.STATE_DOWNLOADING:
                    iv.setBackgroundResource(R.drawable.icon_loading);
                    break;
                case DownloadModel.STATE_ERROR:
                    iv.setBackgroundResource(R.drawable.icon_error);
                    ToastUtil.show(downloadError.toString());
                    Util.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            iv.setBackgroundResource(R.drawable.icon_inbox);
                            pb.setProgress(0);
                        }
                    }, 3500);
                    break;
                case DownloadModel.STATE_FINISH:
                    iv.setBackgroundResource(R.drawable.icon_accept);
                    ToastUtil.show(downloadComplete.toString());
                    Util.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            iv.setBackgroundResource(R.drawable.icon_inbox);
                            pb.setProgress(0);
                        }
                    }, 2500);
                    break;
            }
        }
    }
    
}
