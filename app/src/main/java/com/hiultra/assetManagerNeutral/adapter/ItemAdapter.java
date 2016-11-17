package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.domain.StartLvItem;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.ItemHolder;

/**
 * 资产管理模块DrawerLayout左侧ListView适配器
 * @author Tom
 * @date 2016年9月2日
 * @Time 下午3:19:22
 */
public class ItemAdapter extends BasicAdapter<StartLvItem> {

    public ItemAdapter(Context c, ArrayList<StartLvItem> itemList) {
        super(c, itemList);
    }

    @Override
    protected BaseHolder<StartLvItem> getHolder(LayoutInflater inflater, StartLvItem t) {
        return new ItemHolder(inflater, t);
    }


}
