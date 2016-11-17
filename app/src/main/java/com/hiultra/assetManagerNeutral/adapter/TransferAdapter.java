package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo02;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.TransferHolder;

public class TransferAdapter extends BasicAdapter<Asset_AllocateInfo02> {
    
    public TransferAdapter(Context c, ArrayList<Asset_AllocateInfo02> list) {
        super(c, list);
    }

    @Override
    protected TransferHolder getHolder(LayoutInflater inflater, Asset_AllocateInfo02 t) {
        return new TransferHolder(inflater, t);
    }
    
}
