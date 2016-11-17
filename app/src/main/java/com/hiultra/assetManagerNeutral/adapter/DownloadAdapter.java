package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;
import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.hiultra.assetManagerNeutral.holder.DownloadHolder;

public class DownloadAdapter extends BasicAdapter<ItemInfo> {
    
    public DownloadAdapter(Context c, ArrayList<ItemInfo> list) {
        super(c, list);
    }

    @Override
    protected BaseHolder<ItemInfo> getHolder(LayoutInflater inflater, ItemInfo t) {
        return new DownloadHolder(inflater, t);
    }
    
}
