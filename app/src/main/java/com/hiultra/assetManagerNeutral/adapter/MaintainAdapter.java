package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.MaintainHolder;

public class MaintainAdapter extends BasicAdapter<AssetMaterialInfo> {
    
    public MaintainAdapter(Context c, ArrayList<AssetMaterialInfo> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<AssetMaterialInfo> getHolder(LayoutInflater inflater, AssetMaterialInfo t) {
        return new MaintainHolder(inflater, t);
    }
    
}
