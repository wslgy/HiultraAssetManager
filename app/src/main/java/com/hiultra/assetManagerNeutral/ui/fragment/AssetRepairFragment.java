package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiultra.assetManagerNeutral.adapter.RepairAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo02;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.dao.table.WarehouseInfo;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.util.DateUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 资产维修
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:31:06
 */
public class AssetRepairFragment extends AsyncLoadFragment {
    
    @ViewInject(R.id.tv_date)
    private TextView tvDate;
    @ViewInject(R.id.et_remark_check)
    private EditText etRemark;
    @ViewInject(R.id.tv_all)
    private TextView tvAll;
    @ViewInject(R.id.tv_check)
    private TextView tvCheck;
    @ViewInject(R.id.tv_uncheck)
    private TextView tvUncheck;
    @ViewInject(R.id.tv_count)
    private TextView tvCount;
    
    private Spinner spTheme;
    private ListView lv;
    
    // 缓存数据
    HashMap<Asset_RepairInfo01, ArrayList<Asset_RepairInfo02>> map = new HashMap<>();
    
    /* Spinner */
    private ArrayList<Asset_RepairInfo01> info01List = new ArrayList<>();
    private ArrayAdapter<Asset_RepairInfo01> spAdapter;
    
    /* ListView */
    private ArrayList<Asset_RepairInfo02> info02List = new ArrayList<>();
    /** 最终由这个集合去填充ListView */
    private ArrayList<Asset_RepairInfo02> dataList = new ArrayList<>();
    private RepairAdapter adapter;
    /** 当前盘点主表 */
    private Asset_RepairInfo01 currentInfo01;
    
    /**
     * 数据展示筛选
     */
    enum STATE_SHOW {
        SHOW_ALL, // 显示全部
        SHOW_CHECK, // 显示盘点到的数据
        SHOW_UNCHECK // 显示未盘点的数据
    }
    
