package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo02;
import com.hiultra.assetManagerNeutral.holder.StopHolder;

public class StopAdapter extends BasicAdapter<Asset_StopInfo02> {
    
    public StopAdapter(Context c, ArrayList<Asset_StopInfo02> list) {
        super(c, list);
    }

    @Override
    protected StopHolder getHolder(LayoutInflater inflater, Asset_StopInfo02 t) {
        return new StopHolder(inflater, t);
    }
    
}
