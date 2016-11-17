package com.hiultra.assetManagerNeutral.holder;

import com.lidroid.xutils.ViewUtils;

import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseHolder<T> {
    
    /** 替代Adapter中的convertView */
    View holderView;
    T t;
    
    public BaseHolder(LayoutInflater inflater, T t) {
        holderView = initView(inflater);
        holderView.setTag(this);
        ViewUtils.inject(this, holderView);
        this.t = t;
    }

    /** 初始化HolderView */
    protected abstract View initView(LayoutInflater inflater);
    
    /** 绑定数据 */
    public abstract void bindData(T t);
    
    
    /** 获取holderView */
    public View getHolderView() {
        return holderView;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setHolderView(View holderView) {
        this.holderView = holderView;
    }
    
}
