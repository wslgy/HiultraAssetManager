package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.RepairHolder;

/**
 * 维修模块适配器
 * @author Tom
 * @date 2016年9月19日
 * @Time 下午2:43:54
 */
public class RepairAdapter extends BasicAdapter<Asset_RepairInfo02> {

    public RepairAdapter(Context c, ArrayList<Asset_RepairInfo02> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<Asset_RepairInfo02> getHolder(LayoutInflater inflater, Asset_RepairInfo02 t) {
        return new RepairHolder(inflater, t);
    }
    
}
