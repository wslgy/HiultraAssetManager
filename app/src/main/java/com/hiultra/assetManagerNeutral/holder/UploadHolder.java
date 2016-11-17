package com.hiultra.assetManagerNeutral.holder;

import com.hiultra.assetManagerNeutral.MVP_M.UploadModel;
import com.hiultra.assetManagerNeutral.MVP_M.UploadModel.UploadObserver;
import com.hiultra.assetManagerNeutral.domain.UploadInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UploadHolder extends BaseHolder<UploadInfo> implements UploadObserver {
    
    @ViewInject(R.id.tv_title_upload)
    private TextView tvTitle;
    @ViewInject(R.id.tv_point_upload)
    private TextView tvPoint;
    @ViewInject(R.id.pb_upload)
    private ProgressBar pb;
    @ViewInject(R.id.ll_upload)
    private View vUpload;
    
    private static String clickUpload = Util.getString(R.string.text_click_upload);
    private static String waiting = Util.getString(R.string.text_waiting);
    private static String error = Util.getString(R.string.text_upload_error);
    private static String nullData = Util.getString(R.string.text_null_data_upload);
    private static String uploading = Util.getString(R.string.text_uploading);
    private static String complete = Util.getString(R.string.text_upload_complete);
    
    
    public UploadHolder(LayoutInflater inflater, UploadInfo t) {
        super(inflater, t);
        UploadModel.getInstance().registerObserver(this);
        UploadInfo uploadInfo = UploadModel.getInstance().getUploadInfo(t);
        if(uploadInfo != null) onStateChanged(uploadInfo);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.item_upload, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadInfo uploadInfo = UploadModel.getInstance().getUploadInfo(t);
                if(uploadInfo == null) {
                    UploadModel.getInstance().upload(t.getTag());
                }
            }
        });
        return v;
    }
    
    @Override
    public void bindData(UploadInfo t) {
        tvTitle.setText(t.getTitle());
    }

    @Override
    public void onStateChanged(UploadInfo uploadInfo) {
        if(uploadInfo.getTag() == t.getTag()) {
            switch (uploadInfo.getState()) {
                case UploadModel.STATE_NONE:
                    tvPoint.setText(clickUpload);
                    pb.setVisibility(View.INVISIBLE);
                    break;
                case UploadModel.STATE_WAITTING:
                    tvPoint.setText(waiting);
                    pb.setVisibility(View.INVISIBLE);
                    break;
                case UploadModel.STATE_NULL_DATA:
                    tvPoint.setText(nullData);
                    Util.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            tvPoint.setText(clickUpload);
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }, 2500);
                    break;
                case UploadModel.STATE_UPLOADING:
                    tvPoint.setText(uploading);
                    pb.setVisibility(View.VISIBLE);
                    break;
                case UploadModel.STATE_ERROR:
                    tvPoint.setText(error);
                    Util.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            tvPoint.setText(clickUpload);
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }, 2500);
                    break;
                case UploadModel.STATE_FINISH:
                    tvPoint.setText(complete);
                    Util.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            tvPoint.setText(clickUpload);
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }, 2500);
                    break;
            }
        }
    }
    
}
