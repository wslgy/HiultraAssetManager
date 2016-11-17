package com.hiultra.assetManagerNeutral.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiultra.assetManagerNeutral.UHF.EpcReader;

/**
 * 所有在创建时需要异步加载数据的Fragment,都要继承该类
 * @author Tom
 * @date 2016年9月5日
 * @Time 下午5:12:00
 */
public abstract class AsyncLoadFragment extends BaseFragment {

    protected LoadingPage loadingPage;
    
    @Override
    protected View initView(final LayoutInflater inflater) {
        if(loadingPage == null) {
            loadingPage = new LoadingPage(mActivity) {
                
                @Override
                protected Object requestData() {
                    return loadData();
                }
                
                @Override
                protected View createSuccessView() {
                    return getSuccessView(inflater);
                }
            };
        } else {
            ViewGroup parent = (ViewGroup) loadingPage.getParent();
            parent.removeView(loadingPage);
        }
        return loadingPage;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }
    
    /**
     * 设置布局
     * @param inflater 
     * @return
     */
    protected abstract View getSuccessView(LayoutInflater inflater);
    
    /**
     * 加载数据
     * @return
     */
    protected abstract Object loadData();

}
