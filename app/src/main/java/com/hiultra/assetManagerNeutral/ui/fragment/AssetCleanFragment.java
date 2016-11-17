package com.hiultra.assetManagerNeutral.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_AllocateInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_CheckInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_HandleInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_InspectionInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_RepairInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_ScrapInfo02;
import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo01;
import com.hiultra.assetManagerNeutral.dao.table.Asset_StopInfo02;
import com.hiultra.assetManagerNeutral.dao.table.BaseTable;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.SpUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

/**
 * 资产管理模块清空数据库模块
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午10:11:10
 */
public class AssetCleanFragment extends BaseFragment {
    
    @Override
    public void onEpcRead(String epc) {
        
    }
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_clean_asset, null);
        return v;
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) {
        
    }
    
    @OnClick(R.id.ll_assetBase)
    public void cleanBase(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_BASE, Util.getString(R.string.title_base));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetTransfer)
    public void cleanTransfer(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_TRANSFER, Util.getString(R.string.title_transfer));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetCheck)
    public void cleanCheck(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_CHECK, Util.getString(R.string.title_check));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetHandle)
    public void cleanHandle(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_HANDLE, Util.getString(R.string.title_handle));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetInspection)
    public void cleanInspection(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_INSPECTION, Util.getString(R.string.title_inspection));
        clean(DownloadInfo.create(itemInfo));
        
    }
    
    @OnClick(R.id.ll_assetRepair)
    public void cleanRepair(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_REPAIR, Util.getString(R.string.title_repair));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetScrap)
    public void cleanScrap(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_SCRAP, Util.getString(R.string.title_scrap));
        clean(DownloadInfo.create(itemInfo));
    }
    
    @OnClick(R.id.ll_assetStop)
    public void cleanStop(View v) {
        ItemInfo itemInfo = new ItemInfo(Constants.TAG_STOP, Util.getString(R.string.title_stop));
        clean(DownloadInfo.create(itemInfo));
    }
    
    private void clean(final int tag, int resId, final Class<? extends BaseTable>... clazzs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        StringBuilder sb = new StringBuilder();
        sb.append(Util.getString(R.string.text_confirm_clean));
        sb.append(Util.getString(resId)).append(" ?");
        builder.setTitle(R.string.title_confirm);
        builder.setMessage(sb.toString().trim());
        builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.msg_button_clean, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Class<? extends BaseTable> clazz : clazzs) {
                    try {
                        DBTools.deleteAll(clazz);
                        SpUtil.putString(String.valueOf(tag), "2012-01-01");
                        ToastUtil.show(R.string.text_clean_complete);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    
    private void clean(final DownloadInfo info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.title_confirm);
        StringBuilder sb = new StringBuilder();
        sb.append(Util.getString(R.string.text_confirm_clean));
        sb.append(info.getItemInfo().getTitle()).append(" ?");
        builder.setMessage(sb.toString().trim());
        builder.setNegativeButton(R.string.msg_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.msg_button_clean, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Class<? extends BaseTable> clazz : info.getTableList()) {
                    try {
                        DBTools.deleteAll(clazz);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                SpUtil.putString(String.valueOf(info.getTag()), "2012-01-01");
                ToastUtil.show(R.string.text_clean_complete);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    
    @Override
    protected void saveData() {}
    
}
