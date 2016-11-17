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

import com.hiultra.assetManagerNeutral.adapter.InspectionAdapter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo01;
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
 * 资产巡检
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 上午11:30:31
 */
public class AssetInspectionFragment extends AsyncLoadFragment {
    
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
    HashMap<Asset_InspectionInfo01, ArrayList<Asset_InspectionInfo02>> map = new HashMap<>();
    
    /* Spinner */
    private ArrayList<Asset_InspectionInfo01> info01List = new ArrayList<>();
    private ArrayAdapter<Asset_InspectionInfo01> spAdapter;
    
    /* ListView */
    private ArrayList<Asset_InspectionInfo02> info02List = new ArrayList<>();
    /** 最终由这个集合去填充ListView */
    private ArrayList<Asset_InspectionInfo02> dataList = new ArrayList<>();
    private InspectionAdapter adapter;
    /** 当前巡检主表 */
    private Asset_InspectionInfo01 currentInfo01;
    
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
    
    @Override
    protected View getSuccessView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_inspection_asset, null);
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
        adapter = new InspectionAdapter(mActivity, dataList);
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
        ArrayList<Asset_InspectionInfo01> list = DBTools.findAll(Selector.from(Asset_InspectionInfo01.class).where("ifuodate",
                "=", null));
        if (list == null || list.isEmpty()) {
            ToastUtil.show("没有巡检单,请先同步数据");
            return null;
        }
        info01List.addAll(list);
        for (Asset_InspectionInfo01 info01 : info01List) {
            ArrayList<Asset_InspectionInfo02> list2 = DBTools.findAll(Selector.from(Asset_InspectionInfo02.class).where("BatchNumber", "=",
                    info01.getBatchNumber()));
            if(list2==null || list2.isEmpty()) continue;
            for (Asset_InspectionInfo02 info02 : list2) {
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
                for (Asset_InspectionInfo02 info02 : info02List) {
                    if (!TextUtils.isEmpty(info02.getResult())) dataList.add(info02);
                }
            } else if (currentShow == STATE_SHOW.SHOW_UNCHECK) {
                for (Asset_InspectionInfo02 info02 : info02List) {
                    if (TextUtils.isEmpty(info02.getResult())) dataList.add(info02);
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
        for (Asset_InspectionInfo02 info02 : info02List) {
            if (!TextUtils.isEmpty(info02.getResult())) checkCount++;
        }
        tvCount.setText(checkCount + "/" + totalCount);
    }
    
    private boolean isShow = false;
    
    @Override
    public void onEpcRead(String epc) {
        if(isShow) return;
        isShow = true;
        boolean isExist = false;
        Asset_InspectionInfo02 info = null;
        for (Asset_InspectionInfo02 info02 : info02List) {
            if (TextUtils.equals(epc, info02.getMaterialCode())) {
                if (TextUtils.isEmpty(info02.getResult())) {
                    isExist = true;
                    info = info02;
                }
                break;
            }
        }
        if(!isExist) {
            isShow = false;
            return;
        }
        final Asset_InspectionInfo02 i = info;
        final AssetMaterialInfo a = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", epc);
        if(a == null) {
            isShow = false;
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
        if(currentInfo01 != null && r != null) currentInfo01.setRemark(r);
        for (Asset_InspectionInfo01 info01 : info01List) {
            try {
                DBTools.saveOrUpdate(info01);
                ArrayList<Asset_InspectionInfo02> list = map.get(info01);
                DBTools.saveOrUpdate(list);
            } catch (DbException e) {
                LogUtils.e(info01.getBatchNumber() + "...表保存失败");
                e.printStackTrace();
            }
        }
        LogUtils.e(getClass().getSimpleName() + "...保存数据完成");
    }
    
    @OnClick(R.id.btn_finish)
    public void confirmInspection(View v) {
        if (currentInfo01 == null) {
            ToastUtil.show("没有巡检单");
            return;
        }
        final ArrayList<Asset_InspectionInfo02> list = map.get(currentInfo01);
        if (list == null || list.isEmpty()) {
            ToastUtil.show("巡检单为空");
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
                String user = "";
                if (Varible.userInfo != null)  user = Varible.userInfo.getCode();
                currentInfo01.setIfuodate("0");
                currentInfo01.setInspectionStatus("已完成");
                currentInfo01.setUpdateOperater(user);
                currentInfo01.setUpdateDateTime(time);
                for (Asset_InspectionInfo02 checkInfo02 : list) {
                    checkInfo02.setIfuodate("0");
                    checkInfo02.setUpdateDateTime(time);
                    checkInfo02.setUpdateOperater(user);
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
            tvDate.setText(currentInfo01.getInspectionDate());
            etRemark.setText(currentInfo01.getRemark());
            // 加载从表数据
            info02List.clear();
            ArrayList<Asset_InspectionInfo02> list = map.get(currentInfo01);
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
            Asset_InspectionInfo02 i = adapter.getItem(position);
            if(i == null) return;
            AssetMaterialInfo a = DBTools.findFirst(Selector.from(AssetMaterialInfo.class).where("BarCode", "=",
                    i.getMaterialCode()));
            if (a == null) {
                ToastUtil.show("没有相关数据");
                return;
            }
             itemClick(i, a, false);
        }
    }

    public void itemClick(final Asset_InspectionInfo02 i, AssetMaterialInfo a, boolean isScan) {
        isShow = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("巡检详情");
        View view = View.inflate(mActivity, R.layout.detail_inspection, null);
        TextView tvCode = (TextView) view.findViewById(R.id.tv_code);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvSpec = (TextView) view.findViewById(R.id.tv_specification);
        TextView tvModel = (TextView) view.findViewById(R.id.tv_model);
        TextView tvDepart = (TextView) view.findViewById(R.id.tv_depart);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user);
        TextView tvWare = (TextView) view.findViewById(R.id.tv_warehouse);
        final EditText etResult = (EditText) view.findViewById(R.id.et_result);
        final EditText etContent = (EditText) view.findViewById(R.id.et_content);
        tvCode.setText(i.getMaterialCode());
        tvName.setText(i.getName() == null ? "" : a.getName());
        tvSpec.setText(a.getSpecification() == null ? "" : a.getSpecification());
        tvModel.setText(DaoCache.getModelName(a.getMaterialModelCode()));
        tvDepart.setText(DaoCache.getDepartName(a.getDepartmentCode()));
        tvUser.setText(DaoCache.getUserName(a.getUserCode()));
        tvWare.setText(DaoCache.getWarehouseName(a.getWarehouseCode()));
        etResult.setText(i.getResult() == null ? "" : i.getResult());
        etContent.setText(i.getContents() == null ? "" : i.getContents());
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
                String result = etResult.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                i.setResult(result);
                i.setContents(content);
                dialog.dismiss();
                notifyData(false);
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isShow = false;
            }
       });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
}
