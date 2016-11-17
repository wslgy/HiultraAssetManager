package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.CheckHolder;

/**
 * 盘点模块适配器
 * @author Tom
 * @date 2016年9月19日
 * @Time 下午2:43:54
 */
public class CheckAdapter extends BasicAdapter<Asset_CheckInfo02> {

    public CheckAdapter(Context c, ArrayList<Asset_CheckInfo02> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<Asset_CheckInfo02> getHolder(LayoutInflater inflater, Asset_CheckInfo02 t) {
        return new CheckHolder(inflater, t);
    }
    
}