package com.hiultra.assetManagerNeutral.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo02;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

public class StopHolder extends BaseHolder<Asset_StopInfo02> {
    @ViewInject(R.id.tv_code)
    private TextView tvCode;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_specification)
    private TextView tvSpec;
    @ViewInject(R.id.tv_model)
    private TextView tvModel;
    @ViewInject(R.id.tv_department)
    private TextView tvDepartment;
    
    @ViewInject(R.id.rl_item)
    private View vItem;
    
    public StopHolder(LayoutInflater inflater, Asset_StopInfo02 t) {
        super(inflater, t);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_maintain, null);
    }
    
    @Override
    public void bindData(Asset_StopInfo02 t) {
        tvCode.setText(t.getMaterialCode());
        tvName.setText(t.getName());
        tvSpec.setText(t.getSpecification());
        tvModel.setText(t.getModelName());
        tvDepartment.setText(t.getDepartmentName());
    }
    
}
