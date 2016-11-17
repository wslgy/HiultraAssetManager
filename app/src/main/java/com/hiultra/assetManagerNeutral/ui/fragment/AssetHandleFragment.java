package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.adapter.HandleAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo02;
import com.hiultra.assetManagerNeutral.dao.table.SystemArgument;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.ui.view.SwipeDismissListView;
import com.hiultra.assetManagerNeutral.ui.view.SwipeDismissListView.OnDismissCallback;
import com.hiultra.assetManagerNeutral.util.DateUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 资产处置
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:30:52
 */
public class AssetHandleFragment extends AsyncLoadFragment {
    
    @ViewInject(R.id.tv_number)
    private TextView tvNumber;
    @ViewInject(R.id.et_remark_check)
    private EditText etRemark;
    @ViewInject(R.id.tv_count)
    private TextView tvCount;
    @ViewInject(R.id.et_income)
    private EditText etIncome;
    @ViewInject(R.id.et_cost)
    private EditText etCost;
    @ViewInject(R.id.sp_handleType)
    private Spinner spType;
    
    private SwipeDismissListView lv;
    
    private HandleAdapter adapter;
    
    private Asset_HandleInfo01 info01;
    private ArrayList<Asset_HandleInfo02> info02List = new ArrayList<>();
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_handle_asset, null);
        lv = (SwipeDismissListView) v.findViewById(R.id.lv);
        spType = (Spinner) v.findViewById(R.id.sp_handleType);
        initLv();
        initSpinner();
        return v;
    }
    
    private void initLv() {
        adapter = new HandleAdapter(mActivity, info02List);
        lv.setAdapter(adapter);
        LvListener listener = new LvListener();
        lv.setOnItemClickListener(listener);
        lv.setOnDismissCallback(listener);
    }
    
    private void initSpinner() {
        typeList = DBTools.findAll(SystemArgument.class, "TypeID", 5);
        if (typeList != null && !typeList.isEmpty()) {
            spAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, typeList);
            spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spType.setAdapter(spAdapter);
            spType.setOnItemSelectedListener(new OnItemSelectedListener() {
                
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (info01 != null) info01.setHandleType(spAdapter.getItem(position).getID());
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
                
            });
        } else {
            ToastUtil.show("请先同步基础信息");
        }
    }
    
    @Override
    protected Object loadData() {
        info02List.clear();
        info01 = DBTools.findFirst(Selector.from(Asset_HandleInfo01.class).where("ifuodate", "=", null));
        if (info01 == null) {
            info01 = new Asset_HandleInfo01();
            info01.setID(Util.createId());
            info01.setBatchNumber(Util.getBatchNumber());
            String u = "";
            if(Varible.userInfo != null) u = Varible.userInfo.getCode();
            info01.setCreateDateTime(DateUtil.GetLocalDateTime());
            info01.setCreateOperater(u);
        }
        if (info01.getHandleType() != 0) {
            for (int i = 0; i < typeList.size(); i++) {
                SystemArgument type = typeList.get(i);
                if (type.getID() == info01.getHandleType()) spType.setSelection(i);
            }
        }
        ArrayList<Asset_HandleInfo02> list = DBTools.findAll(Asset_HandleInfo02.class, "BatchNumber", info01.getBatchNumber());
        if (list != null && !list.isEmpty()) {
            info02List.addAll(list);
        }
        for (Asset_HandleInfo02 info : info02List) {
            AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", info.getMaterialCode());
            if (asset == null) continue;
            info.setName(asset.getName());
            info.setSpecification(asset.getSpecification());
            info.setDepartmentName(DaoCache.getDepartName(asset.getDepartmentCode()));
            info.setModelName(DaoCache.getModelName(asset.getMaterialModelCode()));
        }
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etRemark.setText(info01.getRemark() == null ? "" : info01.getRemark());
                tvNumber.setText(info01.getBatchNumber());
                adapter.notifyDataSetChanged();
                updateCount();
            }
        });
        return info01;
    }
    
    boolean isCheck = false;
    private ArrayAdapter<SystemArgument> spAdapter;
    private ArrayList<SystemArgument> typeList;
    
    @Override
    public void onEpcRead(String epc) {
        if (isCheck) return;
        isCheck = true;
        boolean isExit = false;
        for (int i = 0; i < info02List.size(); i++) {
            Asset_HandleInfo02 info02 = info02List.get(i);
            if (TextUtils.equals(info02.getMaterialCode(), epc)) isExit = true;
        }
        if (isExit) {
            isCheck = false;
            return;
        }
        AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", epc);
        if (asset == null) {
            isCheck = false;
            return;
        }
        Asset_HandleInfo02 info = new Asset_HandleInfo02();
        info.setID(Util.createId());
        info.setBatchNumber(info01.getBatchNumber());
        info.setMaterialCode(asset.getBarCode());
        
        info.setName(asset.getName());
        info.setSpecification(asset.getSpecification());
        info.setDepartmentName(DaoCache.getDepartName(asset.getDepartmentCode()));
        info.setModelName(DaoCache.getModelName(asset.getMaterialModelCode()));
        info02List.add(info);
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged(false);
                updateCount();
                isCheck = false;
            }
        });
    }
    
    @Override
    protected void saveData() {
        SystemArgument item = (SystemArgument) spType.getSelectedItem();
        if (item != null) info01.setHandleType(item.getID());
        try {
            DBTools.saveOrUpdate(info01);
            DBTools.saveOrUpdate(info02List);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    
    @OnClick(R.id.btn_finish)
    public void confirmHandle(View v) {
        if (info01 == null) {
            ToastUtil.show("没有报废单");
            return;
        }
        if (info02List == null || info02List.isEmpty()) {
            ToastUtil.show("报废单为空");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.title_confirm_finish);
        builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = "";
                if (Varible.userInfo != null) user = Varible.userInfo.getCode();
                String date = DateUtil.GetLocalDateTime();
                info01.setIfuodate("0");
                info01.setUpdateOperater(user);
                info01.setUpdateDateTime(date);
                info01.setHandleDate(date);
                info01.setRemark(etRemark.getText().toString().trim());
                for (Asset_HandleInfo02 info02 : info02List) {
                    info02.setIfuodate("0");
                    info02.setUpdateDateTime(date);
                    info02.setUpdateOperater(user);
                    updateAsset(info01, info02);
                }
                // 保存盘点表数据
                saveData();
                ToastUtil.show("保存成功");
                // 刷新界面
                loadingPage.loadDataAndRefreshPage();
            }
        });
        builder.show();
    }
    
    protected void updateAsset(Asset_HandleInfo01 info01, Asset_HandleInfo02 info02) {
        AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", info02.getMaterialCode());
        if (asset == null) return;
        String user = "";
        if (Varible.userInfo != null) user = Varible.userInfo.getCode();
        String date = DateUtil.GetLocalDateTime();
        asset.setUpdateDateTime(date);
        asset.setUpdateOperater(user);
        asset.setAssetStatus("已处置");
        asset.setIsChanged(1);
        try {
            DBTools.saveOrUpdate(asset);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    
    private class LvListener implements OnItemClickListener, OnDismissCallback {
        
        @Override
        public void onDismiss(int dismissPosition) {
            Asset_HandleInfo02 item = adapter.getItem(dismissPosition);
            info02List.remove(item);
            adapter.notifyDataSetChanged(false);
            updateCount();
        }
        
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Asset_HandleInfo02 item = adapter.getItem(position);
            AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", item.getMaterialCode());
            if (asset == null) return;
            showDetail(item, asset);
        }
        
    }
    
    private void updateCount() {
        if (info02List == null || info02List.isEmpty()) {
            tvCount.setText("0");
        } else {
            tvCount.setText(String.valueOf(info02List.size()));
        }
    }
    
    public void showDetail(final Asset_HandleInfo02 item, AssetMaterialInfo a) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.title_infoDetail);
        View view = View.inflate(mActivity, R.layout.detail_scrap, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        final EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
        tvCode.setText(item.getMaterialCode());
        tvName.setText(item.getName() == null ? "" : a.getName());
        tvSpec.setText(item.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        etRemark.setText(item.getRemark() == null ? "" : item.getRemark());
        builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 确认
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String remark = etRemark.getText().toString().trim();
                item.setRemark(remark);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
    
}
