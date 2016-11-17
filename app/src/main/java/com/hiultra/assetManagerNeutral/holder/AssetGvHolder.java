package com.hiultra.assetManagerNeutral.holder;

import com.hiultra.assetManagerNeutral.domain.AssetGvItem;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 资产管理模块Gridview Holder
 * 
 * @author Tom
 * @date 2016年10月31日
 * @Time 上午11:06:42
 */
public class AssetGvHolder extends BaseHolder<AssetGvItem> {
    
    @ViewInject(R.id.tv_gv_asset)
    private TextView tv;
    @ViewInject(R.id.iv_gv_asset)
    private ImageView iv;
    
    public AssetGvHolder(LayoutInflater inflater, AssetGvItem t) {
        super(inflater, t);
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.item_gv_asset, null);
        return v;
    }
    
    @Override
    public void bindData(AssetGvItem t) {
        tv.setText(t.getTitle());
        iv.setBackground(t.getIcon());
    }
    
}
