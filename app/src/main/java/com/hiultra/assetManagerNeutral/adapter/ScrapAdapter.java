package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.ScrapHolder;

public class ScrapAdapter extends BasicAdapter<Asset_ScrapInfo02> {
    
    public ScrapAdapter(Context c, ArrayList<Asset_ScrapInfo02> list) {
        super(c, list);
    }

    @Override
    protected ScrapHolder getHolder(LayoutInflater inflater, Asset_ScrapInfo02 t) {
        return new ScrapHolder(inflater, t);
    }
    
}
