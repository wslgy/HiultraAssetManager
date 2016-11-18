package com.hiultra.assetManagerNeutral.ui.activity;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toolbar;

import com.hiultra.assetManagerNeutral.adapter.ItemAdapter;
import com.hiultra.assetManagerNeutral.domain.StartLvItem;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetCheckFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetHandleFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetInspectionFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetMaintainFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetRepairFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetScrapFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetStopFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.AssetTransferFragment;
import com.hiultra.assetManagerNeutral.ui.fragment.BaseFragment;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

/**
 * 资产管理内容界面
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午10:43:05
 */
@ContentView(R.layout.activity_assetcontent)
public class AssetContentActivity extends BaseUhfActivity implements OnItemClickListener {
    
    @ViewInject(R.id.drawerLayout)
    protected DrawerLayout mDrawerLayout;
    @ViewInject(R.id.lv_start)
    protected ListView lvStart;
    @ViewInject(R.id.layout_content)
    private FrameLayout content;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    private ArrayList<BaseFragment> fragmentList = new ArrayList<>();
    private ArrayList<StartLvItem> itemList = new ArrayList<>();
    
    /** 图标集合 */
    private int[] iconArray = new int[] { R.drawable.icon_asset_maintain, R.drawable.icon_asset_transfer,
            R.drawable.icon_asset_scrap, R.drawable.icon_asset_stop, R.drawable.icon_asset_check,
            R.drawable.icon_asset_inspection, R.drawable.icon_asset_handle, R.drawable.icon_asset_repair };
    
    /** 标题集合 */
    private String[] titleArray = Util.getStringArray(R.array.item_title_asset);
    
    @Override
    protected void initView() {
        for (int i = 0; i < titleArray.length; i++) {
            StartLvItem item = new StartLvItem(Util.getDrawable(iconArray[i]), titleArray[i]);
            itemList.add(item);
        }
        // 测试
        ItemAdapter itemAdapter = new ItemAdapter(this, itemList);
        lvStart.setSelector(new ColorDrawable());
        lvStart.setAdapter(itemAdapter);
        lvStart.setOnItemClickListener(this);
        
    }
    
    @Override
    protected Handler initHandler() {
        return null;
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) throws DbException {
        super.initData(savedInstanceState);
        initActionBar();
        initFragment();
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        changeFragment(position);
    }
    
    private void initActionBar() {
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setDisplayShowHomeEnabled(true);
        // 设置汉堡包图片
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_am, 0, 0);
        mDrawerToggle.syncState(); // 同步状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 设置ActionBar背景
        actionBar.setBackgroundDrawable(Util.getDrawable(R.drawable.bg_actionbar));
    }
    
    private void initFragment() {
        BaseFragment assetMaintainFragment = new AssetMaintainFragment();
        BaseFragment assetTransferFragment = new AssetTransferFragment();
        BaseFragment assetScrapFragment = new AssetScrapFragment();
        BaseFragment assetStopFragment = new AssetStopFragment();
        BaseFragment assetCheckFragment = new AssetCheckFragment();
        BaseFragment assetInspectionFragment = new AssetInspectionFragment();
        BaseFragment assetHandleFragment = new AssetHandleFragment();
        BaseFragment assetRepairFragment = new AssetRepairFragment();
        
        fragmentList.add(assetMaintainFragment);
        fragmentList.add(assetTransferFragment);
        fragmentList.add(assetScrapFragment);
        fragmentList.add(assetStopFragment);
        fragmentList.add(assetCheckFragment);
        fragmentList.add(assetInspectionFragment);
        fragmentList.add(assetHandleFragment);
        fragmentList.add(assetRepairFragment);
    }
    
    private void changeFragment(int position) {
        if (currentFragment == fragmentList.get(position)) return;
        currentFragment = fragmentList.get(position);
        if (currentFragment != null) {
            registerObserver(currentFragment);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.layout_content, currentFragment);
            transaction.commit();
            // 变更ActionBar标题
            actionBar.setTitle(itemList.get(position).getTitle());
            actionBar.setLogo(iconArray[position]);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (manager != null && epcReader != null) {
            manager.cancel(epcReader);
            epcReader.setFlag(false);
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除所有监听器
        if (epcReader != null) epcReader.clearObserver();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_start) {
            changeFragment(position);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }
    
}
