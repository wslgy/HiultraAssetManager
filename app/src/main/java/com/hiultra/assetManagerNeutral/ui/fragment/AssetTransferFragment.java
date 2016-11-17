package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.adapter.TransferAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.dao.table.DepartmentInfo;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.ui.view.SwipeDismissListView;
import com.hiultra.assetManagerNeutral.ui.view.SwipeDismissListView.OnDismissCallback;
import com.hiultra.assetManagerNeutral.util.DateUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 资产调拨
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:29:39
 */
public class AssetTransferFragment extends AsyncLoadFragment {
    
    private SwipeDismissListView lv;
    @ViewInject(R.id.tv_number)
    private TextView tvNumber;
    @ViewInject(R.id.sp_department)
    private Spinner spDepartment;
    @ViewInject(R.id.sp_user)
    private Spinner spUser;
    @ViewInject(R.id.sp_storageDepartment)
    private Spinner spStorageDepartment;
    @ViewInject(R.id.sp_storageUser)
    private Spinner spStorageUser;
    @ViewInject(R.id.sp_warehouse)
    private Spinner spWarehouse;
    @ViewInject(R.id.tv_count)
    private TextView tvCount;
    @ViewInject(R.id.btn_finish)
    private Button btnFirish;
    @ViewInject(R.id.et_remark)
    private EditText etRemark;
    
    
    private TransferAdapter adapter;
    private Asset_AllocateInfo01 info01;
    private ArrayList<Asset_AllocateInfo02> info02List = new ArrayList<>();
    private ArrayAdapter<DepartmentInfo> departAdapter;
    private ArrayAdapter<UserInfo> userAdapter;
    private ArrayAdapter<UserInfo> storageUserAdapter;
    private ArrayAdapter<WarehouseInfo> warehouseAdapter;
    
    private ArrayList<UserInfo> baseU = new ArrayList<>();
    private ArrayList<UserInfo> userInfos = new ArrayList<>();
    private ArrayList<UserInfo> storageUserInfos = new ArrayList<>();
    
