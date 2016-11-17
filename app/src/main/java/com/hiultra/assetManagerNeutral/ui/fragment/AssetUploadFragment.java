package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;

import com.hiultra.assetManagerNeutral.adapter.UploadAdapter;
import com.hiultra.assetManagerNeutral.domain.UploadInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.minttown.hiultra.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 上传资产信息界面
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午9:10:56
 */
public class AssetUploadFragment extends BaseFragment {
    
    private ListView lv;
    private UploadAdapter adapter;
    private ArrayList<UploadInfo> list = new ArrayList<>();
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_upload_asset, null);
        lv = (ListView) v.findViewById(R.id.lv);
        initLv();
        return v;
    }
    
    private void initLv() {
        if(list == null || list.isEmpty()) {
            list.add(UploadInfo.create(Constants.TAG_BASE));
            list.add(UploadInfo.create(Constants.TAG_TRANSFER));
            list.add(UploadInfo.create(Constants.TAG_SCRAP));
            list.add(UploadInfo.create(Constants.TAG_STOP));
            list.add(UploadInfo.create(Constants.TAG_INSPECTION));
            list.add(UploadInfo.create(Constants.TAG_CHECK));
            list.add(UploadInfo.create(Constants.TAG_HANDLE));
            list.add(UploadInfo.create(Constants.TAG_REPAIR));
        }
        adapter = new UploadAdapter(mActivity, list);
        lv.setAdapter(adapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        
    }
    
    @Override
    public void onEpcRead(String epc) {
        
    }
    
    @Override
    protected void saveData() {
        
    }
    
}
