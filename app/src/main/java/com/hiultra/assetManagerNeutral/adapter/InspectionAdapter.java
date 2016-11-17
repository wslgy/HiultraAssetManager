package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.InspectionHolder;

/**
 * 盘点模块适配器
 * @author Tom
 * @date 2016年9月19日
 * @Time 下午2:43:54
 */
public class InspectionAdapter extends BasicAdapter<Asset_InspectionInfo02> {

    public InspectionAdapter(Context c, ArrayList<Asset_InspectionInfo02> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<Asset_InspectionInfo02> getHolder(LayoutInflater inflater, Asset_InspectionInfo02 t) {
        return new InspectionHolder(inflater, t);
    }
    
}
