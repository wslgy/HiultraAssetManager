package com.hiultra.assetManagerNeutral.holder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo02;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

/**
 * 盘点模块Holder
 * 
 * @author Tom
 * @date 2016年9月19日
 * @Time 下午2:43:43
 */
public class RepairHolder extends BaseHolder<Asset_RepairInfo02> {
    
    @ViewInject(R.id.tv_code)
    private TextView tvCode;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_specification)
    private TextView tvSpecification;
    @ViewInject(R.id.tv_model)
    private TextView tvModel;
    @ViewInject(R.id.tv_department)
    private TextView tvDepartment;
    @ViewInject(R.id.tv_warehouse)
    private TextView tvWarehouse;
    @ViewInject(R.id.tv_cost)
    private TextView tvCost;
    @ViewInject(R.id.tv_content)
    private TextView tvContent;
    
    @ViewInject(R.id.rl_item_check)
    private RelativeLayout rl;
    
    private String result_loss = Util.getString(R.string.checkResult_loss);
    
    public RepairHolder(LayoutInflater inflater, Asset_RepairInfo02 info) {
        super(inflater, info);
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.item_repair, null);
        return v;
    }
    
    @Override
    public void bindData(Asset_RepairInfo02 t) {
        tvCode.setText(t.getMaterialCode());
        tvName.setText(t.getName());
        tvSpecification.setText(t.getSpecification());
        tvModel.setText(t.getModelName());
        tvDepartment.setText(t.getDepartmentName());
        tvWarehouse.setText(t.getWarehouseName());
        tvCost.setText(String.valueOf(t.getRepairCost()));
        tvContent.setText(t.getContents());
        String s = t.getIsRepair();
        // 设置背景颜色
        if (TextUtils.isEmpty(s)) rl.setBackgroundResource(R.color.light_gray);
        else rl.setBackgroundResource(android.R.color.holo_blue_light);
    }
    
}
