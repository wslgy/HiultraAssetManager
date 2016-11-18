package com.hiultra.assetManagerNeutral.ui.activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.hiultra.assetManagerNeutral.global.App;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;

public abstract class BaseActivity extends AppCompatActivity {
    
    protected ActionBarDrawerToggle mDrawerToggle;
    protected App app;
    protected Context context;
    protected FragmentManager fm;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        fm = getFragmentManager();
        app = (App) getApplication();
        context = this;
        initView();
        try {
            initData(savedInstanceState);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    
    protected abstract void initView();
    
    protected void initData(Bundle savedInstanceState) throws DbException {
        DevBeep.init(this);
    }
    
}
