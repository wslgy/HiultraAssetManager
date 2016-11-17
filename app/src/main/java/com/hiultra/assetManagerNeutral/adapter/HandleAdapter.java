package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.HandleHolder;

/**
 * 盘点模块适配器
 * @author Tom
 * @date 2016年9月19日
 * @Time 下午2:43:54
 */
public class HandleAdapter extends BasicAdapter<Asset_HandleInfo02> {

    public HandleAdapter(Context c, ArrayList<Asset_HandleInfo02> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<Asset_HandleInfo02> getHolder(LayoutInflater inflater, Asset_HandleInfo02 t) {
        return new HandleHolder(inflater, t);
    }
    
}
