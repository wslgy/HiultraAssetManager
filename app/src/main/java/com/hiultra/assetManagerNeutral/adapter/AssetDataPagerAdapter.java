package com.hiultra.assetManagerNeutral.adapter;

import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


import java.util.ArrayList;

import com.hiultra.assetManagerNeutral.ui.fragment.BaseFragment;


/**
 * 资产管理数据交互模块ViewPager适配器
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午9:21:02
 */
public class AssetDataPagerAdapter extends FragmentPagerAdapter {
    
    ArrayList<BaseFragment> list;
    
    public AssetDataPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public BaseFragment getItem(int position) {
        return list.get(position);
    }
    
    @Override
    public int getCount() {
        return list.size();
    }
    
}
