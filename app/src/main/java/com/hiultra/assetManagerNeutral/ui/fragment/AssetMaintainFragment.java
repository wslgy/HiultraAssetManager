package com.hiultra.assetManagerNeutral.ui.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.UHF.AsyncEpcWriter;
import com.hiultra.assetManagerNeutral.adapter.MaintainAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.DepartmentInfo;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 资产维护(为资产绑定标签,要和资产保养区分开)
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:29:21
 */
public class AssetMaintainFragment extends AsyncLoadFragment {
    
    private ListView lv;
    private Spinner mSpModel;
    private Spinner mSpDepart;
    @ViewInject(R.id.tv_count)
    private TextView tvCount;
    @ViewInject(R.id.tv_power)
    private TextView tvPower;
    @ViewInject(R.id.rl_loading)
    private View vLoad;
    
    private ArrayList<AssetMaterialInfo> baseList = new ArrayList<>();
    private ArrayList<AssetMaterialInfo> list = new ArrayList<>();
    private ArrayList<MaterialModelInfo> modelList = new ArrayList<>();
    private ArrayList<DepartmentInfo> departList = new ArrayList<>();
    
    private MaintainAdapter adapter;
    private ArrayAdapter<MaterialModelInfo> modelAdapter;
    private ArrayAdapter<DepartmentInfo> departAdapter;
    
