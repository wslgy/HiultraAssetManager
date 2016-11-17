package com.hiultra.assetManagerNeutral.holder;

import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo02;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TransferHolder extends BaseHolder<Asset_AllocateInfo02> {
    
    @ViewInject(R.id.tv_code)
    private TextView tvCode;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_specification)
    private TextView tvSpec;
    @ViewInject(R.id.tv_storageDepartment)
    private TextView tvStorageDepartment;
    @ViewInject(R.id.tv_storageUser)
    private TextView tvStorageUser;
    @ViewInject(R.id.tv_user)
    private TextView tvUser;
    @ViewInject(R.id.tv_department)
    private TextView tvDepartment;
    
    public TransferHolder(LayoutInflater inflater, Asset_AllocateInfo02 t) {
        super(inflater, t);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_transfer, null);
    }
    
    @Override
    public void bindData(Asset_AllocateInfo02 t) {
        tvCode.setText(t.getMaterialCode());
        tvName.setText(t.getName());
        tvSpec.setText(t.getSpecification());
        tvStorageDepartment.setText(t.getDepartmentName());
        tvStorageUser.setText(t.getStorageUserName());
        tvUser.setText(t.getUserName());
        tvDepartment.setText(t.getDepartmentName());
    }
    
}
