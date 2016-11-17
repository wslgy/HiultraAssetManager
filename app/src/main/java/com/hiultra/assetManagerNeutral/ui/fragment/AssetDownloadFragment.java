package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.hiultra.assetManagerNeutral.MVP_P.DownloadPresenter;
import com.hiultra.assetManagerNeutral.MVP_V.IAssetDownloadView;
import com.hiultra.assetManagerNeutral.adapter.DownloadAdapter;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.Util;
import com.minttown.hiultra.R;

/**
 * 下载资产信息界面
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午9:11:08
 */
public class AssetDownloadFragment extends BaseFragment {
    
    private ListView lv;
    private DownloadAdapter adapter;
    private ArrayList<ItemInfo> list = new ArrayList<>();
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_download_asset1, null);
        lv = (ListView) v.findViewById(R.id.lv);
        initLv();
        return v;
    }
    
    private void initLv() {
        if(list != null && list.isEmpty()) {
            list.add(new ItemInfo(Constants.TAG_BASE, Util.getString(R.string.title_base)));
//            list.add(new ItemInfo(Constants.TAG_TRANSFER, Util.getString(R.string.title_transfer)));
//            list.add(new ItemInfo(Constants.TAG_SCRAP, Util.getString(R.string.title_scrap)));
//            list.add(new ItemInfo(Constants.TAG_STOP, Util.getString(R.string.title_stop)));
            list.add(new ItemInfo(Constants.TAG_INSPECTION, Util.getString(R.string.title_inspection)));
            list.add(new ItemInfo(Constants.TAG_CHECK, Util.getString(R.string.title_check)));
//            list.add(new ItemInfo(Constants.TAG_HANDLE, Util.getString(R.string.title_handle)));
            list.add(new ItemInfo(Constants.TAG_REPAIR, Util.getString(R.string.title_repair)));
        }
        adapter = new DownloadAdapter(mActivity, list);
        lv.setAdapter(adapter);
        lv.setSelector(new ColorDrawable());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }
    
    @Override
    public void onEpcRead(String epc) {}
    
    @Override
    protected void saveData() {
    }
    
}
