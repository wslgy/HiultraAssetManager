package com.hiultra.assetManagerNeutral.holder;

import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 资产维护条目填充器
 * @author Tom
 * @date 2016年11月2日
 * @Time 下午3:20:40
 */
public class MaintainHolder extends BaseHolder<AssetMaterialInfo> {
    
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
    
    public MaintainHolder(LayoutInflater inflater, AssetMaterialInfo t) {
        super(inflater, t);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_maintain, null);
    }
    
    @Override
    public void bindData(AssetMaterialInfo t) {
        tvCode.setText(t.getCode());
        tvName.setText(t.getName());
        tvSpec.setText(t.getSpecification());
        tvModel.setText(t.getModelName());
        tvDepartment.setText(t.getDepartmentName());
        if(TextUtils.isEmpty(t.getBarCode())) {
            vItem.setBackgroundResource(R.color.light_gray);
        } else {
            vItem.setBackgroundResource(android.R.color.holo_blue_light);
        }
    }
    
}
