package com.hiultra.assetManagerNeutral.holder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.hiultra.assetManagerNeutral.domain.StartLvItem;
import com.hiultra.assetManagerNeutral.ui.view.DrawableCenterTextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

/**
 * DrawerLayout左侧ListView填充器
 * @author Tom
 * @date 2016年9月3日
 * @Time 上午11:25:03
 */
public class ItemHolder extends BaseHolder<StartLvItem> {
    
    @ViewInject(R.id.tv_item_itemAdapter)
    private DrawableCenterTextView tv;
    
    public ItemHolder(LayoutInflater inflater, StartLvItem t) {
        super(inflater, t);
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.item_itemadapter, null);
        ViewUtils.inject(this, v);
        return v;
    }

    @Override
    public void bindData(StartLvItem t) {
        tv.setText(t.getTitle());
        Drawable icon = t.getIcon();
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        tv.setCompoundDrawables(icon, null, null, null);
    }

}
