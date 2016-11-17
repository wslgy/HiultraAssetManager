package com.hiultra.assetManagerNeutral.ui.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import com.hiultra.assetManagerNeutral.UHF.AsyncEpcReader;
import com.hiultra.assetManagerNeutral.UHF.EpcWriter;
import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.DaoCache;
import com.hiultra.assetManagerNeutral.dao.table.AssetMaterialInfo;
import com.hiultra.assetManagerNeutral.dao.table.MaterialModelInfo;
import com.hiultra.assetManagerNeutral.global.Constants;
import com.hiultra.assetManagerNeutral.util.DevBeep;
import com.hiultra.assetManagerNeutral.util.ImageUtil;
import com.hiultra.assetManagerNeutral.util.ToastUtil;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.minttown.hiultra.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 资产查询界面
 * 
 * @author Tom
 * @date 2016年10月29日
 * @Time 下午12:29:08
 */
public class AssetSearchFragment extends BaseFragment {
    
    @ViewInject(R.id.btn_scan)
    private Button btnScan;
    @ViewInject(R.id.btn_picture)
    private Button btnPicture;
    @ViewInject(R.id.btn_save)
    private Button btnSave;
    @ViewInject(R.id.btn_setPower)
    private Button btnSerPower;
    
    @ViewInject(R.id.tv_code)
    private TextView tvCode;
    @ViewInject(R.id.sp_model)
    private Spinner spModel;
    @ViewInject(R.id.sp_type)
    private Spinner spType;
    @ViewInject(R.id.et_name)
    private EditText etName;
    @ViewInject(R.id.et_specification)
    private EditText etSpec;
    @ViewInject(R.id.et_assetValue)
    private EditText etAssetValue;
    @ViewInject(R.id.et_netWorth)
    private EditText etNetWorth;
    @ViewInject(R.id.et_useYears)
    private EditText etUseYears;
    @ViewInject(R.id.et_DepreciationType)
    private EditText etDepreciationType;
    @ViewInject(R.id.et_Brand)
    private EditText etBrand;
    @ViewInject(R.id.et_ProduceAddress)
    private EditText etProduceAddress;
    
    @ViewInject(R.id.tv_depart)
    private TextView tvDepart;
    @ViewInject(R.id.tv_user)
    private TextView tvUser;
    @ViewInject(R.id.tv_storageDepart)
    private TextView tvStorageDepart;
    @ViewInject(R.id.tv_storageUser)
    private TextView tvStorageUser;
    @ViewInject(R.id.tv_warehouse)
    private TextView tvWarehouse;
    @ViewInject(R.id.tv_status)
    private TextView tvStatus;
    @ViewInject(R.id.iv_picture)
    private ImageView ivPicture;
    @ViewInject(R.id.rl_loading)
    private View vLoad;
    
    private ArrayAdapter<MaterialModelInfo> adapter = null;
    
    private EpcWriter epcWriter;
    private AssetMaterialInfo currAsset;
    
