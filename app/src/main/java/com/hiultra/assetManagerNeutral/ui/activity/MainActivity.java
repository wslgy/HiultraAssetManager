package com.hiultra.assetManagerNeutral.ui.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.hiultra.assetManagerNeutral.ui.fragment.AssetChooseFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetDataFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetSearchFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.BaseFragment;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面(导航界面)
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:55:54
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseUhfActivity {
    
    @ViewInject(R.id.tv_manager)
    private TextView vManager;
    @ViewInject(R.id.tv_data)
    private TextView vData;
    @ViewInject(R.id.tv_search)
    private TextView vSearch;
    @ViewInject(R.id.container)
    private FrameLayout container;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    List<Fragment> fragmentList = new ArrayList<>();
    
    @Override
    protected void initView() {}
    
    @Override
    protected Handler initHandler() {
        return null;
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) throws DbException {
        super.initData(savedInstanceState);
        initActionBar();
        initFragment(savedInstanceState);
        changeFragment(0);
    }
    
    private void initActionBar() {
        setActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.icon_home);
//        actionBar.setDisplayHomeAsUpEnabled(false);
        // actionBar.setDisplayShowHomeEnabled(true);
        // 设置汉堡包图片
        // mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        // R.drawable.ic_drawer_am, 0, 0);
        // mDrawerToggle.syncState(); // 同步状态
        // mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 设置ActionBar背景
        toolbar.setBackgroundDrawable(Util.getDrawable(R.drawable.bg_actionbar));
    }
    
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            BaseFragment managerFragment = new AssetChooseFragment();
            BaseFragment dataFragment = new AssetDataFragment();
            BaseFragment searchFragment = new AssetSearchFragment();
            fragmentList.add(managerFragment);
            fragmentList.add(dataFragment);
            fragmentList.add(searchFragment);
            LogUtils.e("savedInstanceState == null");
        } else {
            LogUtils.e("savedInstanceState != null");
        }
    }
    
    private void changeFragment(int position) {
        if (currentFragment == fragmentList.get(position)) {
            LogUtils.e("不用切换...");
            return;
        }
        currentFragment = (BaseFragment) fragmentList.get(position);
        if (currentFragment != null) {
            registerObserver(currentFragment);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.container, currentFragment);
            transaction.commit();
            // LogUtils.e("mainactivity fragmentList size : " +
            // fragmentList.size());
            setTab(position);
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (app.checkServiceAndDevice()) {
            app.device.uhfPowerOff();
            app.device.setHandler(null);
        }
    }
    
    /**
     * 根据索引设置底部导航栏
     * 
     * @param position
     */
    private void setTab(int position) {
        vManager.setSelected(false);
        vData.setSelected(false);
        vSearch.setSelected(false);
        // vManager.setBackgroundResource(android.R.color.transparent);
        // vData.setBackgroundResource(android.R.color.transparent);
        // vSearch.setBackgroundResource(android.R.color.transparent);
        switch (position) {
            case 0:
                vManager.setSelected(true);
                // vManager.setBackgroundResource(android.R.color.holo_green_light);
                break;
            case 1:
                vData.setSelected(true);
                // vData.setBackgroundResource(android.R.color.holo_green_light);
                break;
            case 2:
                vSearch.setSelected(true);
                // vSearch.setBackgroundResource(android.R.color.holo_green_light);
                break;
        }
    }
    
    /**
     * 选择界面
     * 
     * @param v
     */
    @OnClick(R.id.tv_manager)
    public void chooseManager(View v) {
        changeFragment(0);
    }
    
    @OnClick(R.id.tv_data)
    public void chooseData(View v) {
        changeFragment(1);
    }
    
    @OnClick(R.id.tv_search)
    public void chooseSearch(View v) {
        changeFragment(2);
    }
}
