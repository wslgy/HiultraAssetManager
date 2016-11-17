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

import com.hiultra.assetManagerNeutral.adapter.CheckAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo02;
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
 * 资产盘点
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:30:15
 */
public class AssetCheckFragment extends AsyncLoadFragment {
    
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
    HashMap<Asset_CheckInfo01, ArrayList<Asset_CheckInfo02>> map = new HashMap<>();
    
    /* Spinner */
    private ArrayList<Asset_CheckInfo01> info01List = new ArrayList<>();
    private ArrayAdapter<Asset_CheckInfo01> spAdapter;
    
    /* ListView */
    private ArrayList<Asset_CheckInfo02> info02List = new ArrayList<>();
    /** 最终由这个集合去填充ListView */
    private ArrayList<Asset_CheckInfo02> dataList = new ArrayList<>();
    private CheckAdapter adapter;
    /** 当前盘点主表 */
    private Asset_CheckInfo01 currentInfo01;
    
    /**
     * 数据展示筛选
     */
    enum STATE_SHOW {
        SHOW_ALL, // 显示全部
        SHOW_CHECK, // 显示盘点到的数据
        SHOW_UNCHECK // 显示未盘点的数据
    }
    
    private STATE_SHOW currentShow = STATE_SHOW.SHOW_ALL;
    private static String result_normal = Util.getString(R.string.checkResult_normal);
    private static String result_loss = Util.getString(R.string.checkResult_loss);
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_check_asset, null);
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
        adapter = new CheckAdapter(mActivity, dataList);
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
        ArrayList<Asset_CheckInfo01> list = DBTools.findAll(Selector.from(Asset_CheckInfo01.class).where("CheckStatus", "=",
                Util.getString(R.string.checkStatus_checking)));
        if (list == null || list.isEmpty()) {
            ToastUtil.show("没有盘点单,请先同步数据");
            return null;
        }
        info01List.addAll(list);
        for (Asset_CheckInfo01 info01 : info01List) {
            ArrayList<Asset_CheckInfo02> list2 = DBTools.findAll(Selector.from(Asset_CheckInfo02.class).where("BatchNumber", "=",
                    info01.getBatchNumber()));
            for (Asset_CheckInfo02 checkInfo02 : list2) {
                AssetMaterialInfo assetInfo = DBTools.findFirst(Selector.from(AssetMaterialInfo.class).where("Code", "=",
                        checkInfo02.getMaterialCode()));
                if (assetInfo != null) {
                    checkInfo02.setName(assetInfo.getName());
                    checkInfo02.setSpecification(assetInfo.getSpecification());
                    WarehouseInfo warehouseInfo = DaoCache.getWarehouseInfo(assetInfo.getWarehouseCode());
                    if (warehouseInfo != null) checkInfo02.setWarehouseName(warehouseInfo.getName());
                    MaterialModelInfo modelinfo = DaoCache.getModelinfo(assetInfo.getMaterialModelCode());
                    if (modelinfo != null) checkInfo02.setModelName(modelinfo.getName());
                    checkInfo02.setDepartmentName(DaoCache.getDepartName(assetInfo.getDepartmentCode()));
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
                for (Asset_CheckInfo02 info02 : info02List) {
                    if (!TextUtils.isEmpty(info02.getCheckResult())) dataList.add(info02);
                }
            } else if (currentShow == STATE_SHOW.SHOW_UNCHECK) {
                for (Asset_CheckInfo02 info02 : info02List) {
                    if (TextUtils.isEmpty(info02.getCheckResult())) dataList.add(info02);
                }
            } else {
                dataList.addAll(info02List);
            }
        }
        updateCount(info02List);
        adapter.notifyDataSetChanged(b);
    }
    
    @Override
    public void onEpcRead(String epc) {
        for (Asset_CheckInfo02 info02 : info02List) {
            if (TextUtils.equals(epc, info02.getMaterialCode())) {
                if (TextUtils.isEmpty(info02.getCheckResult())) {
                    info02.setCheckResult(result_normal);
                }
                break;
            }
        }
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyData(false);
            }
        });
    }
    
    /**
     * 更新数量
     * 
     * @param list
     */
    private void updateCount(ArrayList<Asset_CheckInfo02> list) {
        if (list == null || list.isEmpty()) {
            tvCount.setText("0/0");
            return;
        }
        int totalCount = list.size();
        int checkCount = 0;
        for (Asset_CheckInfo02 info02 : list) {
            if (!TextUtils.isEmpty(info02.getCheckResult())) checkCount++;
        }
        tvCount.setText(checkCount + "/" + totalCount);
    }
    
    @Override
    protected void saveData() {
        if (info01List == null || info01List.isEmpty()) return;
        String r = etRemark.getText().toString().trim();
        if(currentInfo01 != null && r != null) currentInfo01.setRemark(r);
        for (Asset_CheckInfo01 checkInfo01 : info01List) {
            try {
                DBTools.saveOrUpdate(checkInfo01);
                ArrayList<Asset_CheckInfo02> list = map.get(checkInfo01);
                DBTools.saveOrUpdate(list);
            } catch (DbException e) {
                LogUtils.e(checkInfo01.getBatchNumber() + "...表保存失败");
                e.printStackTrace();
            }
        }
        LogUtils.e(getClass().getSimpleName() + "...保存数据完成");
    }
    
    @OnClick(R.id.btn_finish)
    public void confirmCheck(View v) {
        if (currentInfo01 == null) {
            ToastUtil.show("没有盘点单");
            return;
        }
        final ArrayList<Asset_CheckInfo02> list = map.get(currentInfo01);
        if (list == null || list.isEmpty()) {
            ToastUtil.show("盘点单为空");
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
                currentInfo01.setCheckStatus(Util.getString(R.string.checkStatus_end));
                if (Varible.userInfo != null) currentInfo01.setUpdateOperater(Varible.userInfo.getCode());
                currentInfo01.setUpdateDateTime(time);
                for (Asset_CheckInfo02 checkInfo02 : list) {
                    checkInfo02.setIfuodate("0");
                    checkInfo02.setUpdateDateTime(time);
                    if (Varible.userInfo != null) checkInfo02.setUpdateOperater(Varible.userInfo.getCode());
                    if (TextUtils.isEmpty(checkInfo02.getCheckResult())) {
                        checkInfo02.setCheckResult(result_loss);
                    }
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
            tvDate.setText(currentInfo01.getCheckDate());
            etRemark.setText(currentInfo01.getRemark());
            // 加载从表数据
            info02List.clear();
            ArrayList<Asset_CheckInfo02> list = map.get(currentInfo01);
            if (list == null || list.isEmpty()) {
                ToastUtil.show("空的盘点单");
                notifyData(true);
                updateCount(info02List);
                return;
            }
            info02List.addAll(list);
            updateCount(info02List);
            notifyData(true);
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
    
    /** ListView监听器 */
    private class ListViewListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Asset_CheckInfo02 i = adapter.getItem(position);
            AssetMaterialInfo a = DBTools.findFirst(Selector.from(AssetMaterialInfo.class).where("BarCode", "=",
                    i.getMaterialCode()));
            if (i == null || a == null) {
                ToastUtil.show("没有相关数据");
                return;
            }
             itemClick(i, a);
        }
    }

    public void itemClick(final Asset_CheckInfo02 i, AssetMaterialInfo a) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("盘点详情");
        View view = View.inflate(mActivity, R.layout.detail_check, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        final EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
        tvCode.setText(i.getMaterialCode());
        tvName.setText(i.getName() == null ? "" : a.getName());
        tvSpec.setText(a.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        etRemark.setText(i.getRemark() == null ? "" : i.getRemark());
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
//                String remark = etRemark.getText().toString().trim();
//                i.setRemark(remark);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
}