    @Override
    public void onEpcRead(String epc) {}
    
    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_search_asset, null);
        spModel = (Spinner) v.findViewById(R.id.sp_model);
        initSpinner();
        return v;
    }
    
    private void initSpinner() {
        ArrayList<MaterialModelInfo> list = DaoCache.getModelInfos();
        if (list != null && !list.isEmpty()) {
            adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spModel.setAdapter(adapter);
        }
    }
    
    @Override
    protected void initData(Bundle savedInstanceState) {
        etNetWorth.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        epcWriter = EpcWriter.getInstance(app);
        if (currAsset != null) show(currAsset);
    }
    
    @Override
    protected void saveData() {}
    
    @OnClick(R.id.btn_scan)
    public void scan(View v) {
        new AsyncEpcReader(app, new AsyncEpcReader.EpcReadObserver() {
            
            @Override
            public void begin() {
                vLoad.setVisibility(View.VISIBLE);
                clean();
                clickable(false);
            }
            
            @Override
            public void onReadFailed() {
                vLoad.setVisibility(View.GONE);
                ToastUtil.show("读取标签失败");
                clickable(true);
            }
            
            @Override
            public void onReadSuccess(String epc) {
                vLoad.setVisibility(View.GONE);
                clickable(true);
                tvCode.setText(epc);
                currAsset = DBTools.findFirst(AssetMaterialInfo.class, "BarCode", epc);
                if (currAsset == null) {
                    ToastUtil.show("没有相关数据");
                    return;
                }
                show(currAsset);
            }
        }).start();
    }
    
    public void show(AssetMaterialInfo a) {
        tvCode.setText(a.getBarCode());
        MaterialModelInfo modelInfo = DaoCache.getModelinfo(a.getMaterialModelCode());
        spModel.setSelection(adapter.getPosition(modelInfo));
        setText(etName, a.getName());
        setText(etSpec, a.getSpecification());
        setText(etAssetValue, a.getAssetValue());
        setText(etNetWorth, String.valueOf(a.getNetWorth()));
        setText(etBrand, a.getBrand());
        setText(etProduceAddress, a.getProduceAddress());
        setText(etUseYears, a.getUseYears());
        setText(etDepreciationType, a.getDepreciationType());
        setText(tvDepart, DaoCache.getDepartName(a.getDepartmentCode()));
        setText(tvUser, DaoCache.getUserName(a.getUserCode()));
        setText(tvStorageDepart, DaoCache.getDepartName(a.getStorageDepartmentCode()));
        setText(tvStorageUser, DaoCache.getUserName(a.getStorageCode()));
        setText(tvWarehouse, DaoCache.getWarehouseName(a.getWarehouseCode()));
        setText(tvStatus, a.getAssetStatus());
        showPicture(a);
    }
    
    private void showPicture(AssetMaterialInfo a) {
        if (currAsset == null) {
            ImageUtil.show(mActivity, R.drawable.img_load_error2, ivPicture);
            return;
        }
        String key = currAsset.getAttachmentKey();
        if (!TextUtils.isEmpty(key)) {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.PATH).append("/").append(key).append(".jpg");
            File file = new File(sb.toString());
            if (file.exists()) {
                ImageUtil.show(mActivity, file, ivPicture);
            } else {
                ImageUtil.show(mActivity, R.drawable.img_load_error, ivPicture);
            }
        } else {
            ImageUtil.show(mActivity, R.drawable.img_load_error2, ivPicture);
        }
    }
    
    @OnClick(R.id.btn_setPower)
    public void setPower(View v) {
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
                        btnSerPower.setText(String.valueOf("功率(" + power / 10 + ")"));
                    } else {
                        DevBeep.PlayErr();
                        ToastUtil.show(R.string.msg_set_power_no);
                        btnSerPower.setText(String.valueOf("功率ERR"));
                    }
                }
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }
    
    private static final int REQUEST_IMAGE = 101;
    
    @OnClick(R.id.btn_picture)
    public void picture(View vi) {
        if (currAsset == null) {
            ToastUtil.show("请先扫描资产");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String s = currAsset.getAttachmentKey();
        if (TextUtils.isEmpty(s)) {
            s = UUID.randomUUID().toString();
            currAsset.setAttachmentKey(s);
        }
        File f = new File(Constants.PATH);
        if (!f.exists()) f.mkdirs();
        File file = new File(f, s + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            showPicture(currAsset);
        }
    }
    
    @OnClick(R.id.btn_save)
    public void save(View v) {
        if (currAsset == null) return;
        currAsset.setIfuodate("0");
        MaterialModelInfo info = (MaterialModelInfo) spModel.getSelectedItem();
        currAsset.setMaterialModelCode(info.getCode());
        currAsset.setName(getText(etName));
        currAsset.setSpecification(getText(etSpec));
        currAsset.setAssetValue(getText(etAssetValue));
        currAsset.setBrand(getText(etBrand));
        currAsset.setProduceAddress(getText(etProduceAddress));
        Double d = 0.0;
        try {
            d = Double.valueOf(getText(etNetWorth));
        } catch (NumberFormatException e) {}
        currAsset.setNetWorth(d);
        currAsset.setUseYears(getInt(etUseYears));
        currAsset.setDepreciationType(getText(etDepreciationType));
        try {
            DBTools.update(currAsset);
            ToastUtil.show("保存成功");
        } catch (DbException e) {
            ToastUtil.show("保存失败");
            e.printStackTrace();
        }
    }
    
    private void clean() {
        String s = new String("");
        tvCode.setText(s);
        etName.setText(s);
        etSpec.setText(s);
        etAssetValue.setText(s);
        etNetWorth.setText(s);
        etUseYears.setText(s);
        etDepreciationType.setText(s);
        tvDepart.setText(s);
        tvUser.setText(s);
        tvStorageDepart.setText(s);
        tvStorageUser.setText(s);
        tvWarehouse.setText(s);
        // 显示默认图片
        ImageUtil.show(mActivity, R.drawable.img_load_error2, ivPicture);
    }
    
    private void clickable(boolean b) {
        btnScan.setClickable(b);
        btnSerPower.setClickable(b);
        btnPicture.setClickable(b);
        btnSave.setClickable(b);
    }
    
    private void setText(EditText et, String s) {
        if (et == null) return;
        et.setText(s == null ? "" : s);
    }
    
    private void setText(EditText et, int i) {
        if (et == null) return;
        et.setText(String.valueOf(i));
    }
    
    private void setText(TextView tv, String s) {
        if (tv == null) return;
        tv.setText(s == null ? "" : s);
    }
    
    private String getText(EditText et) {
        if (et == null) return new String("");
        return et.getText().toString().trim();
    }
    
    private int getInt(EditText et) {
        if (et == null) return 0;
        String s = et.getText().toString().trim();
        return Integer.parseInt(s);
    }
    
}