    private DepartmentInfo currDepart;
    private MaterialModelInfo currModel;
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_maintain_asset, null);
        lv = (ListView) v.findViewById(R.id.lv);
        mSpModel = (Spinner) v.findViewById(R.id.sp_model);
        mSpDepart = (Spinner) v.findViewById(R.id.sp_department);
        initListView();
        initSpinner();
        return v;
    }
    
    private void initListView() {
        adapter = new MaintainAdapter(mActivity, list);
        lv.setAdapter(adapter);
        LvListener listener = new LvListener();
        lv.setOnItemClickListener(listener);
        lv.setOnItemLongClickListener(listener);
        // 取消默认状态选择器
        lv.setSelector(new ColorDrawable());
    }
    
    private void initSpinner() {
        SpinnerListener spListener = new SpinnerListener();
        MaterialModelInfo modelInfo = new MaterialModelInfo();
        modelInfo.setCode("-1");
        modelInfo.setName(Util.getString(R.string.text_all_model));
        modelList.add(0, modelInfo);
        modelAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpModel.setAdapter(modelAdapter);
        mSpModel.setOnItemSelectedListener(spListener);
        DepartmentInfo departInfo = new DepartmentInfo();
        departInfo.setCode("-1");
        departInfo.setName(Util.getString(R.string.text_all_depart));
        departList.add(0, departInfo);
        departAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, departList);
        departAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpDepart.setAdapter(departAdapter);
        mSpDepart.setOnItemSelectedListener(spListener);
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }
    
    @Override
    protected void initData() {
        super.initData();
//        if (app.checkServiceAndDevice()) {
//            if (app.device.setUhfPower(Constants.DEFAULT_POWER_WRITE, Constants.DEFAULT_TIME_WORKING,
//                    Constants.DEFAULT_TIME_SLEEP)) {
//                tvPower.setText(String.valueOf(Constants.DEFAULT_POWER_WRITE));
//            } else {
//                tvPower.setText(String.valueOf("error"));
//            }
//        }
    }
    
    @Override
    protected Object loadData() {
        baseList.clear();
        List<AssetMaterialInfo> l = DBTools.findAll(AssetMaterialInfo.class);
        if (l == null || l.isEmpty()) {
            ToastUtil.show(R.string.msg_noData);
            return null;
        }
        baseList.addAll(l);
        for (AssetMaterialInfo info : baseList) {
            info.setModelName(DaoCache.getModelName(info.getMaterialModelCode()));
            info.setDepartmentName(DaoCache.getDepartName(info.getDepartmentCode()));
        }
        ArrayList<MaterialModelInfo> modelInfo = DaoCache.getModelInfos();
        if (modelInfo != null) modelList.addAll(modelInfo);
        ArrayList<DepartmentInfo> departmentInfos = DaoCache.getDepartmentInfos();
        if (departmentInfos != null) departList.addAll(departmentInfos);
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSpModel.setAdapter(modelAdapter);
                mSpDepart.setAdapter(departAdapter);
            }
        });
        return baseList;
    }
    
    @Override
    public void onEpcRead(String epc) {
    }
    
    @Override
    protected void saveData() {
        try {
            DBTools.saveOrUpdate(baseList);
            LogUtils.e(getClass().getSimpleName() + "数据保存完成...");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    
    private void updateCount() {
        if (list == null || baseList == null) return;
        int s = list.size();
        int a = baseList.size();
        int c = 0;
        for (AssetMaterialInfo info : list) {
            if(!TextUtils.isEmpty(info.getBarCode())) c++;
        }
        tvCount.setText(String.valueOf(c + " / " + s + " / " + a));
    }
    
    @OnClick(R.id.btn_setPower)
    private void setPower(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.msg_set_power);
        View view = View.inflate(mActivity, R.layout.layout_setpower, null);
        final Spinner spPower = (Spinner) view.findViewById(R.id.uhf_power);
        final Spinner spWorking = (Spinner) view.findViewById(R.id.uhf_working);
        final Spinner spSleep = (Spinner) view.findViewById(R.id.uhf_sleep);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item);
        for (int i = 1; i <= 5; i++)
            adapterTime.add(String.valueOf(i * 100));
        spWorking.setAdapter(adapterTime);
        spWorking.setSelection(1);
        spSleep.setAdapter(adapterTime);
        spSleep.setSelection(2);
        ArrayAdapter<String> adapterPower = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item);
        for (int i = 28; i >= 5; i--)
            adapterPower.add(String.valueOf(i));
        spPower.setAdapter(adapterPower);
        spPower.setSelection(3);
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (app.checkServiceAndDevice()) {
                    int power = Integer.parseInt(spPower.getSelectedItem().toString()) * 10;
                    int working = Integer.parseInt(spWorking.getSelectedItem().toString());
                    int sleeping = Integer.parseInt(spSleep.getSelectedItem().toString());
                    if (app.device.setUhfPower(power, working, sleeping)) {
                        DevBeep.PlayOK();
                        ToastUtil.show(R.string.msg_set_power_ok);
                        tvPower.setText(String.valueOf(power/10));
                    } else {
                        DevBeep.PlayErr();
                        ToastUtil.show(R.string.msg_set_power_no);
                        tvPower.setText(String.valueOf("error"));
                    }
                }
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
    
    private void fitter() {
        if (currDepart == null || currModel == null) return;
        list.clear();
        String dCode = currDepart.getCode();
        String mCode = currModel.getCode();
        if (TextUtils.equals(dCode, "-1") && TextUtils.equals(mCode, "-1")) {
            list.addAll(baseList);
        } else if (TextUtils.equals(dCode, "-1") && !TextUtils.equals(mCode, "-1")) {
            for (AssetMaterialInfo info : baseList) {
                if (TextUtils.equals(info.getMaterialModelCode(), mCode)) list.add(info);
            }
        } else if (!TextUtils.equals(dCode, "-1") && TextUtils.equals(mCode, "-1")) {
            for (AssetMaterialInfo info : baseList) {
                if (TextUtils.equals(info.getDepartmentCode(), dCode)) list.add(info);
            }
        } else if (!TextUtils.equals(dCode, "-1") && !TextUtils.equals(mCode, "-1")) {
            for (AssetMaterialInfo info : baseList) {
                if (TextUtils.equals(info.getDepartmentCode(), dCode) && TextUtils.equals(info.getMaterialModelCode(), mCode))
                    list.add(info);
            }
        }
        updateCount();
        adapter.notifyDataSetChanged(false);
    }
    
    private class SpinnerListener implements OnItemSelectedListener {
        
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_department:
                    currDepart = departAdapter.getItem(position);
                    break;
                case R.id.sp_model:
                    currModel = modelAdapter.getItem(position);
                    break;
            }
            fitter();
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
        
    }
    
    private class LvListener implements OnItemClickListener, OnItemLongClickListener {
        
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            AssetMaterialInfo a = adapter.getItem(position);
            showDetail(a);
            return true;
        }
        
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AssetMaterialInfo a = adapter.getItem(position);
            if (TextUtils.isEmpty(a.getBarCode())) {
                binding(a);
            } else {
                unBinding(a);
            }
        }
    }
    
    /**
     * 绑定标签
     * 
     * @param a
     */
    public void binding(AssetMaterialInfo a) {
        new AsyncEpcWriter(app, a, baseList, new AsyncEpcWriter.EpcWriterObserver() {
            
            @Override
            public void begin() {
                vLoad.setVisibility(View.VISIBLE);
            }
            
            @Override
            public void onBindingSuccess() {
                vLoad.setVisibility(View.GONE);
                ToastUtil.show(R.string.msg_write_success);
                updateCount();
                adapter.notifyDataSetChanged(false);
            }
            
            @Override
            public void onBindingFailed(AssetMaterialInfo a) {
                vLoad.setVisibility(View.GONE);
                if (a != null) {
                    unBinding(a);
                }
                ToastUtil.show(R.string.msg_write_failed);
            }
        }).start();;
    }
    
    public void unBinding(final AssetMaterialInfo a) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.title_confirm);
        View view = View.inflate(mActivity, R.layout.detail_unbinding, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvBarCode = (TextView) view.findViewById(R.id.tv_barCode);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        tvCode.setText(a.getCode() == null ? "" : a.getCode());
        tvBarCode.setText(a.getBarCode() == null ? "" : a.getBarCode());
        tvName.setText(a.getName() == null ? "" : a.getName());
        tvSpec.setText(a.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        // 取消
        builder.setNeutralButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                a.setBarCode(String.valueOf(""));
                updateAsset(a);
                updateCount();
                adapter.notifyDataSetChanged(false);
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
    
    /**
     * 显示资产详情
     * 
     * @param item
     */
    public void showDetail(final AssetMaterialInfo a) {
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
        tvCode.setText(a.getCode());
        tvName.setText(a.getName() == null ? "" : a.getName());
        tvSpec.setText(a.getSpecification() == null ? "" : a.getSpecification());
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
    
    /**
     * 更新资产主表
     * 
     * @param a
     */
    protected void updateAsset(AssetMaterialInfo a) {
        a.setIfuodate("0");
        try {
            DBTools.update(a);
            ToastUtil.show(R.string.text_update_success);
        } catch (DbException e) {
            ToastUtil.show(R.string.text_update_failed);
            e.printStackTrace();
        }
    }
    
}
