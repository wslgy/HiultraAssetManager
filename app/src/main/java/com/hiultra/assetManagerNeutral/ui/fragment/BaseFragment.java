package com.hiultra.assetManagerNeutral.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiultra.assetManagerNeutral.UHF.EpcReader;
import com.hiultra.assetManagerNeutral.UHF.EpcReader.EpcReadObserver;
import com.hiultra.assetManagerNeutral.global.App;
import com.lidroid.xutils.ViewUtils;

/**
 * 所有Fragment的基类
 * @author Tom
 * @date 2016年9月5日
 * @Time 下午4:49:20
 */
public abstract class BaseFragment extends Fragment implements EpcReadObserver {

    protected Context mActivity;
    protected App app;
    protected FragmentManager fm;
    protected EpcReader epcReader;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        log(getClass().getSimpleName().toString() + "  onAttach...");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log(getClass().getSimpleName().toString() + "  onCreate...");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();
        app = (App) getActivity().getApplication();
        fm = getFragmentManager();
        View v = initView(inflater);
        ViewUtils.inject(this, v);
        log(getClass().getSimpleName().toString() + "  onCreateView...");
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        log(getClass().getSimpleName().toString() + "  onActivityCreated...");
    }
    
    @Override
    public void onStart() {
        super.onStart();
        log(getClass().getSimpleName().toString() + "  onStart...");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        log(getClass().getSimpleName().toString() + "  onResume...");
    }
    
    @Override
    public void onPause() {
        super.onPause();
        log(getClass().getSimpleName().toString() + "  onPause...");
    }
    
    @Override
    public void onStop() {
        super.onStop();
        log(getClass().getSimpleName().toString() + "  onStop...");
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log(getClass().getSimpleName().toString() + "  onDestroyView...");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        log(getClass().getSimpleName().toString() + "  onDestroy...");
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        log(getClass().getSimpleName().toString() + "  onDetach...");
    }
    
    /**
     * 初始化布局,这个方法会在Fragment的onCreateView()方法执行时调用
     * @param inflater
     * @return
     */
    protected abstract View initView(LayoutInflater inflater);
    
    /**
     * 初始化数据,这个方法会在Activity的onCreate()方法执行完成后调用
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);
    
    /**
     * 在onResume方法中调用
     */
    protected void initData() {
        
    }
    
    @Override
    public EpcReadObserver getCurrentObserver() {
        saveData();
        return this;
    }
    
    /**
     * 所有Fragment界面在被替换之前,都会回调这个方法,可以处理一些数据保存的逻辑
     */
    protected abstract void saveData();
    
    private void log(String msg) {
//        LogUtils.e(msg);
    }
    
}
