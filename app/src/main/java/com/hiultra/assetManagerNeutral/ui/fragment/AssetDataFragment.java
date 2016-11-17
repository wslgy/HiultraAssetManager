package com.hiultra.assetManagerNeutral.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hiultra.assetManagerNeutral.ui.view.springViewPager.ModelPagerAdapter;
import com.hiultra.assetManagerNeutral.ui.view.springViewPager.PagerModelManager;
import com.hiultra.assetManagerNeutral.ui.view.springViewPager.ScrollerViewPager;
import com.hiultra.assetManagerNeutral.ui.view.springViewPager.SpringIndicator;
import com.hiultra.assetManagerNeutral.util.Util;
import com.minttown.hiultra.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产管理数据交互界面
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午12:28:45
 */
public class AssetDataFragment extends BaseFragment {
    
    private ScrollerViewPager viewPager;
    private SpringIndicator springIndicator;
    
    private PagerModelManager manager = new PagerModelManager();
    private BaseFragment assetDownloadFragment = null;
    private BaseFragment assetUploadFragment = null;
    private BaseFragment assetCleanFragment = null;
    
    private List<Fragment> fragmentList = null;
    private ArrayList<String> titleList = Util.getStringList(R.array.item_data);
    private ModelPagerAdapter adapter;
    
    @Override
    public void onEpcRead(String epc) {
        
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_data_asset, null);
        viewPager = (ScrollerViewPager) v.findViewById(R.id.viewPager);
        springIndicator = (SpringIndicator) v.findViewById(R.id.springIndicator);
        return v;
        
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) {
        initViewPager();
    }
    
    @Override
    protected void initData() {}
    
    private void initViewPager() {
        if (fragmentList == null || fragmentList.isEmpty()) {
            assetDownloadFragment = new AssetDownloadFragment();
            assetUploadFragment = new AssetUploadFragment();
            assetCleanFragment = new AssetCleanFragment();
            fragmentList = new ArrayList<>();
            fragmentList.add(assetDownloadFragment);
            fragmentList.add(assetUploadFragment);
            fragmentList.add(assetCleanFragment);
        } else {
            assetDownloadFragment = (BaseFragment) fragmentList.get(0);
            assetUploadFragment = (BaseFragment) fragmentList.get(1);
            assetCleanFragment = (BaseFragment) fragmentList.get(2);
        }
        manager.addCommonFragment(fragmentList, titleList);
        adapter = new ModelPagerAdapter(getChildFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(manager.getFragmentCount());
        viewPager.fixScrollSpeed();
        // just set viewPager
        springIndicator.setViewPager(viewPager);
        // AssetDataPagerAdapter adapter = new
        // AssetDataPagerAdapter(getChildFragmentManager(), fragmentList);
        // LogUtils.e("fragmentList.size : " + fragmentList.size());
    }
    
    @Override
    protected void saveData() {
        if(fragmentList == null || fragmentList.isEmpty()) return;
        for (int i = 0; i < fragmentList.size(); i++) {
            BaseFragment fragment = (BaseFragment) fragmentList.get(i);
            fragment.saveData();
        }
    }
    
}
