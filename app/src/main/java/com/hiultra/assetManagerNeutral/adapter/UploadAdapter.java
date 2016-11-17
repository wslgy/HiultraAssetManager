package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.hiultra.assetManagerNeutral.domain.UploadInfo;
import com.hiultra.assetManagerNeutral.holder.UploadHolder;

public class UploadAdapter extends BasicAdapter<UploadInfo> {
    
    public UploadAdapter(Context c, ArrayList<UploadInfo> list) {
        super(c, list);
    }

    @Override
    protected UploadHolder getHolder(LayoutInflater inflater, UploadInfo t) {
        return new UploadHolder(inflater, t);
    }
    
}
