package com.hiultra.assetManagerNeutral.ui.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hiultra.assetManagerNeutral.adapter.AssetGvAdapter;
import com.hiultra.assetManagerNeutral.domain.AssetGvItem;
import com.hiultra.assetManagerNeutral.ui.activity.AssetContentActivity;
import com.hiultra.assetManagerNeutral.util.Util;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.minttown.hiultra.R;

/**
 * 资产管理主界面
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午12:27:46
 */
public class AssetChooseFragment extends BaseFragment implements OnItemClickListener {
    
    @ViewInject(R.id.gv_asset)
    private GridView gv;
    
    private String[] gvTitles = Util.getStringArray(R.array.item_title_asset);
    private int[] gvIcons = new int[] { R.drawable.img_maintain_choose_asset, R.drawable.img_transfer_choose_asset,
            R.drawable.img_scrap_choose_asset, R.drawable.img_stop_choose_asset, R.drawable.img_check_choose_asset,
            R.drawable.img_inspection_choose_asset, R.drawable.img_handle_choose_asset, R.drawable.img_repair_choose_asset };
    
    private AssetGvAdapter gvAdapter;
    
    @Override
    public void onEpcRead(String epc) {}
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_choose_asset, null);
        return v;
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) {
        initGridView();
    }
    
    private void initGridView() {
        ArrayList<AssetGvItem> list = new ArrayList<>();
        for (int i = 0; i < gvTitles.length; i++) {
            AssetGvItem item = new AssetGvItem(Util.getDrawable(gvIcons[i]), gvTitles[i]);
            list.add(item);
        }
        gvAdapter = new AssetGvAdapter(mActivity, list);
        gv.setAdapter(gvAdapter);
        gv.setOnItemClickListener(this);
    }
    
    @Override
    protected void saveData() {
        
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.gv_asset) {
            skip(position);
        }
    }
    
    /**
     * 页面跳转
     * @param position
     */
    private void skip(int position) {
        Intent intent = new Intent(mActivity, AssetContentActivity.class);
        intent.putExtra("position", position);
        mActivity.startActivity(intent);
    }
    
}