    private static Object LOCK = new Object();
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_transfer_asset, null);
        lv = (SwipeDismissListView) v.findViewById(R.id.lv);
        spDepartment = (Spinner) v.findViewById(R.id.sp_department);
        spUser = (Spinner) v.findViewById(R.id.sp_user);
        spStorageDepartment = (Spinner) v.findViewById(R.id.sp_storageDepartment);
        spStorageUser = (Spinner) v.findViewById(R.id.sp_storageUser);
        spWarehouse = (Spinner) v.findViewById(R.id.sp_warehouse);
        initListView();
        SpinnerListener spinnerListener = new SpinnerListener();
        initSpinner(spinnerListener);
        return v;
    }
    
    private void initListView() {
        adapter = new TransferAdapter(mActivity, info02List);
        LvListener listener = new LvListener();
        lv.setAdapter(adapter);
        lv.setOnDismissCallback(listener);
        lv.setOnItemClickListener(listener);
    }
    
    private class SpinnerListener implements OnItemSelectedListener {
        
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_department:
                    String c = departAdapter.getItem(position).getCode();
                    info01.setNewDepartmentCode(c);
                    userInfos.clear();
                    for (UserInfo info : baseU) {
                        if(TextUtils.equals(info.getDepartmentCode(), c)) userInfos.add(info);
                    }
                    userAdapter.notifyDataSetChanged();
                    break;
                case R.id.sp_user:
                    info01.setNewUserCode(userAdapter.getItem(position).getCode());
                    break;
                case R.id.sp_storageDepartment:
                    String sc = departAdapter.getItem(position).getCode();
                    info01.setNewStorageDepartmentCode(sc);
                    storageUserInfos.clear();
                    for (UserInfo info : baseU) {
                        if(TextUtils.equals(info.getDepartmentCode(), sc)) storageUserInfos.add(info);
                    }
                    storageUserAdapter.notifyDataSetChanged();
                    break;
                case R.id.sp_storageUser:
                    info01.setNewStorageCode(userAdapter.getItem(position).getCode());
                    break;
                case R.id.sp_warehouse:
                    info01.setNewWarehouseCode(warehouseAdapter.getItem(position).getCode());
                    break;
            }
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
        
    }
    
    private void initSpinner(SpinnerListener spinnerListener) {
        ArrayList<DepartmentInfo> departmentInfos = DaoCache.getDepartmentInfos();
        if (departmentInfos != null && !departmentInfos.isEmpty()) {
            departAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, departmentInfos);
            departAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDepartment.setAdapter(departAdapter);
            spStorageDepartment.setAdapter(departAdapter);
        }
        baseU  = DaoCache.getUserInfos();
        if (baseU  != null && !baseU .isEmpty()) {
            DepartmentInfo d = (DepartmentInfo) spDepartment.getSelectedItem();
            if(d != null) {
                for (UserInfo info : baseU ) {
                    if(TextUtils.equals(info.getDepartmentCode(), d.getCode())) userInfos.add(info);
                }
            }
            userAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, userInfos);
            userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUser.setAdapter(userAdapter);
            
            DepartmentInfo sd = (DepartmentInfo) spStorageDepartment.getSelectedItem();
            if(sd != null) {
                for (UserInfo info : baseU ) {
                    if(TextUtils.equals(info.getDepartmentCode(), sd.getCode())) storageUserInfos.add(info);
                }
            }
            storageUserAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, storageUserInfos);
            storageUserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStorageUser.setAdapter(storageUserAdapter);
        }
        ArrayList<WarehouseInfo> warehouseInfos = DaoCache.getWarehouseInfos();
        if (warehouseInfos != null && !warehouseInfos.isEmpty()) {
            warehouseAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, warehouseInfos);
            warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spWarehouse.setAdapter(warehouseAdapter);
        }
        spDepartment.setOnItemSelectedListener(spinnerListener);
        spStorageDepartment.setOnItemSelectedListener(spinnerListener);
        spUser.setOnItemSelectedListener(spinnerListener);
        spStorageUser.setOnItemSelectedListener(spinnerListener);
        spWarehouse.setOnItemSelectedListener(spinnerListener);
    }
    
    @Override
    protected Object loadData() {
        info02List.clear();
        info01 = DBTools.findFirst(Selector.from(Asset_AllocateInfo01.class).where("ifuodate", "=", null));
        if (info01 == null) { // 创建
            info01 = new Asset_AllocateInfo01();
            info01.setID(Util.createId());
            info01.setBatchNumber(Util.getBatchNumber());
            String u = "";
            if(Varible.userInfo != null) u = Varible.userInfo.getCode();
            info01.setCreateDateTime(DateUtil.GetLocalDateTime());
            info01.setCreateOperater(u);
        }
        ArrayList<Asset_AllocateInfo02> list = DBTools
                .findAll(Asset_AllocateInfo02.class, "BatchNumber", info01.getBatchNumber());
        if (list != null && !list.isEmpty()) info02List.addAll(list);
        for (Asset_AllocateInfo02 info : info02List) {
            AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", info.getMaterialCode());
            if (asset == null) continue;
            info.setOldDepartmentCode(asset.getDepartmentCode());
            info.setOldUserCode(asset.getUserCode());
            info.setOldStorageCode(asset.getStorageCode());
            info.setOldStorageDepartmentCode(asset.getStorageDepartmentCode());
            info.setOldWarehouseCode(asset.getWarehouseCode());
            
            info.setName(asset.getName());
            info.setSpecification(asset.getSpecification());
            info.setDepartmentName(DaoCache.getDepartName(asset.getDepartmentCode()));
            info.setUserName(DaoCache.getUserName(asset.getUserCode()));
            info.setStorageDepartmentName(DaoCache.getDepartName(asset.getStorageDepartmentCode()));
            info.setStorageUserName(DaoCache.getUserName(asset.getStorageCode()));
        }
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNumber.setText(info01.getBatchNumber());
                etRemark.setText(info01.getRemark() == null ? "" : info01.getRemark());
                DepartmentInfo departinfo = DaoCache.getDepartinfo(info01.getNewDepartmentCode());
                spDepartment.setSelection(departinfo != null ? departAdapter.getPosition(departinfo) : 0);
                DepartmentInfo d = DaoCache.getDepartinfo(info01.getNewStorageDepartmentCode());
                spStorageDepartment.setSelection(d != null ? departAdapter.getPosition(d) : 0);
                UserInfo userinfo = DaoCache.getUserinfo(info01.getNewUserCode());
                spUser.setSelection(userinfo != null ? userAdapter.getPosition(userinfo) : 0);
                UserInfo u = DaoCache.getUserinfo(info01.getNewStorageCode());
                spStorageUser.setSelection(u != null ? userAdapter.getPosition(userinfo) : 0);
                WarehouseInfo warehouseInfo = DaoCache.getWarehouseInfo(info01.getNewWarehouseCode());
                spWarehouse.setSelection(warehouseInfo != null ? warehouseAdapter.getPosition(warehouseInfo) : 0);
                adapter.notifyDataSetChanged();
                updateCount();
            }
        });
        return info01;
    }
    
    boolean isCheck = false;
    
    @Override
    public void onEpcRead(String epc) {
        synchronized (LOCK) {
            if (isCheck) return;
            isCheck = true;
            boolean isExit = false;
            if (info02List.size() != 0) {
                for (int i = 0; i < info02List.size(); i++) {
                    Asset_AllocateInfo02 info02 = info02List.get(i);
                    if (TextUtils.equals(info02.getMaterialCode(), epc)) {
                        isExit = true;
                        break;
                    }
                }
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
            final Asset_AllocateInfo02 info = new Asset_AllocateInfo02();
            info.setID(Util.createId());
            info.setBatchNumber(info01.getBatchNumber());
            info.setMaterialCode(asset.getBarCode());
            info.setOldDepartmentCode(asset.getDepartmentCode());
            info.setOldUserCode(asset.getUserCode());
            info.setOldStorageCode(asset.getStorageCode());
            info.setOldStorageDepartmentCode(asset.getStorageDepartmentCode());
            info.setOldWarehouseCode(asset.getWarehouseCode());
            
            info.setName(asset.getName());
            info.setSpecification(asset.getSpecification());
            info.setDepartmentName(DaoCache.getDepartName(asset.getDepartmentCode()));
            info.setUserName(DaoCache.getUserName(asset.getUserCode()));
            info.setStorageDepartmentName(DaoCache.getDepartName(asset.getStorageDepartmentCode()));
            info.setStorageUserName(DaoCache.getUserName(asset.getStorageCode()));
            info02List.add(info);
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    updateCount();
                    isCheck = false;
                }
            });
        }
    }
    
    @Override
    protected void saveData() {
        if(info01 == null || info02List == null || info02List.isEmpty()) return;
        try {
            DBTools.saveOrUpdate(info01);
            DBTools.saveOrUpdate(info02List);
            LogUtils.e(getClass().getSimpleName() + "保存成功...");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    
    private void updateCount() {
        if (info02List == null || info02List.isEmpty()) {
            tvCount.setText(String.valueOf(0));
        } else {
            tvCount.setText(String.valueOf(info02List.size()));
        }
    }
    
    @OnClick(R.id.btn_finish)
    private void confirmTransfer(View v) {
        if (info01 == null) {
            ToastUtil.show("没有调拨单");
            return;
        }
        if (info02List == null || info02List.isEmpty()) {
            ToastUtil.show("调拨单为空");
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
                info01.setAllocateDate(date);
                info01.setRemark(etRemark.getText().toString().trim());
                for (Asset_AllocateInfo02 info02 : info02List) {
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
    
    protected void updateAsset(Asset_AllocateInfo01 info01, Asset_AllocateInfo02 info02) {
        AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", info02.getMaterialCode());
        if(asset == null) return;
        asset.setUpdateDateTime(DateUtil.GetLocalDateTime());
        asset.setUpdateOperater(Varible.userInfo == null ? "" : Varible.userInfo.getCode());
        asset.setDepartmentCode(info01.getNewDepartmentCode());
        asset.setUserCode(info01.getNewUserCode());
        asset.setWarehouseCode(info01.getNewWarehouseCode());
        asset.setStorageDepartmentCode(info01.getNewStorageDepartmentCode());
        asset.setStorageCode(info01.getNewStorageCode());
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
            synchronized (LOCK) {
                if(dismissPosition >= info02List.size()) return;
                Asset_AllocateInfo02 item = adapter.getItem(dismissPosition);
                try {
                    DBTools.delete(Asset_AllocateInfo02.class, WhereBuilder.b("ID", "=", item.getID()));
                    info02List.remove(item);
                    adapter.notifyDataSetChanged(false);
                    updateCount();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
        
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Asset_AllocateInfo02 item = adapter.getItem(position);
            AssetMaterialInfo asset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", item.getMaterialCode());
            if(asset == null) return;
            showDetail(item, asset);
        }
        
    }

    public void showDetail(Asset_AllocateInfo02 item, AssetMaterialInfo a) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.title_infoDetail);
        View view = View.inflate(mActivity, R.layout.detail_asset, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        tvCode.setText(item.getMaterialCode());
        tvName.setText(item.getName() == null ? "" : a.getName());
        tvSpec.setText(item.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        // 确认
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
    
}
