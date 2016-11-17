package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.domain.AssetGvItem;
import com.hiultra.assetManagerNeutral.holder.AssetGvHolder;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;

/**
 * 资产管理模块Gridview适配器
 * @author Tom
 * @date 2016年10月31日
 * @Time 上午11:06:57
 */
public class AssetGvAdapter extends BasicAdapter<AssetGvItem> {

    public AssetGvAdapter(Context c, ArrayList<AssetGvItem> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<AssetGvItem> getHolder(LayoutInflater inflater, AssetGvItem item) {
        return new AssetGvHolder(inflater, item);
    }
    
    
}