    private STATE_SHOW currentShow = STATE_SHOW.SHOW_ALL;
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_repair_asset, null);
        spTheme = (Spinner) v.findViewById(R.id.sp_theme_check);
        etRemark = (EditText) v.findViewById(R.id.et_remark_check);
        lv = (ListView) v.findViewById(R.id.lv_check);
        tvAll = (TextView) v.findViewById(R.id.tv_all);
        tvCheck = (TextView) v.findViewById(R.id.tv_check);
        // 初始化Spinner
        initSpinner();
        // 初始化ListView
        initListview();
        return v;
    }
    
    private void initSpinner() {
        spAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, info01List);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTheme.setAdapter(spAdapter);
        spTheme.setOnItemSelectedListener(new SpinnerListener());
    }
    
    private void initListview() {
        adapter = new RepairAdapter(mActivity, dataList);
        lv.setAdapter(adapter);
        ListViewListener lvListener = new ListViewListener();
        lv.setOnItemClickListener(lvListener);
    }
    
    private void clearCache() {
        if (info01List != null) info01List.clear();
        if (info02List != null) info02List.clear();
        if (map != null) map.clear();
    }
    
    @Override
    protected Object loadData() {
        clearCache();
        ArrayList<Asset_RepairInfo01> list = DBTools
                .findAll(Selector.from(Asset_RepairInfo01.class).where("ifuodate", "=", null));
        if (list == null || list.isEmpty()) {
            ToastUtil.show("没有维修单,请先同步数据");
            return null;
        }
        info01List.addAll(list);
        for (Asset_RepairInfo01 info01 : info01List) {
            ArrayList<Asset_RepairInfo02> list2 = DBTools.findAll(Selector.from(Asset_RepairInfo02.class).where("BatchNumber",
                    "=", info01.getBatchNumber()));
            for (Asset_RepairInfo02 info02 : list2) {
                AssetMaterialInfo assetInfo = DBTools.findFirst(Selector.from(AssetMaterialInfo.class).where("Code", "=",
                        info02.getMaterialCode()));
                if (assetInfo != null) {
                    info02.setName(assetInfo.getName());
                    info02.setSpecification(assetInfo.getSpecification());
                    WarehouseInfo warehouseInfo = DaoCache.getWarehouseInfo(assetInfo.getWarehouseCode());
                    if (warehouseInfo != null) info02.setWarehouseName(warehouseInfo.getName());
                    MaterialModelInfo modelinfo = DaoCache.getModelinfo(assetInfo.getMaterialModelCode());
                    if (modelinfo != null) info02.setModelName(modelinfo.getName());
                    info02.setDepartmentName(DaoCache.getDepartName(assetInfo.getDepartmentCode()));
                }
            }
            map.put(info01, list2);
        }
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spTheme.setAdapter(spAdapter);
                // spAdapter.notifyDataSetChanged();
            }
        });
        return info01List;
    }
    
    @OnClick(R.id.tv_all)
    public void showAll(View v) {
        if (currentShow == STATE_SHOW.SHOW_ALL) return;
        tvAll.setBackgroundResource(R.color.bg_lightGray);
        tvCheck.setBackgroundResource(R.color.bg_darkGray);
        tvUncheck.setBackgroundResource(R.color.bg_darkGray);
        currentShow = STATE_SHOW.SHOW_ALL;
        notifyData(true);
    }
    
    @OnClick(R.id.tv_check)
    public void showCheck(View v) {
        if (currentShow == STATE_SHOW.SHOW_CHECK) return;
        tvAll.setBackgroundResource(R.color.bg_darkGray);
        tvCheck.setBackgroundResource(R.color.bg_lightGray);
        tvUncheck.setBackgroundResource(R.color.bg_darkGray);
        currentShow = STATE_SHOW.SHOW_CHECK;
        notifyData(true);
    }
    
    @OnClick(R.id.tv_uncheck)
    public void showUnCheck(View v) {
        if (currentShow == STATE_SHOW.SHOW_UNCHECK) return;
        tvAll.setBackgroundResource(R.color.bg_darkGray);
        tvCheck.setBackgroundResource(R.color.bg_darkGray);
        tvUncheck.setBackgroundResource(R.color.bg_lightGray);
        currentShow = STATE_SHOW.SHOW_UNCHECK;
        notifyData(true);
    }
    
    /**
     * 展示数据
     */
    public void notifyData(boolean b) {
        dataList.clear();
        if (info02List != null && !info02List.isEmpty()) {
            if (currentShow == STATE_SHOW.SHOW_CHECK) {
                for (Asset_RepairInfo02 info02 : info02List) {
                    if (!TextUtils.isEmpty(info02.getIsRepair())) dataList.add(info02);
                }
            } else if (currentShow == STATE_SHOW.SHOW_UNCHECK) {
                for (Asset_RepairInfo02 info02 : info02List) {
                    if (TextUtils.isEmpty(info02.getIsRepair())) dataList.add(info02);
                }
            } else {
                dataList.addAll(info02List);
            }
        }
        updateCount();
        adapter.notifyDataSetChanged(b);
    }
    
    /**
     * 更新数量
     * 
     * @param list
     */
    private void updateCount() {
        if (info02List == null || info02List.isEmpty()) {
            tvCount.setText("0/0");
            return;
        }
        int totalCount = info02List.size();
        int checkCount = 0;
        for (Asset_RepairInfo02 info02 : info02List) {
            if (!TextUtils.isEmpty(info02.getIsRepair())) checkCount++;
        }
        tvCount.setText(checkCount + "/" + totalCount);
    }
    
    boolean show = false;
    
    @Override
    public void onEpcRead(String epc) {
        if (show) return;
        show = true;
        boolean isExit = false;
        Asset_RepairInfo02 info = null;
        for (Asset_RepairInfo02 info02 : info02List) {
            if (TextUtils.equals(epc, info02.getMaterialCode())) {
                if (TextUtils.isEmpty(info02.getIsRepair())) {
                    isExit = true;
                    info = info02;
                }
                break;
            }
        }
        if (!isExit) {
            show = false;
            return;
        }
        final Asset_RepairInfo02 i = info;
        final AssetMaterialInfo a = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", epc);
        if (a == null) {
            show = false;
            return;
        }
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemClick(i, a, true);
            }
        });
    }
    
    @Override
    protected void saveData() {
        if (info01List == null || info01List.isEmpty()) return;
        String r = etRemark.getText().toString().trim();
        if (currentInfo01 != null && r != null) currentInfo01.setRemark(r);
        for (Asset_RepairInfo01 info01 : info01List) {
            try {
                DBTools.saveOrUpdate(info01);
                ArrayList<Asset_RepairInfo02> list = map.get(info01);
                DBTools.saveOrUpdate(list);
            } catch (DbException e) {
                LogUtils.e(info01.getBatchNumber() + "...表保存失败");
                e.printStackTrace();
            }
        }
        LogUtils.e(getClass().getSimpleName() + "...保存数据完成");
    }
    
    @OnClick(R.id.btn_finish)
    public void confirmRepair(View v) {
        if (currentInfo01 == null) {
            ToastUtil.show("没有维修单");
            return;
        }
        final ArrayList<Asset_RepairInfo02> list = map.get(currentInfo01);
        if (list == null || list.isEmpty()) {
            ToastUtil.show("维修单为空");
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
                String time = DateUtil.GetLocalDateTime();
                currentInfo01.setIfuodate("0");
                currentInfo01.setRepairStatus("已完成");
                if (Varible.userInfo != null) currentInfo01.setUpdateOperater(Varible.userInfo.getCode());
                currentInfo01.setUpdateDateTime(time);
                for (Asset_RepairInfo02 checkInfo02 : list) {
                    checkInfo02.setIfuodate("0");
                    checkInfo02.setUpdateDateTime(time);
                    if (Varible.userInfo != null) checkInfo02.setUpdateOperater(Varible.userInfo.getCode());
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
     
    /** Spinner监听器 */
    private class SpinnerListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentInfo01 = spAdapter.getItem(position);
            tvDate.setText(currentInfo01.getRepairEndDate());
            etRemark.setText(currentInfo01.getRemark());
            // 加载从表数据
            info02List.clear();
            ArrayList<Asset_RepairInfo02> list = map.get(currentInfo01);
            if (list == null || list.isEmpty()) {
                ToastUtil.show("空的盘点单");
                notifyData(true);
                updateCount();
                return;
            }
            info02List.addAll(list);
            updateCount();
            notifyData(true);
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
    
    /** ListView监听器 */
    private class ListViewListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Asset_RepairInfo02 i = adapter.getItem(position);
            if (i == null) return;
            AssetMaterialInfo a = DBTools.findFirst(Selector.from(AssetMaterialInfo.class).where("Code", "=",
                    i.getMaterialCode()));
            if (a == null) {
                ToastUtil.show("没有相关数据");
                return;
            }
            itemClick(i, a, false);
        }
    }
    
    private void itemClick(final Asset_RepairInfo02 i, AssetMaterialInfo a, final boolean isScan) {
        show = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("维修详情");
        View view = View.inflate(mActivity, R.layout.detail_repair, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        final EditText etCost = (EditText) view.findViewById(R.id.et_cost);
        final EditText etContent = (EditText) view.findViewById(R.id.et_content);
        final Spinner spIsRepair = (Spinner) view.findViewById(R.id.sp_isRepair);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, new String[] { "是",
                "否" });
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIsRepair.setAdapter(spAdapter);
        tvCode.setText(i.getMaterialCode());
        tvName.setText(i.getName() == null ? "" : a.getName());
        tvSpec.setText(a.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        etCost.setText(String.valueOf(i.getRepairCost()));
        etContent.setText(i.getContents() == null ? "" : i.getContents());
//        if (i.getIsRepair() != null) spIsRepair.setSelection(spAdapter.getPosition(i.getIsRepair()));
       if(!isScan) {
           builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
       }
        // 确认
        builder.setPositiveButton(R.string.msg_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cost = etCost.getText().toString().trim();
                double d = 0.0f;
                try {
                    d = Double.valueOf(cost);
                } catch (Exception e) {}
                String content = etContent.getText().toString().trim();
                i.setRepairCost(d);
                i.setContents(content);
                // i.setIsRepair(spIsRepair.getSelectedItem().toString());
                if(isScan) i.setIsRepair("true");
                dialog.dismiss();
                notifyData(false);
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                show = false;
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
}
